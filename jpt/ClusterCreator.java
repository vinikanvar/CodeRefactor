/* Creates clusters and add files to them correctly according to the package name
 * If clusters are modified updates the JSON file
 * */

package jpt;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.CompilationUnit.Storage;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;
public class ClusterCreator {

	
	ParseCluster pc = new ParseCluster();
	String curr_cluster ="";
	String curr_class;
	JSONParser jsonParser = new JSONParser();
	//Change to desired output folder
	String output_dir = "/home/ridhi/Documents/clusters/petSpring/";
	
	public void setClass(String n)
	{
		curr_class = n;
	}
	
	public void getClusters(HashSet<String> set)
	{
		/*As in the previous json graph_clustering file one class belonged to two clusters 
		Stored clusters of a class in a hashset
		Now that the file is updated
		This function returns the cluster from the hashset*/
		Iterator<String> iter = set.iterator();
		if (iter.hasNext())
		{
			curr_cluster = iter.next();
			return;
		}
		else
		{
			curr_cluster = "";
			return;
		}
	}
	void updateJSON(String classname, String dst_cluster, String src_cluster, String jsonPath) throws IOException, ParseException
	{
		/*
		 *Updates the json file after moving the file to a diffetnt cluster  
		 */
		String subgraphPath = jsonPath + "/graph_clustering.json";
		FileReader reader= new FileReader(subgraphPath);
		Object obj = jsonParser.parse(reader);
		JSONObject jsonObject = (JSONObject)obj;
		JSONArray clusters = (JSONArray)jsonObject.get("clusters");
		for (Object cluster:clusters)
		{
			JSONObject jsonCluster = (JSONObject)cluster;
			String id = (String)jsonCluster.get("id");
			JSONArray nds = (JSONArray)jsonCluster.get("nodes");
			String src_nd = pc.getNodeID(classname);
			Object del_node = null;
			// Find the  to be deleted from the cluster
			if (id.equals(src_cluster))
			{
				for (Object nd : nds)
				{
					String nd_id = (String)nd;
					if (nd_id.equals(src_nd))
					{
						del_node = nd;
						break;
					}
				}
			}	
			// Delete the node/file
			nds.remove(del_node);
			if (id.equals(dst_cluster))
			{
				// Add node/file to the new cluster in the file
				nds.add(src_nd); 
			}	
		}
	}
	void createNewClass(CompilationUnit cu, String content, String classname, String dst_cluster) throws IOException, ParseException
	{
		/*
		 * Move the subgraph to another/better suitable cluster 
		 * as a subgraph
		 */
		String destinationFolder = output_dir + dst_cluster + "/";
		Optional<PackageDeclaration> opd = cu.getPackageDeclaration();
		String pkgName = "";
		String subpath = "";
		if (opd.isPresent())
		{
			PackageDeclaration pd = opd.get();
			pkgName = pd.getNameAsString();
			//logger.info("pkgName=" + pkgName);
			subpath = pkgName.replace(".", "/");
		}
		destinationFolder = destinationFolder + subpath;
		String filepath = destinationFolder + "/" + classname + ".java";
		File directory = new File(destinationFolder);
		if (directory.exists())
		{
			File f = new File(filepath);
//			System.out.println("Present " + filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(content);    
            fw.close(); 
			
		}
		else
		{
			directory.mkdirs();
			File f = new File(filepath);
			f.createNewFile();
//			System.out.println("Created new "+ filepath);
			FileWriter fw=new FileWriter(filepath);    
            fw.write(content);    
            fw.close();    
		}
	}
	
	void updateFile(CompilationUnit cu, String class_content, String path) throws IOException, ParseException
	{
		/* rewrites the contents of a class contained in the compilation unit cu*/
		/* Usage: when a subgraph is moved to the destination cluster
		 * in a new class, the caller class contents will change accordingly
		 * */
		ParseCluster pc = new ParseCluster();
		CallGraphInfo cgi = new CallGraphInfo();
		cgi.classnameSetter(cu);
		String classname = cgi.getClassname_cg();
		pc.parseClusters(path);
		String cluster = pc.getCluster(classname);
		String destinationFolder = output_dir + cluster + "/";
		Optional<PackageDeclaration> opd = cu.getPackageDeclaration();
		String pkgName = "";
		String subpath = "";
		if (opd.isPresent())
		{
			PackageDeclaration pd = opd.get();
			pkgName = pd.getNameAsString();
			//logger.info("pkgName=" + pkgName);
			subpath = pkgName.replace(".", "/");
		}
		destinationFolder = destinationFolder + subpath;
		String filepath = destinationFolder + "/" + classname + ".java";
		File directory = new File(destinationFolder);
		if (directory.exists())
		{
			File f = new File(filepath);
//			System.out.println("Present file: " + filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(class_content);    
            fw.close(); 
			
		}
		else
		{
			directory.mkdirs();
			File f = new File(filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(class_content);    
            fw.close();    
		}
		
	}
	
	
	
	void moveFile(CompilationUnit cu, String classname, String dst_cluster, String jsonPath) throws IOException, ParseException
	{
		/*
		 * Move the file to another/better suitable cluster
		 */
		String destinationFolder = output_dir + dst_cluster + "/";
		Optional<PackageDeclaration> opd = cu.getPackageDeclaration();
		String pkgName = "";
		String subpath = "";
		if (opd.isPresent())
		{
			PackageDeclaration pd = opd.get();
			pkgName = pd.getNameAsString();
			//logger.info("pkgName=" + pkgName);
			subpath = pkgName.replace(".", "/");
		}
		destinationFolder = destinationFolder + subpath;
		String filepath = destinationFolder + "/" + classname + ".java";
		File directory = new File(destinationFolder);
		if (directory.exists())
		{
			File f = new File(filepath);
//			System.out.println("Present " + filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(cu.toString());    
            fw.close(); 
			
		}
		else
		{
			directory.mkdirs();
			File f = new File(filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(cu.toString());    
            fw.close();    
		}
		HashSet<String> old_cluster_set = new HashSet<String>();
		old_cluster_set = pc.getClusters(classname);
		String old_cluster = "";
		for (String hs : old_cluster_set)
		{
			old_cluster = hs;
			break;
		}
		String old_path = output_dir + old_cluster + "/" + subpath +   "/" + classname + ".java";; 
		File file = new File(old_path);
        if(file.delete()){
            System.out.println(old_path + " Deleted!");
            updateJSON(classname, dst_cluster, old_cluster, jsonPath );
        }
        else
        	System.out.println(old_path +" doesn't exist");
	}
	
	void updateClass(CompilationUnit cu, String classname, String content) throws IOException
	{
		
		String destFolder;
		Optional<PackageDeclaration> pkg = cu.getPackageDeclaration();
		String pkgName = "";
		String subpath = "";
		if(pkg.isPresent())
		{
			PackageDeclaration pd = pkg.get();
			pkgName = pd.getNameAsString();
			subpath = pkgName.replace(".", "/");
		}
		
		ParseCluster pc = new ParseCluster();
		HashSet<String> set = pc.getClusters(classname);
		String cluster = "";
		for (String s : set)
		{
			cluster = s;
			break;
		}
		destFolder = output_dir +cluster +"/"+ subpath;
		File directory = new File(destFolder);
		if (directory.exists())
		{
			String filepath = destFolder + "/" + classname + ".java";
			File f = new File(filepath);
//			System.out.println("Present " + filepath);
			f.createNewFile();
			FileWriter fw = new FileWriter(filepath);    
            fw.write(cu.toString());    
            fw.close(); 
			
		}
		else
		{
			//System.out.println("Made " + destinationFolder);
			directory.mkdirs();
			String filepath = destFolder + "/" + classname + ".java";
			File f = new File(filepath);
			f.createNewFile();
			FileWriter fw = new FileWriter(filepath);    
            fw.write(cu.toString());    
            fw.close();    
		}
		
	}
	void cuVisitor(String fpath[]) throws IOException, ParseException
	{
		pc.parseClusters(fpath[2]);
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
		
		// StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
		ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(symbolSolver);
		StaticJavaParser.setConfiguration(parserConfiguration);
//		JavaParser parser = new JavaParser(parserConfiguration);

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
			sourceRoot.setParserConfiguration(parserConfiguration);
			List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
			Iterator<ParseResult<CompilationUnit>> prIt = parseResults.iterator();
			List<CompilationUnit> allCus = parseResults.stream().filter(ParseResult::isSuccessful)
					.map(r -> r.getResult().get()).collect(Collectors.toList());
		

			Iterator<CompilationUnit> cIter = allCus.iterator();
			while (cIter.hasNext()) {
				CompilationUnit cuI = cIter.next();
				curr_class = "";
				VoidVisitor<List<String>> classNameVisitor = new ClassNameCollector();
			    List<String> className = new ArrayList<>();
			    String cluster_id;
			    classNameVisitor.visit(cuI,className);
			    className.forEach(n->
			    {
			    	HashSet<String> cluster_ids = new HashSet<String>();
			    	
			    	ClassOrInterfaceDeclaration classX;
			    	try
			    	{
			    		classX = cuI.getClassByName(n).get();
			    		cluster_ids = pc.getClusters(n);
			    		getClusters(cluster_ids);
			    		setClass(n);
			    	}
			    	catch (NoSuchElementException e)
			    	{
			    		try
			    		{
				    		classX = cuI.getInterfaceByName(n).get();
				    		cluster_ids = pc.getClusters(n);
				    		getClusters(cluster_ids);
				    		setClass(n);
			    		}
			    		catch (NoSuchElementException e1)
			    		{
			    			//TODO: handle
			    		}
			    		
			    	}
			    	
			   });
			    if (!curr_class.equals(""))
			    	parseRewriteDump(cuI, output_dir, curr_cluster, curr_class);
			}
		}
	}
	private void parseRewriteDump(CompilationUnit cu , String outputFolder, String cluster, String classname) throws IOException
	{
		/*
		 * Create different clusters according to the json file
		 */
		String destinationFolder = outputFolder + cluster + "/";
		
		Optional<PackageDeclaration> opd = cu.getPackageDeclaration();
		String pkgName = "";
		String subpath = "";
		if (opd.isPresent())
		{
			PackageDeclaration pd = opd.get();
			pkgName = pd.getNameAsString();
			//logger.info("pkgName=" + pkgName);
			subpath = pkgName.replace(".", "/");
		}
		destinationFolder = destinationFolder + subpath;
		File directory = new File(destinationFolder);
		if (directory.exists())
		{
			String filepath = destinationFolder + "/" + classname + ".java";
			File f = new File(filepath);
//			System.out.println("Present " + filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(cu.toString());    
            fw.close(); 
			
		}
		else
		{
			//System.out.println("Made " + destinationFolder);
			directory.mkdirs();
			String filepath = destinationFolder + "/" + classname + ".java";
			File f = new File(filepath);
			f.createNewFile();
			FileWriter fw=new FileWriter(filepath);    
            fw.write(cu.toString());    
            fw.close();    
		}
	}
	public static void main(String args[]) throws IOException, ParseException
	{
		ClusterCreator cc = new ClusterCreator();
		cc.cuVisitor(args);
	}
	
}
