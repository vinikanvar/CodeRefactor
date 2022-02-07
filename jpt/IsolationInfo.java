package jpt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.javaparser.utils.Pair;

public class IsolationInfo {
	
	HashMap<String, HashSet<Subgraph>> subgraph_map;
	public IsolationInfo() {
		subgraph_map = new HashMap<String, HashSet<Subgraph>>();
		// TODO Auto-generated constructor stub
	}
	
	JSONParser jsonParser = new JSONParser();
	
	String getSuperclass(String name, String jsonPath) throws IOException, ParseException
	{
		
		/* Reads the inter_class_usage file
		 * also gets the superclass info
		 * */
		String classname;
		String path = jsonPath + "/inter_class_usage.json";
		FileReader reader= new FileReader(path);
		Object obj = jsonParser.parse(reader);
		JSONObject jsonObject = (JSONObject)obj;
		//JSONObject all_classes = (JSONObject) jsonObject.get("all");
		Set<String> keys =  jsonObject.keySet();
		for (String key: keys)
		{
			classname = key.substring(key.lastIndexOf('.') + 1);
			if (classname.equals(name))
			{
				JSONObject class_details = (JSONObject) jsonObject.get(key);
				Set<String> class_keys  =  class_details.keySet();
				String superclass = (String) class_details.get("superClass");
				return superclass;
			}
		}
		return null;
	}
	
	
	JSONArray getImplementedInterfaces(String name,String  jsonPath) throws IOException, ParseException
	{
		
		/* Reads the inter_class_usage file
		 * also gets the interface implemented info
		 * */
		String classname;
		String path = jsonPath + "/inter_class_usage.json";
		FileReader reader= new FileReader(path);
		Object obj = jsonParser.parse(reader);
		JSONObject jsonObject = (JSONObject)obj;
		//JSONObject all_classes = (JSONObject) jsonObject.get("all");
		Set<String> keys =  jsonObject.keySet();
		for (String key: keys)
		{
			classname = key.substring(key.lastIndexOf('.') + 1);
			if (classname.equals(name))
			{
				JSONObject class_details = (JSONObject) jsonObject.get(key);
				Set<String> class_keys  =  class_details.keySet();
				JSONArray interfaces = (JSONArray) class_details.get("implementedInterfaces");
				return interfaces;
			}
		}
		return null;
	}
	void getSingleExternalCallerSubgraph(String jsonPath) throws IOException, ParseException
	{
		/* Read movableSubgraphs json to get the subgraphs that are 
		 * only called by one cluster
		 * */
		String classname;
		String path = jsonPath +"/movableSubgraphs.json";
		FileReader reader= new FileReader(path);//("C:\\\\Users\\\\AVT6SC744\\\\Downloads\\\\rejptest\\\\movableSubgraphs.json");
		Object obj = jsonParser.parse(reader);
		JSONObject jsonObject = (JSONObject)obj;
		JSONObject all_classes = (JSONObject) jsonObject.get("all");
		try {
			Set<String> keys =  all_classes.keySet();
		
		
		//for (Object c:all_classes)
		for (String key: keys)
		{
			classname = key;
			
			
			JSONObject class_details = (JSONObject) all_classes.get(key);
			Set<String> class_keys  =  class_details.keySet();
			for (String class_key:class_keys)
			{
				Subgraph sg = new Subgraph();
				JSONObject subgraphs = (JSONObject) class_details.get(class_key);
				Set<String> subgraph_details = subgraphs.keySet();
				for (String sd: subgraph_details)
				{
					if (sd.equals("relocate_to"))
					{
						JSONObject reloc_info = (JSONObject) subgraphs.get(sd);
						String cluster =  (String) reloc_info.get("cluster");
						String dst_class =  (String) reloc_info.get("class");
						//System.out.println(cluster);
						sg.dst_cluster = cluster;
						sg.dst_class = dst_class;
						
					}
					if (sd.equals("methods"))
					{
						JSONArray methods = (JSONArray) subgraphs.get(sd);
						for (int i = 0; i < methods.size(); i++)
						{
							sg.subgraph.add(methods.get(i).toString());
						}
						//System.out.println(methods);
					}
					if (sd.equals("read_variables"))
					{
						JSONObject reads = (JSONObject) subgraphs.get(sd);
						Set<String> read_set = reads.keySet();
						for (String read: read_set)
						{
							read = read.replace("\"", "");
							String var = read.substring(0, read.indexOf('_'));
							String cName = read.substring(read.indexOf('_')+1);
							var = var.trim();
							cName = cName.trim();
							sg.field_read.add(new Pair<String,String> (var,cName));
						}
						//System.out.println(reads);
					}
					if (sd.equals("written_variables"))
					{
						JSONObject writes = (JSONObject) subgraphs.get(sd);
						Set<String> write_set = writes.keySet();
						for (String write: write_set)
						{
							write = write.replace("\"", "");
							String var = write.substring(0, write.indexOf('_'));
							String cName = write.substring(write.indexOf('_')+1);
							var = var.trim();
							cName = cName.trim();
							sg.field_written.add(new Pair<String,String> (var,cName));
						}
					}
				}
				if (subgraph_map.containsKey(classname))
				{
					HashSet<Subgraph> sg_set = new HashSet<Subgraph>();
					sg_set = subgraph_map.get(classname);
					sg_set.add(sg);
					subgraph_map.replace(classname, sg_set);
					
				}
				else
				{
					HashSet<Subgraph> sg_set = new HashSet<Subgraph>();
					//System.out.println("Add "+sg_set.size());
					sg_set.add(sg);
					//System.out.println("Add "+sg_set.size());
					subgraph_map.put(classname, sg_set);
				}
			}
		}
	}
	catch (Exception e)
	{}
		
	}
	public static void main(String args[]) throws Exception, Throwable
	{
		IsolationInfo io = new IsolationInfo();
		io.getSingleExternalCallerSubgraph(args[2]);
//		JSONArray interfaces = io.getImplementedInterfaces("HoldingData");
//		io.getSuperclass("HoldingData");
//		String s1 = "Nenenene";
//		String s2 = "neq";
//		s1 = s1.replace("ne","neq");
//		System.out.println(s1);
	}
}
