package jpt;

import java.util.HashMap;
import java.util.HashSet;

import com.github.javaparser.utils.Pair;

public class Subgraph {
	 HashSet<String> subgraph; 
	 String dst_cluster;
	 String dst_class; 
	 HashMap<String, HashSet<Pair<String, String>>> caller_map; 
	 HashMap<String, HashSet<Pair<String, String>>> called_map;
	 HashSet<Pair<String,String>> field_written;
	 HashSet<Pair<String,String>> field_read;
	 public Subgraph()
	 {
		dst_class = "";
		subgraph = new HashSet<String>();
		caller_map = new HashMap<String, HashSet<Pair<String, String>>>();
		called_map = new HashMap<String, HashSet<Pair<String, String>>>();
		field_read = new HashSet<Pair<String,String>>();
		field_written = new HashSet<Pair<String,String>>();
	 }
}
