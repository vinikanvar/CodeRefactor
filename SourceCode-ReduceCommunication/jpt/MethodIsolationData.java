package jpt;

import java.util.HashSet;

public class MethodIsolationData {
	String classname;
	String method_name;
	HashSet<String> global_reads;
	HashSet<String> global_writes;
	public MethodIsolationData() {
		global_reads = new HashSet<String>();
		global_writes = new HashSet<String>();
		// TODO Auto-generated constructor stub
	}
}
