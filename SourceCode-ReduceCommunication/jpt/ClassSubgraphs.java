package jpt;

import java.util.HashSet;

public class ClassSubgraphs {
	String classname;
	String pkg_name;
	boolean is_interface;
	HashSet<Subgraph> all_subgraphs;
	HashSet<String> fields;
	public ClassSubgraphs()
	{
		is_interface = false;
		all_subgraphs = new HashSet<Subgraph>();
		pkg_name = "";
		classname = "";
		fields = new HashSet<String>();
	}
}
