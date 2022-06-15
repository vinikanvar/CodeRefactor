package jpt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.imageio.IIOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseCluster {
	JSONParser jsonParser = new JSONParser();
	HashMap<String,String> class_id = new HashMap<String,String>();
	HashMap<String,String> inter_class_connections = new HashMap<String,String>();
	HashMap<String,String> transaction_relatedeness = new HashMap<String,String>();
	HashMap<String,HashSet<String>> node_cluster = new HashMap<String,HashSet<String>>();
	
	
	boolean belongstoDiffClusters(String class1, String class2)
	{
		boolean result = false;
		HashSet<String> set1 = getClusters(class1);
		HashSet<String> set2 = getClusters(class2);
		for (String s: set1)
		{
			if (!set2.contains(s))
			{
				return true;
			}
		}
		return result;
	}
	
	public String getCluster(String name)
	{
		String cluster = "";
		for (HashMap.Entry<String,HashSet<String>> entry : node_cluster.entrySet())
		{
			for (String c_name : entry.getValue())
			{ 
				if (c_name.equals(name))
				{
					return entry.getKey();
					//System.out.println("ADDED for "+ name +": "+entry.getKey());
				}
			}
		}
		return cluster;
	}
	public HashSet<String> getClusters(String name)
	{
		int count = 0;
		HashSet<String> set = new HashSet<String>();
		for (HashMap.Entry<String,HashSet<String>> entry : node_cluster.entrySet())
		{
			for (String c_name : entry.getValue())
			{ 
				if (c_name.equals(name))
				{
					count++;
					set.add(entry.getKey());
//					System.out.println("ADDED for "+ name +": "+entry.getKey());
					break;
				}
			}
		}
		//if (count != 8)
		return set;
	}
	String getNodeID(String classname)
	{
		String nodeID = "";
		for (Entry<String, String> map : class_id.entrySet())
		{
			if (map.getValue().equals(classname))
				return map.getKey();
		}
		return nodeID;
	}
	
	
	HashSet<String> listClusters()
	{
		HashSet<String> set = new HashSet<>();
		for (HashMap.Entry<String,HashSet<String>> entry : node_cluster.entrySet())
		{
			//System.out.println("Cluster ID " + entry.getKey());
			set.add(entry.getKey());
		}
		return set;
	}

	HashSet<String> listClusterNodes(String label)
	{
		HashSet<String> set = new HashSet<>();
		for (HashMap.Entry<String,HashSet<String>> entry : node_cluster.entrySet())
		{
			if (entry.getKey().equals(label))
			{
				for (String s : entry.getValue())
				{
					set.add(s);
				}
			}
		}
		return set;
	}
	
	public void parseClusters(String path) throws IOException, ParseException
	{
		try {
			FileReader reader= new FileReader(path+"/graph_clustering.json");
			Object obj = jsonParser.parse(reader);
			
			JSONObject jsonObject = (JSONObject)obj;
			JSONArray nodes = (JSONArray)jsonObject.get("nodes");
			//String name = (String)jsonObject.get("nodes");
			JSONArray clusters = (JSONArray)jsonObject.get("clusters");
			JSONArray edges = (JSONArray)jsonObject.get("edges");
			
			
			for (Object node : nodes)
			{
				//System.out.println("*************************************");
				JSONObject jsonNode = (JSONObject)node;
				JSONObject properties = (JSONObject)jsonNode.get("properties");
				String label = (String)jsonNode.get("label");
				String id = (String)jsonNode.get("id");
				if (label.contains(".") && !(label.contains("http://")))
					label = label.substring(label.lastIndexOf('.')+1);
				if(!label.contains("http://"))
					class_id.put(id, label);
				//System.out.println(id+" "+label);
				//String name = (String)node.get("entity_types");
			}
			for (Object edge : edges)
			{
				JSONObject jsonEdge = (JSONObject)edge;
				String type = (String)jsonEdge.get("type");
				JSONArray relation = (JSONArray)jsonEdge.get("relationship");
				if (type.equals("inter_class_connections"))
				{
					for (Object rel : relation)
					{
						JSONArray reln = (JSONArray)relation;
						for (Object rel_item : reln)
						{
							JSONObject rel_object = (JSONObject)rel_item;
							JSONObject properties = (JSONObject)rel_object.get("properties");
							String start = (String)properties.get("start");
							String end = (String)properties.get("end");
							inter_class_connections.put(class_id.get(start), class_id.get(end));
						}
						
					}
				}
				else if (type.equals("transaction_relatedeness"))
				{
					for (Object rel : relation)
					{
						JSONArray reln = (JSONArray)relation;
						for (Object rel_item : reln)
						{
							JSONObject rel_object = (JSONObject)rel_item;
							JSONObject properties = (JSONObject)rel_object.get("properties");
							String start = (String)properties.get("start");
							String end = (String)properties.get("end");
							transaction_relatedeness.put(class_id.get(start), class_id.get(end));
						}
					}
				}
			}
			
			
			int i=0;
			for (Object cluster:clusters)
			{
				i++;
				JSONObject jsonCluster = (JSONObject)cluster;
				JSONArray nds = (JSONArray)jsonCluster.get("nodes");
				String label = (String)jsonCluster.get("label");
				HashSet<String> set = new HashSet<String>();
				for (Object nd : nds)
				{
					String nd_id = (String)nd;
					String c_name = class_id.get(nd_id);
					try {
						if(!c_name.contains("http://"))
							set.add(c_name);
					}
					catch (Exception e)
					{}
				}
				node_cluster.put(label, set);
			}
			
			
		}
		catch (FileNotFoundException e)
		{}
	}
}
