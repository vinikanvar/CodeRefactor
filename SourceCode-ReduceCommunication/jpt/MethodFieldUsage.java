package jpt;

import java.util.HashSet;

import com.github.javaparser.utils.Pair;

public class MethodFieldUsage {
String method;
HashSet<Pair<String,String>> global_reads;
HashSet<Pair<String,String>> global_writes;
HashSet<String> local_vars;
	public MethodFieldUsage() {
		method = "";
		global_reads = new HashSet<Pair<String,String>>();
		global_writes = new HashSet<Pair<String,String>>();
		local_vars = new HashSet<String>();
		// TODO Auto-generated constructor stub
	}
}
