package jpt;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.json.simple.parser.ParseException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.SourceRoot;

public class MoveSubgraphMain {

	void moveAllSubgraphs(HashMap<String, HashSet<Subgraph>> subgraph_map, String fpath[]) throws IOException, ParseException
	{
		String chosen_path = fpath[0]+ "\\";
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
			if (dir.toString().lastIndexOf("\\java") == dir.toString().length()-5)
			{
				typeSolver.add(new JavaParserTypeSolver(
						new File(dir.toString() + "\\")));
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
			List<CompilationUnit> allCus = parseResults.stream().filter(ParseResult::isSuccessful)
					.map(r -> r.getResult().get()).collect(Collectors.toList());
		
			String cname = "";
			Iterator<CompilationUnit> cIter = allCus.iterator();
			ClassEditor class_editor = new ClassEditor();
			ParseCluster pc = new ParseCluster();
			pc.parseClusters(fpath[2]);
			HashMap<String, HashSet<HashSet<Subgraph>>> sg_map = new HashMap<String, HashSet<HashSet<Subgraph>>>();
			while (cIter.hasNext()) {
				HashSet<HashSet<Subgraph>> subgraph_set = new HashSet<HashSet<Subgraph>> ();
				CompilationUnit cuI = cIter.next();
				CallGraphInfo cgi = new CallGraphInfo();
				cgi.classnameSetter(cuI);
				cname = cgi.getClassname_cg();
				final String cnameFinal = cname;
				for (HashMap.Entry<String, HashSet<Subgraph>> entry : subgraph_map.entrySet())
				{
					
					if (cname != null)
					{
						if (cname.equals(entry.getKey()))
						{
							for (Subgraph sg: entry.getValue())
							{
								boolean found = false;
								for (HashSet<Subgraph> sg_set :subgraph_set)
								{
									Iterator<Subgraph> sg_iter = sg_set.iterator();
									if (sg_iter.hasNext())
									{
										if (sg.dst_cluster.equals(sg_iter.next().dst_cluster))
										{
											found = true;
											sg_set.add(sg);
											break;
										}
									}
								}
								if (!found)
								{
									HashSet<Subgraph> new_sg_set = new HashSet<Subgraph>();
									new_sg_set.add(sg);
									subgraph_set.add(new_sg_set);
								}
							}
							
//							Thread t1 = new Thread(new Runnable() {
//								public void run() {
//									try {
//										for (HashSet<Subgraph> sg_set : subgraph_set)
//										{
////											if (entry.getKey().equals("QuoteData"))
////											{
////												for (Subgraph s:sg_set)
////													System.out.println("Passed subgraph  :" + s.subgraph);
////											}
//											class_editor.APIBuilder(cuI, sg_set, cnameFinal, fpath);
//											
//										}
//										sg_map.put(cnameFinal, subgraph_set);										
//										} catch (IOException | ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//								}
//							});
//							t1.start();
//							for (HashSet<Subgraph> sg_set : subgraph_set)
//							{
//								if (entry.getKey().equals("QuoteData"))
//								{
//									for (Subgraph s:sg_set)
//										System.out.println("Passed subgraph  :" + s.subgraph);
//								}
//								class_editor.APIBuilder(cuI, sg_set, cname, fpath);
//								
//							}
//							sg_map.put(cname, subgraph_set);
						}
					}
				}
				
//				Thread t2 = new Thread(new Runnable() {
//					public void run() {
//						try {
//							for (HashMap.Entry<String, HashSet<Subgraph>> entry : subgraph_map.entrySet())
//							{
//								if (cnameFinal != null)
//								{
//									String src_cluster = pc.getCluster(cnameFinal);
//									for (Subgraph sg : entry.getValue())
//									{
//										if (cnameFinal.equals(sg.dst_class))
//											class_editor.editTarget(cuI, entry.getKey(), src_cluster, fpath[2]);
//									}
//								}
//							}								
//						} catch (IOException | ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//				t2.start();
				for (HashMap.Entry<String, HashSet<Subgraph>> entry : subgraph_map.entrySet())
				{
					if (cname != null)
					{
						String src_cluster = pc.getCluster(cname);
						for (Subgraph sg : entry.getValue())
						{
							if (cname.equals(sg.dst_class))
								class_editor.editTarget(cuI, entry.getKey(), src_cluster, fpath[2]);
						}
					}
				}
				
				
			}
		}
	}
	
	
	public static void main(String args[]) throws IOException, ParseException
	{
		IsolationInfo ii = new IsolationInfo();
		ii.getSingleExternalCallerSubgraph(args[2]);
		MoveSubgraphMain move = new MoveSubgraphMain();
		move.moveAllSubgraphs(ii.subgraph_map, args);
	}
}
