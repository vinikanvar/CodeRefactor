package jpt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.utils.Pair;

public class NeededByClasses {
	String classname;
	String pkg_name;
	boolean is_interface = false;
	HashSet<String> field_set;
	HashSet<MethodFieldUsage> field_usage;
	HashMap<String, HashSet<String>> internal_callers_of_M;
	HashMap<String, Pair<Integer,Integer>> method_scope;
	HashMap<String, HashSet<String>> internal_called_by_M;
	HashMap<String, HashSet<String>> dep_on_classes;
	HashMap<String, HashSet<String>> called_by_classes;
	HashMap<String,HashSet<Pair<String, String>>> external_callers_of_M;
	HashMap<String,HashSet<Pair<String, String>>> external_called_by_M;
	public NeededByClasses() {
		String pkg_name="";
		field_set = new HashSet<String>();
		field_usage = new HashSet<MethodFieldUsage>();
		internal_callers_of_M = new HashMap<String, HashSet<String>>();
		internal_called_by_M = new HashMap<String, HashSet<String>>();
		external_called_by_M = new HashMap<String,HashSet<Pair<String, String>>> ();
		external_callers_of_M = new HashMap<String,HashSet<Pair<String, String>>> ();
		dep_on_classes = new HashMap<String, HashSet<String>>();
		called_by_classes = new HashMap<String, HashSet<String>>();
		method_scope = new HashMap<String, Pair<Integer,Integer>>();
		// TODO Auto-generated constructor stub
	}
	

}
