package jpt;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.Pair;
import com.github.javaparser.utils.SourceRoot;

public class ClassEditor {
	
	void spiltSuperOrInterface(String interface_name, String[] fpath, HashSet<Subgraph> subgraphs) throws IOException, ParseException
	{
		String chosen_path = fpath[0]+ "/";
		File mainfile = new File(fpath[0]);
		File jarDir = new File(fpath[1]);
			    
		// Get all files from from the project directory.
        Collection<File> file_dir = FileUtils.listFilesAndDirs(
        		  mainfile,// f.getSelectedFile(), 
        		  new RegexFileFilter("^(.*?)"), 
        		  DirectoryFileFilter.DIRECTORY
        		);
        
        // get all JARs from the jars directory
        Collection<File> jarfiles = FileUtils.listFilesAndDirs(
      		  jarDir,// f.getSelectedFile(), 
      		  new RegexFileFilter("^(.*?)"), 
      		  DirectoryFileFilter.DIRECTORY
      		);
        
        String jsonPath = fpath[2]+ "/";
		 
		//////////////////////////////////////////////////
		// Set configurations
		// Add dependent files
		//////////////////////////////////////////////////	
        Path projectRoot = FileSystems.getDefault().getPath(
        		chosen_path
				);
		CombinedTypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(), new CombinedTypeSolver());
		
		for (File dir: file_dir)
		{
			if (dir.toString().lastIndexOf("/java") == dir.toString().length()-5)
			{
				typeSolver.add(new JavaParserTypeSolver(
						new File(dir.toString() + "/")));
			}
		}
		for (File dir: jarfiles)
		{
			if (dir.toString().lastIndexOf(".jar") == dir.toString().length()-4)
				typeSolver.add(new JarTypeSolver(dir.toString()));
		}
		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
		
		ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(symbolSolver);
		StaticJavaParser.setConfiguration(parserConfiguration);
		JavaParser parser = new JavaParser(parserConfiguration);

		//////////////////////////////////////////////////
		// Set SourceRoots
		// Multiple folders to be analyzed 
		//////////////////////////////////////////////////
		HashSet<String> roots = new HashSet<String>();
		for (File dir: file_dir)
		{
			if (dir.isDirectory())
				roots.add(dir.toString().replace(chosen_path, ""));
		}
		
		ParseCluster pc = new ParseCluster();
		pc.parseClusters(fpath[2]);

		
		for (String root : roots) {
			
			SourceRoot sourceRoot = new SourceRoot(projectRoot.resolve(root));
			sourceRoot.setParserConfiguration(parserConfiguration);
			List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
			List<CompilationUnit> allCus = parseResults.stream().filter(ParseResult::isSuccessful)
					.map(r -> r.getResult().get()).collect(Collectors.toList());
		
			Iterator<CompilationUnit> cuIter = allCus.iterator();
			
			
			while (cuIter.hasNext()) {
				CompilationUnit cuI = cuIter.next();
				CallGraphInfo cgi = new CallGraphInfo();
				cgi.classnameSetter(cuI);
				cgi.classnameSetter(cuI);
				String classname = cgi.classname_cg;
				String src_cluster = pc.getCluster(classname);
				if (interface_name.equals(classname))
				{
//					String srcClass = "";
//					String APIClass = "";
					String srcCtrs = "";
					String APICtrs = "";
					HashSet<FieldDeclaration> fieldSrc_set = new HashSet<FieldDeclaration>();
					HashSet<FieldDeclaration> fieldDest_set = new HashSet<FieldDeclaration>();
					
					List<ConstructorDeclaration> ctrSrc_set = cuI.findAll(ConstructorDeclaration.class);
					List<ConstructorDeclaration> apiCtr = new ArrayList<ConstructorDeclaration>();
					HashSet<MethodDeclaration> mthdSrc_set = new HashSet<MethodDeclaration>();
					HashSet<MethodDeclaration> mthdDest_set = new HashSet<MethodDeclaration>();
					
					NodeList<ImportDeclaration> import_dec = cuI.getImports(); 
					
					List<MethodDeclaration> src_mthd = cuI.findAll(MethodDeclaration.class);
					
					List<FieldDeclaration> src_fields = cuI.findAll(FieldDeclaration.class);
					
					String dst_cluster = "";
					
//					for (Subgraph subgraph: subgraphs)
					{
						dst_cluster = "";
						for (FieldDeclaration field : src_fields) {
						//System.out.println(classname_cg+" Fields: " + field.getVariables());\
							String fieldname = field.getVariables().toString();
							if (fieldname.contains("="))
								fieldname = fieldname.substring(0,fieldname.indexOf('='));
							fieldname = fieldname.replace("[", "");
							fieldname = fieldname.replace("]", "");
							fieldname = fieldname.replace("Optional", "");
							fieldname = fieldname.trim();
							boolean found = false;
							for (Subgraph subgraph: subgraphs)
							{
								dst_cluster = subgraph.dst_cluster;
								if(subgraph.field_read.contains(new Pair<String, String>(fieldname, classname)))
								{
									fieldDest_set.add(field);
									found = true;
									break;
								}
								if(subgraph.field_written.contains(new Pair<String, String>(fieldname, classname)))
								{
									fieldDest_set.add(field);
									found = true;
									break;
								}
							}
							if (!found)
								fieldSrc_set.add(field);
						}

						for (MethodDeclaration mthd: src_mthd) {
							//System.out.println(classname_cg+" Fields: " + field.getVariables());\
								String name = mthd.getDeclarationAsString();
								NodeList<Parameter> parameters = mthd.getParameters();
								String param = "(";
								for (Parameter parameter: parameters) {
									param = param + parameter.getTypeAsString();
									param = param + ", ";
								}
								param = param.trim();
								if (param.charAt(param.length()-1) == ',')
									param = param.substring(0, param.length()-1);
								param = param + ")";
								name = name.substring(0,name.indexOf('('));
								name = name.substring(name.lastIndexOf(' ')+1);
								name = name + param;
								//System.out.println("API Class:" + subgraph.subgraph +" \n --"+ mthd + "\n --"+subgraph.subgraph.contains(name));
								boolean found = false;
								for (Subgraph subgraph: subgraphs)
								{
									dst_cluster = subgraph.dst_cluster;
									if(subgraph.subgraph.contains(name))
									{
										mthdDest_set.add(mthd);
										found = true;
										break;
									}
								}	
								if (!found)
									mthdSrc_set.add(mthd);
						}
						for (ConstructorDeclaration cd : ctrSrc_set)
						{
							String remove = removeFieldsConstructor(classname, cd, fieldDest_set, mthdDest_set);
							srcCtrs = srcCtrs + remove + "\n";
							String retain = retainFieldsConstructor(classname, cd, fieldDest_set, mthdDest_set);
							APICtrs = APICtrs + retain + "\n";
						}
					}	
					String dst_cluster_final = dst_cluster;
					String APIname = cgi.classname_cg + src_cluster;
					String srcClass = constructClass(cuI, classname, mthdSrc_set, fieldSrc_set, srcCtrs, import_dec, fpath, subgraphs);
					String APIClass = constructClass(cuI, APIname, mthdDest_set, fieldDest_set, APICtrs, import_dec, fpath, subgraphs);

					
					ClusterCreator cluster_creator = new ClusterCreator();
//					Thread t1 = new Thread(new Runnable() {
//						public void run() {
//							try {
//								cluster_creator.createNewClass(cuI, srcClass, classname, src_cluster);
//							} catch (IOException | ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					});
//					Thread t2 = new Thread(new Runnable() {
//					public void run() {
//					   try {
//						   cluster_creator.createNewClass(cuI, APIClass, APIname, dst_cluster_final);
//						} catch (IOException | ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						}
//					   }
//					});
//					t1.start();
//					t2.start();
					cluster_creator.createNewClass(cuI, srcClass, classname, src_cluster);
					cluster_creator.createNewClass(cuI, APIClass, APIname, dst_cluster);
				}
				System.out.println(cgi.getClassname_cg());
			}
		}
	}
	
	
	String constructClass(CompilationUnit cu,String classname, HashSet<MethodDeclaration> mthdSet, HashSet<FieldDeclaration> fieldSet, String ctrDec, NodeList<ImportDeclaration> import_dec, String[] path, HashSet<Subgraph> subgraphs) throws IOException, ParseException
	{
		String pkg = cu.getPackageDeclaration().toString();
		String pkg_name = pkg.substring(pkg.indexOf(' ')+1, pkg.length()-1);
		pkg_name = pkg_name.trim();
		String classStr = "";
		
		
		if (pkg.contains("["))
			pkg = pkg.substring(pkg.indexOf('[')+1, pkg.indexOf(']'));
		pkg = pkg.trim();
		classStr = classStr + pkg + "\n";
		for (ImportDeclaration id : import_dec)
		{
			classStr = classStr + id.toString();
		}
		classStr = classStr + "\n class "+ classname ;
		CallGraphInfo cgi = new CallGraphInfo();
		cgi.classnameSetter(cu);
		String className = cgi.getClassname_cg();
		
		JSONParser jsonParser = new JSONParser();
		try {
		FileReader reader= new FileReader(path[2]+"/inter_class_usage.json");
		Object obj = jsonParser.parse(reader);
		JSONObject jsonObject = (JSONObject)obj;
		Set keys =  jsonObject.keySet();
		for (Object key : keys)
		{
			// get class name from key
			String cname = key.toString().substring(key.toString().lastIndexOf('.')+1);
			if (cname.equals(className))
			{
				JSONObject details = (JSONObject)jsonObject.get(key);
				String superclass = (String)details.get("superClass");
				
				if (superclass != null)
				{
					if (!superclass.equals("java.lang.Object"))
					{
						classStr = classStr + " extends " + superclass;
					}
				}
				JSONArray impl_interface = (JSONArray)jsonObject.get("implementedInterfaces");
				if (impl_interface != null)
				{
					for (Object impl : impl_interface)
					{
						if (impl != null)
						{
							classStr = classStr + " implements " + impl.toString();
							String interface_name = impl.toString();
							if (interface_name.contains("."))
								interface_name = interface_name.substring(interface_name.lastIndexOf('.'));
							interface_name = interface_name.trim();
							spiltSuperOrInterface(interface_name, path, subgraphs); 
						}
					}
				}	
			}
		}
		
		}
		catch(Exception e)
		{
			System.out.println("Except "+e);
		}
		classStr = classStr + " \n { \n " ;
		
		for (FieldDeclaration field: fieldSet)
		{
			classStr = classStr + field.toString() + "\n";
			// If a class type is declared in field access import the class.
//			Type type = field.getElementType();
//			if (type.isClassOrInterfaceType())
//			{
//				ResolvedType resolved = type.resolve();
//				try {
//				System.out.println(resolved.asReferenceType().getTypeDeclaration().get().getPackageName());
//				System.out.println(resolved.asReferenceType().getQualifiedName());
//				}
//				catch(Exception e)
//				{
//					System.err.println("Except " + e);
//				}
//			}
		}
		classStr = classStr + ctrDec + "\n";
		for (MethodDeclaration mthd: mthdSet)
		{
			classStr = classStr + mthd.toString() + "\n";
		}
		classStr = classStr + "} \n";
		return classStr;
	}
	
	String getClassnameFromCU(CompilationUnit cu) 
	{
		VoidVisitor<List<String>> classNameVisitor = new ClassNameCollector();
	    List<String> className = new ArrayList<>();
	    classNameVisitor.visit(cu,className);
	   
	    for (String n : className)
	    {
	    	ClassOrInterfaceDeclaration classX;
	    	try
	    	{
		    	classX = cu.getClassByName(n).get();
		    	return classX.toString();
	    	}
	    	catch (NoSuchElementException e)
	    	{
	    		try
	    		{
	    		classX = cu.getInterfaceByName(n).get();
	    		return classX.toString();
	    		}
	    		catch (NoSuchElementException e1)
	    		{
	    			return "";
	    			//TODO: Handle
	    		}
	    		//System.out.println("Interface  : " + n +" "+classX.isInterface());
	    	}
	    }	
	    	//System.out.println("Constructors: "+ classX.getConstructors());
	 return "";
	}

	
	void editTarget(CompilationUnit cu, String new_class, String old_cluster, String path) throws IOException, ParseException
	{
		ParseCluster pc = new ParseCluster();
		CallGraphInfo cgi = new CallGraphInfo();
		cgi.classnameSetter(cu);
		pc.parseClusters(path);
		String tgtClassName = cgi.getClassname_cg();
		String tgt_cluster = pc.getCluster(tgtClassName); 
		List<MethodDeclaration> tgt_mthd = cu.findAll(MethodDeclaration.class);
		for (MethodDeclaration mthd: tgt_mthd)
		{
			
		}
	}

	@SuppressWarnings("unchecked")
	void APIBuilder(CompilationUnit src,  HashSet<Subgraph> subgraphs, String APIname, String[] path) throws IOException, ParseException
	{
		ParseCluster pc = new ParseCluster();
		CallGraphInfo cgi = new CallGraphInfo();
//		HashSet<String> srcCtr_set = new HashSet<String>();
//		HashSet<String> APIctr_set = new HashSet<String>();
		cgi.classnameSetter(src);
		pc.parseClusters(path[2]);
		String srcClassName = cgi.getClassname_cg();
		String src_cluster = pc.getCluster(srcClassName);
		if (srcClassName.equals(""))
		{
			System.out.println("Subgraph not moved! \n Classname empty.");
			return;
		}
		String srcCtrs = "";
		String APICtrs = "";
//		String srcClass ="";
//		String APIClass = "";
		HashSet<FieldDeclaration> fieldSrc_set = new HashSet<FieldDeclaration>();
		HashSet<FieldDeclaration> fieldDest_set = new HashSet<FieldDeclaration>();
		
		List<ConstructorDeclaration> ctrSrc_set = src.findAll(ConstructorDeclaration.class);
		List<ConstructorDeclaration> apiCtr = new ArrayList<ConstructorDeclaration>();
		HashSet<MethodDeclaration> mthdSrc_set = new HashSet<MethodDeclaration>();
		HashSet<MethodDeclaration> mthdDest_set = new HashSet<MethodDeclaration>();
		
		NodeList<ImportDeclaration> import_dec = src.getImports(); 
		
		List<MethodDeclaration> src_mthd = src.findAll(MethodDeclaration.class);
		
		List<FieldDeclaration> src_fields = src.findAll(FieldDeclaration.class);
		HashSet<Pair<String, String>>  other_fields = new HashSet<Pair<String, String>>();
		
		String dst_cluster = "";
		APIname = APIname + src_cluster;
//		for (Subgraph subgraph: subgraphs)
//		{
			dst_cluster = "";
			for (FieldDeclaration field : src_fields) {
			//System.out.println(classname_cg+" Fields: " + field.getVariables());\
				String fieldname = field.getVariables().toString();
				if (fieldname.contains("="))
					fieldname = fieldname.substring(0,fieldname.indexOf('='));
					
				fieldname = fieldname.replace("[", "");
				fieldname = fieldname.replace("]", "");
				fieldname = fieldname.replace("Optional", "");
				fieldname = fieldname.trim();
				boolean found = false;
				for (Subgraph subgraph: subgraphs)
				{
					dst_cluster = subgraph.dst_cluster;
					if(subgraph.field_read.contains(new Pair<String, String>(fieldname, srcClassName)))
					{
						fieldDest_set.add(field);
						found = true;
						break;
					}
					if(subgraph.field_written.contains(new Pair<String, String>(fieldname, srcClassName)))
					{
						fieldDest_set.add(field);
						found = true;
						break;
					}
					for(Pair<String,String> pair : subgraph.field_read)
					{
						if (!pair.b.equals(srcClassName))
							other_fields.add(pair);
					}
					for(Pair<String,String> pair : subgraph.field_written)
					{
						if (!pair.b.equals(srcClassName))
							other_fields.add(pair);
					}
				}
				if (!found)
					fieldSrc_set.add(field);
			}

			for (MethodDeclaration mthd: src_mthd) {
				//System.out.println(classname_cg+" Fields: " + field.getVariables());\
					String name = mthd.getDeclarationAsString();
					NodeList<Parameter> parameters = mthd.getParameters();
					String param = "(";
					for (Parameter parameter: parameters) {
						param = param + parameter.getTypeAsString();
						param = param + ", ";
					}
					param = param.trim();
					if (param.charAt(param.length()-1) == ',')
						param = param.substring(0, param.length()-1);
					param = param + ")";
					name = name.substring(0,name.indexOf('('));
					name = name.substring(name.lastIndexOf(' ')+1);
					name = name + param;
					//System.out.println("API Class:" + subgraph.subgraph +" \n --"+ mthd + "\n --"+subgraph.subgraph.contains(name));
					boolean found = false;
					for (Subgraph subgraph: subgraphs)
					{
						dst_cluster = subgraph.dst_cluster;
						if(subgraph.subgraph.contains(name))
						{
							mthdDest_set.add(mthd);
							found = true;
							break;
						}
					}	
					if (!found)
						mthdSrc_set.add(mthd);
			}

			for (ConstructorDeclaration cd : ctrSrc_set)
			{
				
				String remove = removeFieldsConstructor(srcClassName, cd, fieldDest_set, mthdDest_set);
				if (!srcCtrs.contains(remove))
					srcCtrs = srcCtrs + remove + "\n";
				String retain = retainFieldsConstructor(APIname, cd, fieldDest_set, mthdDest_set);
				if (!APICtrs.contains(retain))
					APICtrs = APICtrs + retain + "\n";
			}
//		}	
		
		String srcClass = constructClass(src, srcClassName, mthdSrc_set, fieldSrc_set, srcCtrs, import_dec, path, subgraphs);
		String APIClass = constructClass(src, APIname, mthdDest_set, fieldDest_set, APICtrs, import_dec, path, subgraphs);
		String APInameFinal = APIname;	
		String dst_clusterFinal = dst_cluster;
		ClusterCreator cluster_creator = new ClusterCreator();
		
//		Thread t1 = new Thread(new Runnable() {
//			public void run() {
//				try {
//					cluster_creator.createNewClass(src, srcClass, srcClassName, src_cluster);			
//					} catch (IOException | ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		Thread t2 = new Thread(new Runnable() {
//		public void run() {
//		   try {
//				cluster_creator.createNewClass(src, APIClass, APInameFinal, dst_clusterFinal);
//			} catch (IOException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
//		   }
//		});
//		t1.start();
//		t2.start();
//		
		cluster_creator.createNewClass(src, srcClass, srcClassName, src_cluster);
		cluster_creator.createNewClass(src, APIClass, APIname, dst_cluster);
		
	}
	
	//TODO: Spilt constructors
	String retainFieldsConstructor(String classname, ConstructorDeclaration cd, HashSet<FieldDeclaration> fields, HashSet<MethodDeclaration> methods)
	{
		String type = cd.getAccessSpecifier().toString().toLowerCase();
		String constructor ="";
		BlockStmt block = cd.getBody();
		NodeList<Parameter> parameters = cd.getParameters();
		NodeList<Statement> statements = block.getStatements();
		HashSet<String> fieldnames = new HashSet<String>();
		HashSet<String> mthdnames = new HashSet<String>();
		// statements that use fields
		HashSet<String> inFields = new HashSet<String>();
		// parameters that use fields
		HashSet<String> usedParam = new HashSet<String>();
		
		for(MethodDeclaration mthd : methods)
		{
			mthdnames.add(mthd.getNameAsString());
		}
		for (FieldDeclaration field : fields) {
			//System.out.println(classname_cg+" Fields: " + field.getVariables());\
				String fieldname = field.getVariables().toString();
				if (fieldname.contains("="))
					fieldname = fieldname.substring(0,fieldname.indexOf('='));
				fieldname = fieldname.replace("[", "");
				fieldname = fieldname.replace("]", "");
				fieldname = fieldname.replace("Optional", "");
				fieldname = fieldname.trim();
				fieldnames.add(fieldname);
		}
		
		for (Statement stmt : statements)
		{
			// for cases like this.setValue(value);
			boolean result = stmt.toString().matches(".*(.*);");
			if (stmt.isBlockStmt())
			{
				for (Statement blockstmt: stmt.asBlockStmt().getStatements())
				{
					//TODO Handle try blocks
				}
			}
			if (stmt.toString().contains("="))
			{
				String def = stmt.toString().substring(0,stmt.toString().indexOf('='));
				def = def.trim();
				String use = stmt.toString().substring(stmt.toString().indexOf('=')+1);
				use = use.trim();
				use = use.replace(";", "");
				if (def.contains(" "))
				{
					inFields.add(stmt.toString());
					for (Parameter parameter : parameters)
					{
						if (parameter.getNameAsString().equals(use))
						{
							usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
							continue;
						}
					}
					continue;
				}
				def = def.replace("this.", "");
				if (fieldnames.contains(def) && ! def.contains("."))
				{
					inFields.add(stmt.toString());
					for (Parameter parameter : parameters)
					{
						if (parameter.getNameAsString().equals(use))
						{
							usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
							continue;
						}
					}
					continue;
				}
			}
			else if(result)
			{
				String statement = stmt.toString(); 
				if (statement.indexOf("this.") == 0)
				{
					String methodname = statement.substring(statement.indexOf('.')+1, statement.indexOf('('));
					methodname = methodname.trim();
					if (mthdnames.contains(methodname))
					{
						inFields.add(statement);
						String use = statement.substring(statement.indexOf('(')+1, statement.indexOf(')'));
						for (Parameter parameter : parameters)
						{
							if (parameter.getNameAsString().equals(use))
							{
								usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
								continue;
							}
						}
						continue;
					}
				}
				String name = statement.substring(0, statement.indexOf('('));
				name = name.trim();
				if (name.contains("."))
				{
					// for classes like classname.method(value)
					//ex: Util.setPrice(price)
				}
				else
				{
					// method(val)
					// ex: setPrice(price)
					String methodname = name;
					methodname = methodname.trim();
					if (mthdnames.contains(methodname))
					{
						inFields.add(statement);
						String use = statement.substring(statement.indexOf('(')+1, statement.indexOf(')'));
						for (Parameter parameter : parameters)
						{
							if (parameter.getNameAsString().equals(use))
							{
								usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
								continue;
							}
						}
						continue;
					}
				}
			}
			else
			{
				inFields.add(stmt.toString());
			}
		}
		constructor = type + " " + classname;
		String args = "(";
		for (String p : usedParam) {
			args = args + p + ", ";
		}
		args = args.trim();
		if (args.charAt(args.length() - 1) == ',')
			args = args.substring(0, args.length() - 1);
		args = args + ")";
		constructor = constructor + args + "{ \n";
		for (String f : inFields)
			constructor = constructor + f + "\n";
		constructor = constructor + "} \n";
		return constructor;
	}
	
	String removeFieldsConstructor(String classname, ConstructorDeclaration cd, HashSet<FieldDeclaration> fields, HashSet<MethodDeclaration> methods)
	{
		String type = cd.getAccessSpecifier().toString().toLowerCase();
		String constructor ="";
		BlockStmt block = cd.getBody();
		NodeList<Parameter> parameters = cd.getParameters();
		NodeList<Statement> statements = block.getStatements();
		HashSet<String> fieldnames = new HashSet<String>();
		HashSet<String> mthdnames = new HashSet<String>();
		// statements that use fields
		HashSet<String> inFields = new HashSet<String>();
		// parameters that use fields
		HashSet<String> usedParam = new HashSet<String>();
		
		for(MethodDeclaration mthd : methods)
		{
			mthdnames.add(mthd.getNameAsString());
		}
		for (FieldDeclaration field : fields) {
			//System.out.println(classname_cg+" Fields: " + field.getVariables());\
				String fieldname = field.getVariables().toString();
				if (fieldname.contains("="))
					fieldname = fieldname.substring(0,fieldname.indexOf('='));
				fieldname = fieldname.replace("[", "");
				fieldname = fieldname.replace("]", "");
				fieldname = fieldname.replace("Optional", "");
				fieldname = fieldname.trim();
				fieldnames.add(fieldname);
		}
		
		for (Statement stmt : statements)
		{
			if (stmt.isBlockStmt())
			{
				for (Statement blockstmt: stmt.asBlockStmt().getStatements())
				{
					//TODO Handle try blocks
				}
			}
			// for cases like this.setValue(value);
			boolean result = stmt.toString().matches(".*(.*);");
			if (stmt.toString().contains("="))
			{
				String def = stmt.toString().substring(0,stmt.toString().indexOf('='));
				def = def.trim();
				String use = stmt.toString().substring(stmt.toString().indexOf('=')+1);
				use = use.trim();
				use = use.replace(";", "");
				if (def.contains(" "))
				{
					inFields.add(stmt.toString());
					for (Parameter parameter : parameters)
					{
						if (parameter.getNameAsString().equals(use))
						{
							usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
							continue;
						}
					}
					continue;
				}
				
				def = def.replace("this.", "");
				if (!fieldnames.contains(def) || def.contains("."))
				{
					inFields.add(stmt.toString());
					for (Parameter parameter : parameters)
					{
						if (parameter.getNameAsString().equals(use))
						{
							usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
							continue;
						}
					}
					continue;
				}
				// Analyze class.var cases separately
				// currently such statements are retained in both cases
				// See the above condition: if (!fields.contains(def) || def.contains("."))
			}
			else if(result)
			{
				String statement = stmt.toString(); 
				if (statement.indexOf("this.") == 0)
				{
					String methodname = statement.substring(statement.indexOf('.')+1, statement.indexOf('('));
					methodname = methodname.trim();
					if (!mthdnames.contains(methodname))
					{
						inFields.add(statement);
						String use = statement.substring(statement.indexOf('(')+1, statement.indexOf(')'));
						for (Parameter parameter : parameters)
						{
							if (parameter.getNameAsString().equals(use))
							{
								usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
								continue;
							}
						}
						continue;
					}
				}
				String name = statement.substring(0, statement.indexOf('('));
				name = name.trim();
				if (name.contains("."))
				{
					// for classes like classname.method(value)
					//ex: Util.setPrice(price)
				}
				else
				{
					// method(val)
					// ex: setPrice(price)
					String methodname = name;
					methodname = methodname.trim();
					if (!mthdnames.contains(methodname))
					{
						inFields.add(statement);
						String use = statement.substring(statement.indexOf('(')+1, statement.indexOf(')'));
						for (Parameter parameter : parameters)
						{
							if (parameter.getNameAsString().equals(use))
							{
								usedParam.add(parameter.getTypeAsString() + " "+ parameter.getNameAsString());
								continue;
							}
						}
						continue;
					}
				}
			}
			else
				inFields.add(stmt.toString());
		}
		constructor = type + " " + classname;
		String args = "(";
		for (String p : usedParam) {
			args = args + p + ", ";
		}
		args = args.trim();
		if (args.charAt(args.length() - 1) == ',')
			args = args.substring(0, args.length() - 1);
		args = args + ")";
		constructor = constructor + args + "{ \n";
		for (String f : inFields)
			constructor = constructor + f + "\n";
		constructor = constructor + "} \n";
		return constructor;
	}
	
}
