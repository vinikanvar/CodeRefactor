package jpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.json.simple.JSONObject;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.Pair;
import com.github.javaparser.utils.SourceRoot;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/* TODO: Add field access of a different class as a connection
 * Consider these connections while identifying the relocatable subgraph
 * Example: class TradeUsage
 * --> TradeClass obj = new TradeClass();
 * --> var = obj.serial;
 * move serial to TradeUsage unless it is not connected to any subgraph that cannot be moved
 * to TradeUsage
 * */

public class Test {
	JSONObject jsonObject = new JSONObject();
	JSONObject jsonObject2 = new JSONObject();
//	JSONObject jsonObject3 = new JSONObject();
	JSONObject jsonObject4 = new JSONObject();
	JSONObject jsonObjectmain = new JSONObject();
	JSONObject jsonObjectfinal = new JSONObject();
//	JSONObject jsonMethods = new JSONObject();
    FileWriter file; 
    FileWriter callDetails; 
    FileWriter subgraphDetails; 
    FileWriter movableSubgraphs; 
    int totalf = 0;
    int totalm = 0;
    HashSet<String> donotSplitSet = new HashSet<String>();
    HashSet<String> analyzed_classes = new  HashSet<String>();
    HashMap<String, HashSet<String>> class_fields_map = new HashMap<String, HashSet<String>>();
    HashMap<String, HashSet<MethodFieldUsage>>  mfu_pair = new HashMap<String, HashSet<MethodFieldUsage>>();
    HashSet<ClassSubgraphs> class_subgraphs_set = new HashSet<ClassSubgraphs>();
	Set<NeededByClasses> needed_by_classes = new HashSet<NeededByClasses>(); 
	HashMap<String, Set<String>> unique_class_calls = new HashMap<String, Set<String>>();
	HashMap<String, ArrayList<String>> dependency_map = new HashMap<String,ArrayList<String>>();
	HashMap<String, Integer> external_usage_freq = new HashMap<String, Integer>();
	HashMap<String, Method []> all_methods = new HashMap<String, Method []>();
	static ParseCluster pc = new ParseCluster();
	int class_count = 0;
	HashSet<String> classnames = new HashSet<String>();
	void analyzeShiftableCode()
	{
		Iterator<NeededByClasses> n = needed_by_classes.iterator();
		while (n.hasNext()) {
			/*The NeededByClasses structure for each class holds
			 * classname
			 * set of pairs of methods and internal (same class) methods it is called by
			 * set of pairs of methods and internal (same class) methods it calls
			 * set of pairs of methods and (external (different class) methods it is called by x caller class)
			 * set of pairs of methods and (external (different class) methods it calls x called class)
			 * */
			ClassSubgraphs class_subgraphs = new ClassSubgraphs();
			NeededByClasses x = n.next();
			List<HashSet<String>> method_subgraphs = new ArrayList<HashSet<String>>();
			List<HashSet<String>> method_subgraphs_updated = new ArrayList<HashSet<String>>();
			List<Subgraph> method_subgraphs_field_updated = new ArrayList<Subgraph>();
			//Build subgraphs
			for (HashMap.Entry<String,HashSet<String>> entry : x.internal_called_by_M.entrySet()) {
				boolean found_m =false;
				for (HashSet<String> hs : method_subgraphs)	{
					if (hs.contains(entry.getKey())) {
						// If a method is already in the subgraph
						// add its internal calls as well
						hs.addAll(entry.getValue());
						found_m = true;
						break;
					}
				}
				if (!found_m) {
					// If the method is not in any subgraph
					// create a new subgraph  with it and its internal calls
					HashSet<String> new_entry = new HashSet<String>();
					new_entry.add(entry.getKey());
					new_entry.addAll(entry.getValue());
					method_subgraphs.add(new_entry);
				}
			}  
			for (HashMap.Entry<String,HashSet<String>> entry : x.internal_callers_of_M.entrySet()) {
				boolean found_m =false;
				for (HashSet<String> hs : method_subgraphs) {
					if (hs.contains(entry.getKey())) {
						// If a method is already in the subgraph
						// add its internal callers as well
						hs.addAll(entry.getValue());
						found_m = true;
						break;
					}
				}
				if (!found_m) {
					// If a method is already in the subgraph
					// add its internal callers as well
					HashSet<String> new_entry = new HashSet<String>();
					new_entry.add(entry.getKey());
					new_entry.addAll(entry.getValue());
					method_subgraphs.add(new_entry);
				}
			}
			class_subgraphs.pkg_name = x.pkg_name;
			class_subgraphs.classname = x.classname;
			class_subgraphs.fields = x.field_set;
			HashSet<Integer> skipIndexes = new HashSet<Integer>();
			for (HashSet<String> hs : method_subgraphs)	{
			    for (String str : hs) {
					for (HashSet<String> hs_iter : method_subgraphs) {
						int hs_iter_index = new ArrayList<>(method_subgraphs).indexOf(hs_iter);
						int hs_index = new ArrayList<>(method_subgraphs).indexOf(hs);
						if (hs_iter_index >= hs_index)
							continue;
						if (hs_iter.contains(str) || donotSplitSet.contains(x.classname)) {
							skipIndexes.add(hs_index);
							// merge two subgraphs with any common method
							hs_iter.addAll(hs);
						}
					}
				}
			}
			for (HashSet<String> hs : method_subgraphs)	{
				int hs_index = new ArrayList<>(method_subgraphs).indexOf(hs);
				// Remove the duplicates  and Put the merged entries in a new structure
				if(!skipIndexes.contains(hs_index))
					method_subgraphs_updated.add(hs);
			}
			Set<String> all_mthd = x.method_scope.keySet();
			for (String mtd: all_mthd)	{
				boolean found = false;
				for (HashSet<String> hs : method_subgraphs_updated)	{
					if (hs.contains(mtd)) {
						found = true;
						break;
					}
				}
				if (!found) {
					if (donotSplitSet.contains(x.classname)) {
						// keep single methods in the same subgraph for selected classes
						if (method_subgraphs_updated.isEmpty()) {
							HashSet<String> h_new = new HashSet<String>();
							h_new.add(mtd);
							method_subgraphs_updated.add(h_new);
						}
						else {
							HashSet<String> add_entry = method_subgraphs_updated.get(0);
							add_entry.add(mtd);
							method_subgraphs_updated.remove(0);
							method_subgraphs_updated.add(add_entry);
						}
					}
					else {
						HashSet<String> h_new = new HashSet<String>();
						h_new.add(mtd);
						method_subgraphs_updated.add(h_new);
					}
					//New method_subgraph_updated is created to save from ConcurrentModidicationException
				}
			}
			for (HashSet<String> hs : method_subgraphs_updated)
			{
				Subgraph sg = new Subgraph();
				sg.subgraph.addAll(hs);
				HashSet<MethodFieldUsage> mfu_set = new HashSet<MethodFieldUsage>();
				if (mfu_pair.containsKey(class_subgraphs.classname))
					mfu_set = mfu_pair.get(class_subgraphs.classname);
				for (String mthd : hs)	{
					for (MethodFieldUsage mfu : mfu_set) {
						if (mthd.equals(mfu.method)) {
							sg.field_read.addAll(mfu.global_reads);
							sg.field_written.addAll(mfu.global_writes);
						}
					}
					for (HashMap.Entry<String, HashSet<Pair<String,String>>> entry : x.external_called_by_M.entrySet())	{
						if (entry.getKey().equals(mthd))
							sg.called_map.put(mthd, entry.getValue());
					}
				}
				if (method_subgraphs_field_updated.isEmpty())
				{
					Subgraph s_temp = new Subgraph();
					s_temp.subgraph.addAll(hs);
					s_temp.field_read = sg.field_read;
					s_temp.field_written = sg.field_written;
					s_temp.called_map = sg.called_map;
					method_subgraphs_field_updated.add(s_temp);
				}
				else
				{
					boolean found = false;
					for (Subgraph s: method_subgraphs_field_updated)
					{
						HashSet<Pair<String, String>> set1 =  new HashSet<Pair<String, String>>();
						set1.addAll(s.field_read);
						set1.addAll(s.field_written);
						HashSet<Pair<String, String>> set2 =  new HashSet<Pair<String, String>>();
								set2.addAll(sg.field_read);
						set2.addAll(sg.field_written);
						set1.retainAll(set2);
						if (set1.size() > 0) {
							//merge
							s.subgraph.addAll(sg.subgraph);
							s.field_read.addAll(sg.field_read);
							s.field_written.addAll(sg.field_written);
							s.called_map.putAll(sg.called_map);
							found = true;
							break;
						}
					}
					if (!found)	{
						Subgraph s_temp = new Subgraph();
						s_temp.subgraph.addAll(hs);
						s_temp.field_read = sg.field_read;
						s_temp.field_written = sg.field_written;
						s_temp.called_map = sg.called_map;
						method_subgraphs_field_updated.add(s_temp);
					}
				}
			}
			for (Subgraph hs : method_subgraphs_field_updated)
			{
				HashSet<String> caller_set = new HashSet<String>();
				for (String mthd : hs.subgraph)
				{
					for (HashMap.Entry<String,HashSet<Pair<String,String>>> entry : x.external_callers_of_M.entrySet())	{
						if (entry.getKey().equals(mthd)) {
							hs.caller_map.put(mthd, entry.getValue());
							for (Pair<String, String> p : entry.getValue())
							{
								caller_set.addAll(pc.getClusters(p.a));
							}
							caller_set.remove(pc.getClusters(class_subgraphs.classname));
						}
					}
				}

				class_subgraphs.is_interface = x.is_interface;
				if(caller_set.size() == 1)
				{
//					System.out.println("Class: "+ class_subgraphs.classname+" Movable "+hs.subgraph +" to "+ caller_set);
				}
				class_subgraphs.all_subgraphs.add(hs);
			}
			/*Only add subgraph details if class is not an interface
			 * */
			if (!class_subgraphs.is_interface)
				class_subgraphs_set.add(class_subgraphs);
		}
	}
	public void getAllEdges(CompilationUnit cu, CallGraphInfo cgi) throws IOException
	{
		NeededByClasses nbc = new NeededByClasses();
		nbc.classname = cgi.classname_cg;
		nbc.pkg_name = cgi.pkg;
		nbc.method_scope = cgi.method_scope;
		nbc.field_set = cgi.field_set;
		nbc.field_usage = cgi.mfu_set;
		ClassOrInterfaceDeclaration classX;
		classnames.add(nbc.classname);
		try
		{
		classX = cu.getInterfaceByName(nbc.classname).get();
		nbc.is_interface = true;
		}
		catch (NoSuchElementException e1)
		{
			//TODO: Handle
		}
		Iterator<NeededByClasses> nb = needed_by_classes.iterator();
		for (HashMap.Entry<String,HashSet<String>> entry : cgi.caller_callee.entrySet())
		{
			try {
				
				for (String value: entry.getValue())
				{
					if (nbc.internal_callers_of_M.containsKey(value))
					{
						HashSet<String> temp =  nbc.internal_callers_of_M.get(value);
						temp.add(entry.getKey());
						nbc.internal_callers_of_M.replace(value, temp);
					}
					else
					{
						HashSet temp = new HashSet<String>();
						temp.add(entry.getKey());
						nbc.internal_callers_of_M.put(value, temp);
					}
				}
				if (nbc.internal_called_by_M.containsKey(entry.getKey())) 	{
					HashSet<String> temp =  nbc.internal_called_by_M.get(entry.getKey());
					temp.addAll(entry.getValue());
					nbc.internal_called_by_M.replace(entry.getKey(), temp);
				}
				else {
					HashSet temp = new HashSet<String>();
					temp.addAll(entry.getValue());
					nbc.internal_called_by_M.put(entry.getKey(), temp);
				}
			}
			catch(NullPointerException e) {
				//TODO: Handle
			}
		}
		boolean exist = false;
		while (nb.hasNext())
		{
			NeededByClasses nx = nb.next();
			if (nx.classname != null)
				if (nx.classname.equals(nbc.classname))
				{	exist = true;
					nx.internal_called_by_M = nbc.internal_called_by_M;
					nx.internal_callers_of_M = nbc.internal_callers_of_M;
					nx.pkg_name =nbc.pkg_name;
					nx.method_scope = nbc.method_scope;
					nx.field_set = nbc.field_set;
					nx.field_usage = nbc.field_usage;
					
					try
					{
					classX = cu.getInterfaceByName(nbc.classname).get();
					nx.is_interface = true;
					}
					catch (NoSuchElementException e1)
					{
						//TODO: Handle
					}
					
					//System.out.println("Exists "+ nbc.classname);
					break;
				}
		}
		if (!exist)
			needed_by_classes.add(nbc);
		for (HashMap.Entry<Pair<String,String>,HashSet<Pair<String,String>>> entry : cgi.external_calls.entrySet())
		{
			Iterator<NeededByClasses> n = needed_by_classes.iterator();

			NeededByClasses curr = new NeededByClasses();
			NeededByClasses iter = new NeededByClasses();
			boolean found_class = false;
			boolean found_method = false;
			while (n.hasNext())	{
				curr = n.next();
				if (curr.classname != null)
				if (curr.classname.equals(entry.getKey().a)) {
					found_class = true;
					if (curr.external_called_by_M.containsKey(entry.getKey().b)) {
						found_method = true;
						HashSet<Pair<String, String>> temp = curr.external_called_by_M.get(entry.getKey().b);
						for (Pair<String,String> pair: entry.getValue()) {
							temp.add(pair);
						}
						curr.external_called_by_M.replace(entry.getKey().b, temp);
					}
					break;
				}
			}
			if (found_class && !found_method) {
				HashSet<Pair<String, String>> temp = new HashSet<Pair<String, String>>();
				for (Pair<String,String> pair: entry.getValue())
					temp.add(pair);
				//temp.putAll(entry.getValue());
				curr.external_called_by_M.put(entry.getKey().b, temp);
			}
			if (!found_class && !found_method) {
				curr = new NeededByClasses();
				curr.classname = entry.getKey().a;
				HashSet<Pair<String, String>> temp = new HashSet<Pair<String, String>>();
				for (Pair<String,String> pair: entry.getValue())
					temp.add(pair);
				curr.external_called_by_M.put(entry.getKey().b, temp);
				needed_by_classes.add(curr);
			}
			//for (HashMap.Entry<String,String> calls : entry.getValue().entrySet())
			for (Pair<String,String> calls :entry.getValue()) {
				Iterator<NeededByClasses> it = needed_by_classes.iterator();
				boolean found_class_caller = false;
				boolean found_method_caller = false;
				try	{
					while (it.hasNext()) {
						iter = it.next();
						if (iter.classname.equals(calls.a)) {
							found_class_caller = true;
							if (iter.external_callers_of_M.containsKey(calls.b)) {
								found_method_caller = true;
								HashSet<Pair<String, String>> temp = iter.external_callers_of_M.get(calls.b);
								temp.add(entry.getKey());
								iter.external_callers_of_M.replace(calls.b, temp);
//								if (iter.classname.equals("OrderDataBean"))
//									System.out.println("@@@@@@Replace@@@@@ "+iter.external_callers_of_M);
								break;
							}
							else {
								HashSet<Pair<String, String>> temp = new HashSet<Pair<String, String>>();
								for (Pair<String,String> pair: entry.getValue())
									temp.add(entry.getKey());
								iter.external_callers_of_M.put(calls.b, temp);
//								if (iter.classname.equals("OrderDataBean"))
//									System.out.println("@@@@@@Put@@@@@ "+iter.external_callers_of_M);
								break;
							}
						}
					}
					if(!found_class_caller)
					{
						NeededByClasses new_nbc = new NeededByClasses();
						new_nbc.classname = calls.a;
						HashSet<Pair<String, String>> temp = new HashSet<Pair<String, String>>();
						for (Pair<String,String> pair: entry.getValue())
						{
							temp.add(entry.getKey());
						}
						new_nbc.external_callers_of_M.put(calls.b, temp);
//						if (iter.classname.equals("OrderDataBean"))
//							System.out.println(" $$$$$$$$$$$ "+new_nbc.external_callers_of_M);
						needed_by_classes.add(new_nbc);
					}
				}
				catch (NullPointerException e)				{
					//TODO: handle
				}
			}
		}
	}
	public void mainAnalysis(String fpath[]) throws IOException {

		String chosen_path = fpath[0]+ "/";
		File mainfile = new File(fpath[0]);
		File jarDir = new File(fpath[1]);
		JSONObject jsonObject3 = new JSONObject();
		
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
		 file = new FileWriter(jsonPath+"ClassDetails.json");
	     // JSON file for all inter and intra-class edges between methods 
		 callDetails = new FileWriter(jsonPath+"MethodDependencies.json");
		 subgraphDetails = new FileWriter(jsonPath+"class_subgraphs.json");
		 movableSubgraphs = new FileWriter(jsonPath+"movableSubgraphs.json");
		//////////////////////////////////////////////////
		// Set configurations
		// Add dependent files
		//////////////////////////////////////////////////	
        Path projectRoot = FileSystems.getDefault().getPath(
        		chosen_path
				);
		CombinedTypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(), new CombinedTypeSolver());
		
		typeSolver.add(new JavaParserTypeSolver(
				new File("")));
		for (File dir: file_dir) {
			if (dir.toString().lastIndexOf("/java") == dir.toString().length()-5){
				typeSolver.add(new JavaParserTypeSolver(
						new File(dir.toString() + "/")));
			}
		}
		for (File dir: jarfiles) {
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
		for (String root : roots) {
			
			SourceRoot sourceRoot = new SourceRoot(projectRoot.resolve(root));
			//sourceRoot.addSourceRoot(Paths.get(root));
			sourceRoot.setParserConfiguration(parserConfiguration);
			List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
			Iterator<ParseResult<CompilationUnit>> prIt = parseResults.iterator();
			List<CompilationUnit> allCus = parseResults.stream().filter(ParseResult::isSuccessful)
					.map(r -> r.getResult().get()).collect(Collectors.toList());
			Iterator<CompilationUnit> cuIter = allCus.iterator();
			
			/* Pre analysis to store the classname of the compilationunit
			 * corresponding to the declared fields, in order to save time in filtering.
			 * The second iteration is expensive
			 * */
			while (cuIter.hasNext()) {
				CompilationUnit cuI = cuIter.next();
				List<FieldDeclaration> fields = cuI.findAll(FieldDeclaration.class);
				HashSet<String> field_set= new HashSet<String>(); 
				for (FieldDeclaration field : fields) {
					String fieldname = field.getVariables().toString();
					fieldname = fieldname.substring(1, fieldname.length()-1);
					if (fieldname.contains("="))
						fieldname = fieldname.substring(0, fieldname.indexOf('='));
					fieldname = fieldname.trim();
					field_set.add(fieldname);
				}
				CallGraphInfo cgi = new CallGraphInfo();
				cgi.classnameSetter(cuI);
				class_fields_map.put(cgi.getClassname_cg(), field_set);
				if (!analyzed_classes.contains(cgi.getClassname_cg()))
						 {
					List<FieldDeclaration> f = cuI.findAll(FieldDeclaration.class);
					List<MethodDeclaration> m = cuI.findAll(MethodDeclaration.class);
					totalf += f.size();
					totalm += m.size();
						 }
				analyzed_classes.add(cgi.getClassname_cg());
				System.out.println("Analyzed class "+cgi.getClassname_cg()+" -- "+cuI.getPackageDeclaration().toString());
			}
			System.out.println("Classes analyzed: "+ analyzed_classes.size());
			System.out.println("method: "+totalm + " -- fields: "+totalf);
			if (true)
				continue;
			Iterator<CompilationUnit> cIter = allCus.iterator();
			while (cIter.hasNext()) {
				CompilationUnit cuI = cIter.next();
				class_count++;
				
				ClassDetails classdetails = new ClassDetails();
				CallGraphInfo cgi = new CallGraphInfo();
				cgi.buildCallGraph(cuI,class_fields_map, analyzed_classes);
				getAllEdges(cuI, cgi);
				mfu_pair.put(cgi.classname_cg, cgi.mfu_set);
				donotSplitSet.addAll(cgi.donotSplit);
				try	{
					dependency_map.put(cgi.class_dependency.a,cgi.class_dependency.b);
					for (HashMap.Entry<String,Method []> entry : cgi.all_methods.entrySet()) {
						if (!(all_methods.containsKey(entry.getKey())))
							all_methods.put(entry.getKey(), entry.getValue());
					}
				}
				catch(Exception e)	{
					e.printStackTrace();
					System.out.println(cgi);
				}
				//////////////////////////////////////////////////
				// Get Class details 
				// Such as: name, is Interface
				// Constructors
				//////////////////////////////////////////////////
				VoidVisitor<List<String>> classNameVisitor = new ClassNameCollector();
			    List<String> className = new ArrayList<>();
			    classNameVisitor.visit(cuI,className);
				    className.forEach(n->
				    {
				    	ClassOrInterfaceDeclaration classX;
				    	try	{
					    	classX = cuI.getClassByName(n).get();
					    	Iterator<ConstructorDeclaration> cd = classX.getConstructors().iterator();
						    while (cd.hasNext())  {
						    	ConstructorDeclaration cdI = cd.next();
						    	classdetails.total_constructors++;
						    	classdetails.all_constructors.add(cdI);
						    }
				    	}
				    	catch (NoSuchElementException e) {
				    		try {
				    		classX = cuI.getInterfaceByName(n).get();
				    		classdetails.is_interface = true;
				    		}
				    		catch (NoSuchElementException e1) {
//				    			//TODO: Handle
				    		}
				    	}
					    classdetails.name = n;
				   });
				FieldDeclaration result = null;
				  List<FieldDeclaration> fields = cuI.findAll(FieldDeclaration.class);
				  for (FieldDeclaration field : fields) {
//					  //////////////////////////////////////////////////
//					  // Iterate over class fields
//					  /////////////////////////////////////////////////
//					  // TODO: Resolve error in above commented condition
					  if (field.getBegin().isPresent()) {
						  // Get all fields
						  classdetails.total_fields++;
					      classdetails.all_fields.add(field);
	 				  }
				  }
			    cuI.findAll(MethodDeclaration.class).forEach(declaration -> {
			    	
					String name = declaration.getNameAsString();
					StringBuilder parents = new StringBuilder();
		            Node currentNode = declaration;
		            
					declaration.accept(new MethodCallVisitor(), null);
				});
//				// Make the visitor go through everything inside the method.
				List<MethodCallExpr> fileMethodCalls = cuI.findAll(MethodCallExpr.class);
				for (MethodCallExpr me : fileMethodCalls)
				{
					Node curr_node = me;
					while (curr_node.getParentNode().isPresent()) {
		                curr_node = curr_node.getParentNode().get();
		            }
					
				}
				for (TypeDeclaration typeDec : cuI.getTypes()) {
					List<BodyDeclaration> members = typeDec.getMembers();

					if (members != null) {
						for (BodyDeclaration member : members) {
//							// BodyDeclaration method = parseBodyDeclaration(member.toString());
							if (member.isMethodDeclaration()) {
								MethodDeclaration field = (MethodDeclaration) member;
								classdetails.total_methods++;
								classdetails.all_methods.add(field);
							}
						}
					}
				}
				jsonObject.put("classname", classdetails.name);
				jsonObject.put("is_interface", classdetails.is_interface);
				jsonObject.put("num_fields", classdetails.total_fields);
				int i = 0;
				String f_name;
				for(FieldDeclaration fld :classdetails.all_fields)
				{
					i++;
					Map<String, Comparable> m = new LinkedHashMap(4);
					f_name = fld.getVariables().toString();
					if (f_name.charAt(0) == '[')
						f_name=f_name.substring(1, f_name.length() -1);
					if (f_name.contains("="))
						f_name = f_name.substring(0,f_name.indexOf("="));
			        m.put("field_name", f_name);
			        m.put("is_static", fld.isStatic());
			        m.put("is_final", fld.isFinal());
			        m.put("type", fld.getElementType().toString());
			          
//				        // putting address to JSONObject
				    jsonObject3.put("field" + i , m);
				}
				jsonObject.put("fields", jsonObject3);
				jsonObject.put("num_methods", classdetails.total_methods);
				int j = 0;
				for(MethodDeclaration mthd :classdetails.all_methods)
				{
					j++;
					int k = 0;
					Map m = new LinkedHashMap(7);
					Map m1 = new LinkedHashMap(mthd.getParameters().size());
			        m.put("method_name", mthd.getNameAsString());
			        m.put("method_return_type", mthd.getTypeAsString());
			        
			        for (Parameter p : mthd.getParameters())
			        {
			        	k++;
			        	m1.put("arg"+k, p.toString());
			        }
			        m.put("method_arguments", m1);
			        m.put("method_is_static", mthd.isStatic());
			        m.put("method_is_private", mthd.isPrivate());
			        m.put("method_is_public", mthd.isPublic());
			        m.put("method_is_protected", mthd.isProtected());
			        jsonObject2.put("method" + j , m);
				}
				jsonObject.put("methods", jsonObject2);
				jsonObject.put("num_constructors", classdetails.total_constructors);
				int k = 0;
				for(ConstructorDeclaration cd :classdetails.all_constructors)
				{
					k++; 
					int l = 0;
					Map m = new LinkedHashMap(2);
					Map m1 = new LinkedHashMap(cd.getParameters().size());
			        for (Parameter p : cd.getParameters())
			        {
			        	l++;
			        	m1.put("Parameter"+l, p.toString());
			        }
					jsonObject4.put("constructor" + k , m1);
				}
				jsonObject.put("constructors", jsonObject4);
				jsonObjectmain.put("class"+class_count, jsonObject);
			}
		}
		for (HashMap.Entry<String,ArrayList<String>> dep : dependency_map.entrySet()) {
			String calling_class = dep.getKey();
			for (String val : dep.getValue()) {
				if (external_usage_freq.containsKey(val)) {
					external_usage_freq.replace(val, external_usage_freq.get(val) + 1);
				}
				else {
					external_usage_freq.put(val, 1);
				}
				if (unique_class_calls.containsKey(val)) {
					Set class_set = unique_class_calls.get(val);
					class_set.add(calling_class);
					unique_class_calls.replace(val, class_set);
				}
				else {
					Set<String> s = new HashSet<String>();
					s.add(calling_class);
					unique_class_calls.put(val, s);
				}
			}
		}
		jsonObjectfinal.put("classes",jsonObjectmain);
		try {
		       file.append(jsonObjectfinal.toJSONString());
		    } catch (IOException e) {
		       // TODO Auto-generated catch block
		       e.printStackTrace();
	    }
		analyzeShiftableCode();
//		writeCallDetails();
	file.close();
	}
	void query() throws IOException
	{
		// TODO: extend later to make allow interactive queries
		String input;
		System.out.println("Do you want to list all clusters? (y/n)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		input = br.readLine();
		HashSet<String> set = new HashSet<String>();
		if (input.equalsIgnoreCase("y"))
		{
			set = pc.listClusters();
		}
	}
	public void printAllSubgraphs() throws IOException, org.json.simple.parser.ParseException
	{
		JSONObject class_movable_subgraphs = new JSONObject();
		JSONObject all_movable_subgraphs = new JSONObject();
		JSONObject jsonObject3 = new JSONObject();
		jsonObject4 = new JSONObject();
		jsonObjectmain = new JSONObject();
		jsonObjectfinal = new JSONObject();
		int total_subgraph = 0;
		int relocatable_subgraph = 0;
		int api_candidates = 0;
		System.out.println("*************** subgraphs **********************");
		Iterator<ClassSubgraphs> iter = class_subgraphs_set.iterator();
		int count = 0;
		while (iter.hasNext())
		{
			ClassSubgraphs class_subgraph = iter.next();
			if (class_subgraph.pkg_name == null)
				continue;
			count++;
			jsonObject = new JSONObject();
			jsonObject2 = new JSONObject();
			JSONObject move_sg_set = new JSONObject();
			JSONObject jo_move = new JSONObject();
			jsonObject.put("classname", class_subgraph.classname);
			HashSet<String> ch = pc.getClusters(class_subgraph.classname);
			String cluster_name="";
			for (String cls : ch)
				cluster_name = cls;
			jsonObject.put("cluster", cluster_name);
			int i = 0;
			for (Subgraph sg : class_subgraph.all_subgraphs)
			{
				i++; int j = 0;
				Map m = new LinkedHashMap();
				HashSet<String> set_mthd = new HashSet<>();
				for (HashMap.Entry<String,HashSet<Pair<String, String>>> entry : sg.caller_map.entrySet())	{
					for (Pair<String,String> p : entry.getValue())
						set_mthd.add(p.a);
				}
				Map move_map = new LinkedHashMap();
				if (set_mthd.size() == 1) {
					for (String s: set_mthd) {
						for (String ctr : pc.getClusters(class_subgraph.classname)) {
							if (!pc.getClusters(s).contains(ctr)) {
								for (String c : pc.getClusters(s)) {
									move_map.put("cluster", c);
									move_map.put("class", s);
								}
							}
						}
					}
				}
				JsonArray all_methods = new JsonArray();
				JsonArray all_caller_class = new JsonArray();
				JsonArray all_called_class = new JsonArray();
				for (String sg_value : sg.subgraph)
				{
					Map new_map = new LinkedHashMap();
					all_methods.add(sg_value);
					j++;
					new_map.put("method_"+j,sg_value);
					for (HashMap.Entry<String,HashSet<Pair<String, String>>> entry : sg.called_map.entrySet())
					{
						if (entry.getKey().equals(sg_value))
						{
							Map method_map = new LinkedHashMap();
							int t = 0;
							for (Pair<String,String> calls : entry.getValue())
							{
								t++;
								Map class_method_call = new LinkedHashMap(3);
								class_method_call.put("method", calls.b);
								class_method_call.put("class", calls.a);
								HashSet<String> clusters = pc.getClusters(calls.a);
								for (String cluster : clusters)
								{
									class_method_call.put("cluster", cluster);
								}
								all_called_class.add(calls.a);
								method_map.put(calls.a+"_"+calls.b, class_method_call);
							}
							new_map.put("calls_"+j, method_map);
							break;
						}
					}
					for (HashMap.Entry<String,HashSet<Pair<String, String>>> entry : sg.caller_map.entrySet())
					{
						if (entry.getKey().equals(sg_value)) {
							Map method_map = new LinkedHashMap();
							int t = 0;
							for (Pair<String,String> calls : entry.getValue())	{
								t++;
								Map class_method_call = new LinkedHashMap(3);
								class_method_call.put("method", calls.b);
								class_method_call.put("class", calls.a);
								HashSet<String> clusters = pc.getClusters(calls.a);
								for (String cluster : clusters)
									class_method_call.put("cluster", cluster);
								boolean present = false;
								for (int index = 0 ; index < all_caller_class.size(); index++)	{
									if (all_caller_class.get(index).getAsString().contains(calls.a)) {
										present = true;
										break;
									}
								}
								if (!present)
									all_caller_class.add(calls.a);
								method_map.put(calls.a+"_"+calls.b, class_method_call);
							}
							new_map.put("called_by_"+j, method_map);
							break;
						}
					}
					m.put("method_call_details_"+j, new_map);
				}
				Map read_map = new LinkedHashMap();
				Map write_map = new LinkedHashMap();
				for (Pair<String,String> read : sg.field_read)	{
					Map entity = new LinkedHashMap();
					entity.put("field", read.a);
					entity.put("class", read.b);
					read_map.put(read.a+"_"+read.b, entity)	;				
				}
				for (Pair<String,String> write : sg.field_read)	{
					Map entity = new LinkedHashMap();
					entity.put("field", write.a);
					entity.put("class", write.b);
					write_map.put(write.a+"_"+write.b, entity);					
				}
				JSONObject jo = new JSONObject();
				jo.put("Subgraph",m);
				jo.put("relocate_to",move_map);
				if (move_map.size() > 0)
					relocatable_subgraph++;
				jo.put("methods", all_methods);
				jo.put("caller_classes", all_caller_class);
				jo.put("called_classes", all_called_class);
				jo.put("read_variables", read_map);
				jo.put("written_variables", write_map);
				/* Get the directly movable classes
				 * record the subgraphs that are called by only
				 * one cluster other than itself's
				 * */
				jsonObject2.put("Subgraph"+i,jo);
				total_subgraph++;
				HashSet<String>  caller_cluster_set = new HashSet<String>();
				HashSet<String> main_cluster_set = pc.getClusters(class_subgraph.classname); //get the current class's cluster
				String main_cluster = "";
				 for (String s:main_cluster_set)
				 {
					 main_cluster = s;
					 break;
				 }
				boolean same_class_caller = false;
				if (all_caller_class.size() > 0) {
					for (JsonElement s: all_caller_class) {
						String caller_c = s.toString();
						caller_c = caller_c.substring(1,caller_c.length()-1);
						if (caller_c.equals(class_subgraph.classname))	{
							same_class_caller = true;
							continue;
						}
						caller_cluster_set.addAll(pc.getClusters(caller_c));
						if (pc.getClusters(caller_c).isEmpty())
							caller_cluster_set.add("noClusterAssigned");
					}
				}
				if (caller_cluster_set.size() == 1 && move_map.size() > 0)	{
					caller_cluster_set.retainAll(pc.getClusters(class_subgraph.classname));
					if (caller_cluster_set.isEmpty())
						jo_move.put("Subgraph"+i,jo);
				}
				if (caller_cluster_set.size() > 1 || (caller_cluster_set.size() == 1 && same_class_caller))
					api_candidates++;
			}
			if (jo_move.size() > 0)
				class_movable_subgraphs.put(class_subgraph.classname, jo_move);
			jsonObject.put("subgraphs", jsonObject2);
			jsonObjectmain.put(class_subgraph.classname+count, jsonObject);
		}
		if (class_movable_subgraphs.size() > 0)
			all_movable_subgraphs.put("all", class_movable_subgraphs);
		jsonObjectfinal.put("all", jsonObjectmain);
		try {
		       subgraphDetails.append(jsonObjectfinal.toJSONString());
		       movableSubgraphs.append(all_movable_subgraphs.toJSONString());
		    }
			catch (IOException e) {
		    e.printStackTrace();
			}
		movableSubgraphs.close();
		subgraphDetails.close();
		System.out.println("Total subgraphs: "+total_subgraph +"  -Relocate- "+relocatable_subgraph + " -API candidates- "+api_candidates);
	}
	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
		pc.parseClusters(args[2]);
		Test t = new Test();
		t.mainAnalysis(args);
		t.printAllSubgraphs();
	}
}