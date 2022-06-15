package jpt;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.JavaToken;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.utils.Pair;
//import com.ibm.websphere.samples.daytrader.TradeAction;
//import com.ibm.websphere.samples.daytrader.TradeAction;

public class CallGraphInfo {
	String classname_cg;
	String pkg;
	HashSet<String> donotSplit = new HashSet<String>();
	HashSet<String> classSet = new HashSet<String>();
	
	/*
	 * Update these sets accordingly
	 * */
	static HashSet<String> stopWords = new HashSet<String>(Arrays.asList("[","]",";","true", "false", 
			"Object","ArrayList", "this","\"\"", "new", "@", "Override",
			"==", "!=", ">=", "<=", "=", "<", ">", "(",")", ":", "?", "&&",
			"null", " ", "", "if", "for", "Collection",
			".", "trim", "+", "-", "%", "*", ",", "++", "--", "\\n","\\r", "\\r\\n")); 
	static HashSet<String> libMethods = new HashSet<String>(Arrays.asList("trim", "toString", "substring",
			"compare", "concat", "charAt", "equals")); 
	
	
	HashSet<MethodFieldUsage> mfu_set;
	Set<String> int_calls;
	Set<String> dep_classes;
	HashSet<String> field_set;
	HashMap<String,String> ext_calls;
	List<String> inner_class;
	HashMap<String,HashSet<String>> caller_callee;
	HashMap<String, Pair<Integer,Integer>> method_scope;
	HashMap<Pair<String, String>, HashSet<Pair<String, String>>> external_calls;
	Pair<String, ArrayList<String>> class_dependency;
	ResolvedMethodDeclaration decl;
	int ext,intr;
	ArrayList<String> dependencies;
	HashMap<String, Method[]> all_methods = new HashMap<String, Method[]>();
	
	public CallGraphInfo() throws IOException {
	ext = 0;
	intr = 0;
	dep_classes = new HashSet<String>();
	field_set = new HashSet<String>();
	ext_calls = new HashMap<String,String>();
	int_calls = new HashSet<String>();
	caller_callee = new HashMap<String,HashSet<String>>();
	dependencies = new ArrayList<String>();
	method_scope = new HashMap<String, Pair<Integer,Integer>>();
	inner_class = new ArrayList<String>();
	
	mfu_set = new HashSet<MethodFieldUsage>();
	external_calls = new HashMap<Pair<String, String>, HashSet<Pair<String, String>>>();
	//class_dependency = new Pair<String, ArrayList<String>>();
	// TODO Auto-generated constructor stub
}

public String getClassname_cg() {
	return classname_cg;
}

public void setClassname_cg(String classname_cg) {
	this.classname_cg = classname_cg;
}

public HashMap<String, HashSet<String>> getCaller_callee() {
	return caller_callee;
}

public void setCaller_callee(HashMap<String, HashSet<String>> caller_callee) {
	this.caller_callee = caller_callee;
}

String getToken(JavaToken t) 
{
	String token = t.toString().substring(t.toString().indexOf('\"')+1, t.toString().lastIndexOf('\"'));
	return token;
}

String getObjectType(String obj, CompilationUnit cu, MethodDeclaration method)
{
	
	String type = "none";
	if (obj.equals(""))
		return type.toString();
	String pruned = "";
	/* Check if object is declared in method
	 * */
	List<VariableDeclarationExpr> v = method.findAll(VariableDeclarationExpr.class);
	for (VariableDeclarationExpr ve : v) {
		if (ve.toString().contains(obj))
		{
			pruned = ve.toString().substring(0, ve.toString().indexOf(obj));
			if (pruned.contains(","))
			{
				pruned = pruned.trim();
				if (pruned.equals(",")) {
					return ve.getElementType().toString();
				}
			}
			else
			{
				if (!pruned.contains("=")) {
					return ve.getElementType().toString();
				}
			}
		}
	}
	/*If object is passed via arguments
	 * */
	NodeList<Parameter> args = method.getParameters();
	for (Parameter p : args)
	{
		if(p.getName().toString().equals(obj))
			return p.getType().toString();
		
	}
	
	/*Ifdeclared as field variable
	 * */
   List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
   for (FieldDeclaration field : fields) {
	   String fieldname = field.getVariables().toString();
	   fieldname = fieldname.substring(1, fieldname.length()-1);
	   if (fieldname.contains("="))
		   fieldname = fieldname.substring(0, fieldname.indexOf('='));
	   fieldname = fieldname.trim();
	   if (fieldname.equals(obj))
		   return field.getElementType().toString();
   }
	return type;
}



HashSet<Pair<String,String>> readReturn(Statement stmt, HashSet<String> locals, String cname, CompilationUnit cu, MethodDeclaration method,  HashMap<String, HashSet<String>> class_fields_map)
{
	HashSet<Pair<String,String>> readvars = new HashSet<Pair<String,String>>();
	String statement = stmt.toString();
	statement = statement.substring(statement.indexOf(' ')+1, statement.indexOf(';'));
	if (statement.contains("."))
	{
		String obj = statement.substring(0,statement.indexOf('.'));
		String var = statement.substring(statement.indexOf('.')+1);
		var = var.trim();
		if (obj.charAt(0)>= 'A' && obj.charAt(0) <= 'Z')
		{
			if (class_fields_map.containsKey(obj))
			{
				for (String v : class_fields_map.get(obj))
				{
					if (v.equals(var))
					{
						readvars.add(new Pair<String, String>(var, obj));
						break;
					}
				}
			}
		}
		else
		{
			String type = getObjectType(obj, cu, method);
			if (class_fields_map.containsKey(type))
			{
				for (String v : class_fields_map.get(type))
				{
					if (v.equals(var))
					{
						readvars.add(new Pair<String, String>(var, type));
						break;
					}
				}
			}
			
		}
	}
	else
	{
		if (!locals.contains(statement))
		{
			if (class_fields_map.containsKey(classname_cg))
			{
				for (String var : class_fields_map.get(classname_cg))
				{
					if (var.equals(statement))
					{
						readvars.add(new Pair<String, String>(statement, classname_cg));
						break;
					}
				}
			}
		}
	}
	return readvars;
}

HashSet<Pair<String,String>> readVariables(Expression e, HashSet<String> locals, String cname, CompilationUnit cu, MethodDeclaration method,  HashMap<String, HashSet<String>> class_fields_map)
{
	
	
	String digits = "[0-9]+";
    Pattern p = Pattern.compile(digits);
	HashSet<Pair<String,String>> readvars = new HashSet<Pair<String,String>>();
	TokenRange tr = e.getTokenRange().get();
	// Get unary reads
	if (e.isUnaryExpr())
	{
		String e_str = e.toString();
		if (e_str.contains("++") || e_str.contains("--"))
		{
			e_str.replace("++", "");
			e_str.replace("--", "");
			if (!locals.contains(e_str))
			{
				readvars.add(new Pair<String, String>(e_str, classname_cg));
			}
		}
	}
	
	
	if (e.isFieldAccessExpr())
	{
		String expr = e.toString();
		String obj = expr.substring(0, expr.indexOf('.'));
		String var = expr.substring(expr.indexOf('.')+1);
		var = var.trim();
		if (obj.equals("this"))
			readvars.add(new Pair<String, String>(var, classname_cg));
		else if (obj.charAt(0) >= 'A' && obj.charAt(0) <= 'Z')
			readvars.add(new Pair<String, String>(var, obj));
		else
			readvars.add(new Pair<String, String>(var, getObjectType(obj, cu, method)));
	}
	
	if (!e.isAssignExpr() && !e.isUnaryExpr() && !e.isFieldAccessExpr()) //(e.isConditionalExpr())
	{
		JavaToken t = tr.getBegin();
		
		while (t != null)
		{
			
			String token = getToken(t);
			Optional<JavaToken> t1 = t.getNextToken();
			Optional<JavaToken> tp = t.getPreviousToken();
			//TODO: Analyze tokens to see if they are variables
			// 1. ignore stop words
			if (stopWords.contains(token))
			{
				if (t == tr.getEnd())
					break;
				if (t1.isPresent())
					t = t1.get();
				else
					break; 
				continue;
			}	
			
			
			// 2. ignore methods
			if (t1.isPresent())
			{
				JavaToken next_t = t1.get();
				String next_token = getToken(next_t);
				if (next_token.equals("(")) 
				{
					if (t == tr.getEnd())
						break;
					//System.out.println("Continued Tokens: " + all + " " + cname);
					if (t1.isPresent())
						t = t1.get();
					else
						break;
					continue;
				}
			}
			
			// 3. Get variable names that are not succeeded or preceded by any thing
			// example: String t = "";
			// or t = "";
			String next_token = " ";	
			String prev_token = " ";
			if (t1.isPresent()) 
			{
				JavaToken next_t = t1.get();
				next_token = getToken(next_t);
			}	
			if (tp.isPresent())
			{
				JavaToken prev_t = tp.get(); 
				prev_token = getToken(prev_t);
			}
				//if (prev_token.equals(" ") && next_token.equals(";"))
			
			if (!prev_token.equals("."))
			{
				if (!next_token.equals(".") && !next_token.equals("(")) 
				{
					Matcher m = p.matcher(token);
			        boolean match = m.matches();
					if (!locals.contains(token) && !match)
					{
						if (!token.startsWith("\"") && !token.endsWith("\""))
						{
							if (class_fields_map.containsKey(classname_cg))
							{
								for (String var : class_fields_map.get(classname_cg))
								{
									if (var.equals(token))
									{
										readvars.add(new Pair<String, String>(token, classname_cg));
										break;
									}
								}
							}
						}
					}	
				}
			}


			// 4. if next token is . followed by a function
			if (t1.isPresent())
			{
				JavaToken next_t = t1.get();
				next_token = getToken(next_t);
				if (next_token.equals(".")) 
				{
					Optional<JavaToken> next_next_t_opt = next_t.getNextToken();
					if (next_next_t_opt.isPresent()) {
						JavaToken next_next_t = next_next_t_opt.get();
						String next_next_token = getToken(next_next_t);
						boolean isClass = false;
						if (token.charAt(0) >= 'A' && token.charAt(0) <= 'Z')
						{
							isClass = true;
							// token is a class name
							// relies on the assumption that class name should begin with upper case
						}
						Optional<JavaToken> third_t_opt = next_next_t.getNextToken();
						if (third_t_opt.isPresent())
						{
							JavaToken third_t = third_t_opt.get();
							String third_token = getToken(third_t);
							if (!third_token.equals(".") && ! third_token.equals("(")) 
							{
								//If class.variable pattern
								if (isClass)
									readvars.add(new Pair<String, String>(next_next_token, token));
							}
							// If format is *variable.method(*)
							if (third_token.equals("(")) 
							{
								if (!isClass) //current token is not a class
								{
									String token_prev = " ";
									if (tp.isPresent()) {
										// get what precedes the format variable.funtion 
										// Example: is it object.variable.function
										// Or only variable.function
										JavaToken t_prev = tp.get();
										token_prev = getToken(t_prev);
									}
									if (!token_prev.equals("."))
									{
										// If the pattern is: variable.function
										if (!locals.contains(token)) {
											// Below if condition can be relaxed based on further analysis
											// Currently it checks if a library method is called on a global variable
											if (libMethods.contains(next_next_token))
											{
												if (class_fields_map.containsKey(classname_cg))
												{
													for (String var : class_fields_map.get(classname_cg))
													{
														if (var.equals(token))
														{
															readvars.add(new Pair<String, String>(token, classname_cg));
															break;
														}
													}
												}
											}	
										}
									}
									/* Get this.variable.[*]
									 * Because if the token is neither a class nor is succeeded by ()
									 * It is more likely (not sure if 100%) to be a variable
									 * */
									else //if (token_prev.equals("."))
									{
										String before_prev_token = "";
										if (tp.isPresent())
										{
											JavaToken tpp = tp.get();
											Optional<JavaToken> tp_prev = tpp.getPreviousToken();
											if (tp_prev.isPresent())
											{
												JavaToken before_prev_t = tp_prev.get();
												before_prev_token = getToken(before_prev_t);
											}
										}
										// add current class' varable
										if (before_prev_token.equals("this"))
										{
											if (class_fields_map.containsKey(classname_cg))
											{
												for (String var : class_fields_map.get(classname_cg))
												{
													if (var.equals(token))
													{
														readvars.add(new Pair<String, String>(token, classname_cg));
														break;
													}
												}
											}
										}
										// add other class' varable
										else if (before_prev_token.charAt(0) >= 'A' && before_prev_token.charAt(0) <= 'Z')
										{
											if (class_fields_map.containsKey(before_prev_token))
											{
												for (String var : class_fields_map.get(before_prev_token))
												{
													if (var.equals(token))
													{
														readvars.add(new Pair<String, String>(token, before_prev_token));
														break;
													}
												}
											}
//											readvars.add(new Pair(token, before_prev_token));	
										}	
										else
										{
											/*This could be modified based on further observations
											 * Currently it captures obj.variable.func(*)
											 * Doubt: if there can be a case where the token before first dot (in this case, obj)
											 * can be anything other than an object
											 * */
											String type = getObjectType(before_prev_token, cu, method);
											if (class_fields_map.containsKey(type))
											{
												for (String var : class_fields_map.get(type))
												{
													if (var.equals(token))
													{
														readvars.add(new Pair<String, String>(token, type));
														break;
													}
												}
											}
//											readvars.add(new Pair(token, getObjectType(before_prev_token, cu, method)));	
											// Typically readvars stores <varable, classname>
											// classname is none in case of the same class
											// however, in this case we store the object, obj instead of the classname
										}
										
									}
								}
							}
						}
					}
				}
			}
			/* TODO: Identify what other cases are left
			 * */
			if (t == tr.getEnd())
				break;
			
			if (t1.isPresent())
				t = t1.get();
			else
				break; 
//			if (t.asString().equals(" "))
//				continue;
		}
	}
	// handle for assignment expressions
	if(e.isAssignExpr())
	{
		JavaToken t = tr.getBegin();
		boolean mark = false;
		//System.out.println("***** Assign "+e +"  "+ cname);
		while (t != null)
		{
			String token = getToken(t);
			Optional<JavaToken> t1 = t.getNextToken();
			Optional<JavaToken> tp = t.getPreviousToken();
			//TODO: Analyze tokens to see if they are variables
			// 1. Get "=" as a marker
			if (!mark)
			{
				if (token.contains("="))
					mark = true;
				if (t == tr.getEnd())
					break;
				if (t1.isPresent())
					t = t1.get();
				else
					break; 
				continue;
			}	
			
			// 2. ignore stop words
			if (stopWords.contains(token))
			{
				if (t == tr.getEnd())
					break;
				if (t1.isPresent())
					t = t1.get();
				else
					break; 
				continue;
			}
			
			else // (!stopWords.contains(token))
			{
				JavaToken next_t, prev_t;
				String next_token = " ";
				String prev_token = " ";
				String prev2_token = " ";
				String prev3_token = " ";
				if (t1.isPresent())
				{
					next_t = t1.get();
					next_token = getToken(next_t);
				}
				if (tp.isPresent())
				{
					prev_t = tp.get();
					prev_token = getToken(prev_t);
					Optional<JavaToken> prev2 = prev_t.getPreviousToken();
					if (prev2.isPresent())
					{
						JavaToken prev2_t = prev2.get();
						prev2_token = getToken(prev2_t);
						Optional<JavaToken> prev3 = prev2_t.getPreviousToken();
						if (prev3.isPresent())
						{
							JavaToken prev3_t = prev3.get();
							prev3_token = getToken(prev3_t);
						}
					}
				}
				
				
				/*3. Record simple variable usages
				 * Example: var = id : record id here
				 * */ 
				if (!prev_token.equals("."))
				{
					if (!next_token.equals(".") && !next_token.equals("("))
					{
						Matcher m = p.matcher(token);
				        boolean match = m.matches();
						if (!locals.contains(token) && !match)
						{
							if (!token.startsWith("\"") && !token.endsWith("\""))
							{
								if (class_fields_map.containsKey(classname_cg))
								{
									for (String var : class_fields_map.get(classname_cg))
									{
										if (var.equals(token))
										{
											readvars.add(new Pair<String, String>(token, classname_cg));
											break;
										}
									}
								}
							}
						}
					}	
					if (next_token.equals("."))
					{
						// TODO: add var = s.trim()
					}
				}
				
				if (prev_token.equals("."))
				{
					if (!next_token.equals(".") && !next_token.equals("("))
					{
						if (prev2_token.charAt(0) >= 'A' && prev2_token.charAt(0) <= 'Z')
						{
							/* Add class name and field accessed
							 * Example: var = Abc.f : add <f, Abc> 
							 * */
							if (class_fields_map.containsKey(prev3_token))
							{
								for (String var : class_fields_map.get(prev3_token))
								{
									if (var.equals(token))
									{
										readvars.add(new Pair<String, String>(token, prev3_token));
										break;
									}
								}
							}
						}
						else // var = abc.f where abc is object of class Abc
						{
							//System.out.println("Assign "+e +" TOKEN " +token +" CLASS: "+ cname);
							String type = getObjectType(prev3_token, cu, method);
							if (class_fields_map.containsKey(type))
							{
								for (String var : class_fields_map.get(type))
								{
									if (var.equals(token))
									{
										readvars.add(new Pair<String, String>(token, type));
										break;
									}
								}
							}
						}
					}
				}
			}
			// Get next token
			if (t == tr.getEnd())
				break;
			if (t1.isPresent())
				t = t1.get();
			else
				break;
			
		}	
	}	
	return readvars;
}
HashSet<Pair<String,String>> writtenVariables(Expression e, HashSet<String> locals, String cname, CompilationUnit cu, MethodDeclaration method, HashMap<String, HashSet<String>> class_fields_map)
{
	TokenRange tr = e.getTokenRange().get();
	HashSet<Pair<String,String>> writevars = new HashSet<Pair<String,String>>();
	// Capture unary increment and decrement
	if (e.isUnaryExpr())
	{
		String e_str = e.toString();
		if (e_str.contains("++") || e_str.contains("--"))
		{
			e_str.replace("++", "");
			e_str.replace("--", "");
			if (!locals.contains(e_str))
				writevars.add(new Pair<String, String>(e_str, classname_cg));
		}
	}
	else if (e.isAssignExpr())
	{
		String before = e.toString().substring(0, e.toString().indexOf('='));
		char last_char = before.charAt(before.length()-1);
		if(last_char == '+' || last_char == '-' || last_char == '/' || last_char == '*')
			before = before.substring(0, before.length()-1);
		before.trim();
		if (!before.contains("."))
		{
			if (!locals.contains(before))
			{
				if (class_fields_map.containsKey(classname_cg))
				{
					for (String var : class_fields_map.get(classname_cg))
					{
						if (var.equals(before))
						{
							writevars.add(new Pair<String, String>(before, classname_cg));
							break;
						}
					}
				}
			}
//				writevars.add(new Pair(before, classname_cg));
		}
		else
		{
			
			String obj = before.substring(0, before.indexOf('.'));
			String v = before.substring(before.indexOf('.')+1);
			v = v.trim();
			if (obj.charAt(0) >= 'A'  && obj.charAt(0) <= 'Z')
			{
				if (class_fields_map.containsKey(obj))
				{
					for (String var : class_fields_map.get(obj))
					{
						if (var.equals(v))
						{
							writevars.add(new Pair<String, String>(v, obj));
							break;
						}
					}
				}
			}
			else if(obj.equals("this"))
			{
				if (class_fields_map.containsKey(classname_cg))
				{
					for (String var : class_fields_map.get(classname_cg))
					{
						if (var.equals(v))
						{
							writevars.add(new Pair<String, String>(v, classname_cg));
							break;
						}
					}
				}
			}
			else
			{
				String type = getObjectType(obj, cu, method);
				if (class_fields_map.containsKey(type))
				{
					for (String var : class_fields_map.get(type))
					{
						if (var.equals(v))
						{
							writevars.add(new Pair<String, String>(v, type));
							break;
						}
					}
				}
			}
//			writevars.add(new Pair(before,obj));
		}
	}
	JavaToken t = tr.getBegin();
	while (t != null)
	{
		Optional<JavaToken> t1 = t.getNextToken();
		//System.out.println("Tokens: " + t);
		if (t1.isPresent())
			t = t1.get();
		else
			break;
		if (t.toString().equals("="))
			break;
		
//		if (t.asString().equals(" "))
//			continue;
	}	
	return writevars;
}

HashSet<String> getLocalVars(VariableDeclarationExpr vex, String pkg_name)
{
	NodeList<VariableDeclarator> vd = vex.getVariables();
	
	Type type = vex.getElementType();
	
	HashSet<String> local = new HashSet<String>();
	for (VariableDeclarator vd_element : vd)
	{
		String ve = vd_element.toString();
		if (ve.contains("="))
		{
			String arr[] = ve.split("=");
			arr[0] = arr[0].trim();
			local.add(arr[0]);
		}
		else
		{
			local.add(ve);
		}
	}
	return local;
}

HashSet<String> getArgs(NodeList<Parameter> args)
{
	HashSet<String> param = new HashSet<String>();
	for (Parameter p : args)
	{
		String p_str = p.toString();
		p_str = p_str.substring(p_str.indexOf(' ')+1);
		p_str = p_str.trim();
		param.add(p_str);
	}
	return param;
}




public void classnameSetter(CompilationUnit compilationunit)
{
	VoidVisitor<List<String>> classNameVisitor = new ClassNameCollector();
    List<String> className = new ArrayList<>();
    classNameVisitor.visit(compilationunit,className);
//    if (className.size() > 1)
//    System.out.println(className);
    	classSet.addAll(className);
	    className.forEach(n->
	    {
	    	ClassOrInterfaceDeclaration classX;
	    	try
	    	{
	    		classX = compilationunit.getClassByName(n).get();
		    	setClassname_cg(n);
		    	
	    	}
	    	catch (NoSuchElementException e)
	    	{
	    		try
	    		{
		    		classX = compilationunit.getInterfaceByName(n).get();
		    		setClassname_cg(n);
	    		}
	    		catch (NoSuchElementException e1)
	    		{
	    			//TODO: handle inner classes
	    			//classX = compilationunit.getPrimaryTypeName().get();
		    		setClassname_cg(compilationunit.getPrimaryTypeName().toString());
	    			//System.out.println("Null class"+ compilationunit);
	    		}
	    		
	    	}
	    	
	   });
	}
@SuppressWarnings({ "deprecation", "unchecked" })
public void buildCallGraph(CompilationUnit compilationunit, HashMap<String, HashSet<String>> class_fields_map, HashSet<String> analyzed_classes) throws IOException {
	pkg = compilationunit.getPackageDeclaration().toString();
	try {
	String pkg_name = compilationunit.getPackageDeclaration().get().getNameAsString();
	
	// Works on assumption that one compilation unit (class file) has only one class.
	// Inner classes are okay
	Method[] m = compilationunit.getClass().getDeclaredMethods();
	
	classnameSetter(compilationunit);
    List<FieldDeclaration> fields = compilationunit.findAll(FieldDeclaration.class);
	for (FieldDeclaration field : fields) {
		//System.out.println(classname_cg+" Fields: " + field.getVariables());\
		String fieldname = field.getVariables().toString();
		fieldname = fieldname.substring(1, fieldname.length()-1);
		if (fieldname.contains("="))
			fieldname = fieldname.substring(0, fieldname.indexOf('='));
		field_set.add(fieldname);
		Type type = field.getElementType();
		if (type.isClassOrInterfaceType())
		{
			
			try {
				ResolvedType resolved = type.resolve();
				String calledClass = resolved.asReferenceType().getQualifiedName();
				String currClass = pkg_name + "." + getClassname_cg();
				if (!calledClass.equals(currClass)) {
					donotSplit.add(calledClass.substring(calledClass.lastIndexOf('.')+1));
					String c = calledClass.substring(calledClass.lastIndexOf('.')+1);
					if (c.equals("OrderItem"))
						System.out.println(currClass +" -- "+calledClass);
				}
			}
			catch(Exception e)
			{
				System.err.println("Type " + type);
				System.err.println("Except " + e);
			}
		}
	}

    for (TypeDeclaration typeDec : compilationunit.getTypes()) {
		List<BodyDeclaration> members = typeDec.getMembers();
		if (members != null) {
			for (BodyDeclaration member : members) {
				// BodyDeclaration method = parseBodyDeclaration(member.toString());
				if (member.isMethodDeclaration()) {
					Type return_type = member.asMethodDeclaration().getType();
					// For methods with return type as a class 
					// add class to do not split
					if (return_type.isClassOrInterfaceType())
					{
						try {
							ResolvedType resolved = return_type.resolve();
							String calledClass = resolved.asReferenceType().getQualifiedName();
							String currClass = pkg_name + "." + getClassname_cg();
							if (!calledClass.equals(currClass)) {
								donotSplit.add(calledClass.substring(calledClass.lastIndexOf('.')+1));
//								String c = calledClass.substring(calledClass.lastIndexOf('.')+1);
//								if (c.equals("OrderItem"))
//									System.out.println("Return type: "+currClass +" -- "+calledClass);
							}
						}
						catch(Exception e)
						{
							System.out.println("Return type: "+return_type.asString() + "--"+classname_cg);
							//System.out.println("is reference: "+return_type.resolve().isReferenceType());

							System.err.println("Except " + e);
						}
					}
					
					HashSet<String> local_vars = new HashSet<String>();
					HashSet<Pair<String,String>> read_vars = new HashSet<Pair<String,String>>();
					HashSet<Pair<String,String>> written_vars = new HashSet<Pair<String,String>>();
					MethodFieldUsage mfu = new MethodFieldUsage();
					
					MethodDeclaration field = (MethodDeclaration) member;
					
					
					local_vars.addAll(getArgs(field.getParameters()));
					
					field.findAll(VariableDeclarationExpr.class).forEach(variable -> {
						local_vars.addAll(getLocalVars(variable, pkg_name));
			        });
					List<Statement> stmt = field.findAll(Statement.class);
					
					for (Statement statement : stmt) {
						if (statement.isReturnStmt())
							read_vars.addAll(readReturn(statement, local_vars, classname_cg, compilationunit, field, class_fields_map));
					}
					
					List<Expression> e = field.findAll(Expression.class);
					
					for (Expression ai: e)
					{
						read_vars.addAll(readVariables(ai, local_vars, classname_cg, compilationunit, field, class_fields_map));
						written_vars.addAll(writtenVariables(ai, local_vars,classname_cg, compilationunit, field, class_fields_map));
					}
					
					Pair<Integer, Integer> temp = new Pair<Integer, Integer> (field.getBegin().get().line,field.getEnd().get().line);
    				String mthd = field.getSignature().toString();
    				if (mthd.indexOf('[') != mthd.lastIndexOf(']') - 1)
    				{
	    				if (mthd.contains("["))
	    					mthd = mthd.substring(mthd.indexOf('[') + 1);
	    				if (mthd.contains("]"))
	    					mthd = mthd.substring(0,mthd.lastIndexOf(']'));
    				}
    				String args = mthd.substring(mthd.indexOf('(')+1,mthd.lastIndexOf(')'));
   					String param = "";
   					if (args.contains(", "))
   					{
	   					String[] arr = args.split(", ");    
	   					for (String ss : arr) {
	   					    if (ss.contains("."))
	   					    {
	   					    	ss = ss.substring(ss.lastIndexOf('.') + 1);
	   					    }
	   					    param = param + ss + ", ";
	   					}
	   					param = param.substring(0,param.length()-2);
   					}
   					else
   					{
   						if (args.contains("."))
   							param = args.substring(args.lastIndexOf('.')+1);
   						else
   							param = args;
   					}
   					param = "("+param+")";
					mthd = mthd.substring(0,mthd.indexOf('('));
					if (mthd.contains("."))
						mthd = mthd.substring(mthd.lastIndexOf('.')+1);
					mthd = mthd+param;
					
					mfu.method = mthd;
					mfu.local_vars.addAll(local_vars);
					mfu.global_reads.addAll(read_vars);
					mfu.global_writes.addAll(written_vars);
					mfu_set.add(mfu);
					method_scope.put(mthd,temp);
					
				}
				if (member.isClassOrInterfaceDeclaration())
					inner_class.add(member.getClass().getName());
			}
		}
	}
    
    all_methods.put(classname_cg, m);
    List<MethodCallExpr> fileMethodCalls = compilationunit.findAll(MethodCallExpr.class);
	for (MethodCallExpr me : fileMethodCalls)
	{
		for (HashMap.Entry<String, Pair<Integer, Integer>> set : method_scope.entrySet()) {
		    if (me.getBegin().get().line >= set.getValue().a)
		    {
		    	if (me.getBegin().get().line <= set.getValue().b)
		    	{
		    	try {
		    		decl = me.resolve();
		    	}
		    	catch(Exception e)
		    	{
		    		System.out.println("Method details "+me.getNameAsString()+ "--" +me.toString()+ "--");
		    		System.out.println(pkg_name +" -- " +classname_cg);
		    		System.err.println("Exception "+e);
		    		continue;
		    	}
	    		
		    		
		    		/* Do not analyze or add any method call that is 
		    		 * not a part of the project analyzed.
		    		 * */
		    		if (!analyzed_classes.contains(decl.getClassName()))
		    			continue;
		    		if (classname_cg != decl.getClassName())
		    		{
		    			Pair<String, String> internal = new Pair<String,String> (classname_cg, set.getKey());
		    			HashSet<Pair<String, String>> temp = new HashSet<Pair<String, String>>();
		    			if (external_calls.containsKey(internal))
		    			{
		    				temp = external_calls.get(internal);
		    				String mthd = decl.getQualifiedSignature();
		    				if (mthd.indexOf('[') != mthd.lastIndexOf(']') - 1)
		    				{
			    				if (mthd.contains("["))
			    					mthd = mthd.substring(mthd.indexOf('[') + 1);
			    				if (mthd.contains("]"))
			    					mthd = mthd.substring(0,mthd.lastIndexOf(']'));
		    				}
		    				String args = mthd.substring(mthd.indexOf('(')+1,mthd.lastIndexOf(')'));
		   					String param = "";
		   					if (args.contains(", "))
		   					{
			   					String[] arr = args.split(", ");    
			   					for ( String ss : arr) {
			   					    if (ss.contains("."))
			   					    {
			   					    	ss = ss.substring(ss.lastIndexOf('.') + 1);
			   					    }
			   					    param = param + ss + ", ";
			   					}
			   					param = param.substring(0,param.length()-2);
		   					}
		   					else
		   					{
		   						if (args.contains("."))
		   							param = args.substring(args.lastIndexOf('.')+1);
		   						else
		   							param = args;
		   					}
		   					param = "("+param+")";
							mthd = mthd.substring(0,mthd.indexOf('('));
							if (mthd.contains("."))
								mthd = mthd.substring(mthd.lastIndexOf('.')+1);
							mthd = mthd+param;
		    				Pair<String,String> p = new Pair<String,String>(decl.getClassName(),mthd);
		    				temp.add(p);
		    				external_calls.put(internal,temp);
		    			}
		    			else
		    			{
		    				String mthd = decl.getQualifiedSignature();
		    				if (mthd.indexOf('[') != mthd.lastIndexOf(']') - 1)
		    				{
			    				if (mthd.contains("["))
			    					mthd = mthd.substring(mthd.indexOf('[') + 1);
			    				if (mthd.contains("]"))
			    					mthd = mthd.substring(0,mthd.lastIndexOf(']'));
		    				}
		   					String args = mthd.substring(mthd.indexOf('(')+1,mthd.lastIndexOf(')'));
		   					String param = "";
		   					if (args.contains(", "))
		   					{
			   					String[] arr = args.split(", ");    
			   					for ( String ss : arr) {
			   					    if (ss.contains("."))
			   					    {
			   					    	ss = ss.substring(ss.lastIndexOf('.') + 1);
			   					    }
			   					    param = param + ss + ", ";
			   					}
			   					param = param.substring(0,param.length()-2);
		   					}
		   					else
		   					{
		   						if (args.contains("."))
		   							param = args.substring(args.lastIndexOf('.')+1);
		   						else
		   							param = args;
		   					}
		   					param = "("+param+")";
							mthd = mthd.substring(0,mthd.indexOf('('));
							if (mthd.contains("."))
								mthd = mthd.substring(mthd.lastIndexOf('.')+1);
							mthd = mthd+param;
		    				Pair<String,String> p = new Pair<String,String>(decl.getClassName(),mthd);
		    				temp.add(p);
		    				external_calls.put(internal,temp);
		    			}
		    			dependencies.add(decl.getClassName());
		    			ext++;
		    			if (ext_calls.get(me.getNameAsString()) != decl.getClassName())
		    				ext_calls.put(me.getNameAsString(),decl.getClassName());
		    			//decl.getClass().getDeclaredMethods()
		    			if (!(all_methods.containsKey(decl.getClassName())))
		    			{
		    				Method[] mthd = decl.getClass().getDeclaredMethods();
		    			    all_methods.put(classname_cg, mthd);
		    			}
		    			dep_classes.add(decl.getClassName());
		    		}
		    		else 
	    			{
		    			decl = me.resolve();
		    			String mthd = decl.getQualifiedSignature();
		    			if (mthd.indexOf('[') != mthd.lastIndexOf(']') - 1)
	    				{
		    				if (mthd.contains("["))
		    					mthd = mthd.substring(mthd.indexOf('[') + 1);
		    				if (mthd.contains("]"))
		    					mthd = mthd.substring(0,mthd.lastIndexOf(']'));
	    				}
		    			String args = mthd.substring(mthd.indexOf('(')+1,mthd.lastIndexOf(')'));
	   					String param = "";
	   					if (args.contains(", "))
	   					{
		   					String[] arr = args.split(", ");    
		   					for ( String ss : arr) {
		   					    if (ss.contains("."))
		   					    {
		   					    	ss = ss.substring(ss.lastIndexOf('.') + 1);
		   					    }
		   					    param = param + ss + ", ";
		   					}
		   					param = param.substring(0,param.length()-2);
	   					}
	   					else
	   					{
	   						if (args.contains("."))
	   							param = args.substring(args.lastIndexOf('.')+1);
	   						else
	   							param = args;
	   					}
	   					param = "("+param+")";
						mthd = mthd.substring(0,mthd.indexOf('('));
						if (mthd.contains("."))
							mthd = mthd.substring(mthd.lastIndexOf('.')+1);
						mthd = mthd+param;
	    				HashSet<String> new_entry = new HashSet<String>();
	    				if (caller_callee.containsKey(set.getKey()))
	    				{
	    					new_entry = caller_callee.get(set.getKey());
	    					new_entry.add(mthd);
	    					caller_callee.replace(set.getKey(), new_entry);
	    				}
	    				else
	    				{
	    					new_entry.add(mthd);
	    					caller_callee.put(set.getKey(), new_entry);
	    				}	
		    			//caller_callee.put( set.getKey(),mthd);
	    				intr++;
	    				int_calls.add(me.getNameAsString());
	    				//TODO: store details of same class functions
	    			}
		    	}
		    }
		}
	}
	class_dependency = new Pair<String, ArrayList<String>>(classname_cg, dependencies);
	
}
	catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("Exception "+e+"-\n-"+ e.getCause() );
	}
	}


}
