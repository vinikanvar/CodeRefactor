package jpt;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.utils.Pair;

public class ClassDetails {
 
	String name;
	boolean is_interface;
	int total_methods;
	int total_fields;
	int total_constructors;
	List<MethodDeclaration> all_methods;
	List<FieldDeclaration> all_fields;
	List<ConstructorDeclaration> all_constructors;
	ClassDetails()
	{
		total_methods = 0;
		total_fields = 0;
		total_constructors = 0;
		all_constructors = new ArrayList<ConstructorDeclaration>();
		all_fields = new ArrayList<FieldDeclaration>();
		all_methods = new ArrayList<MethodDeclaration>();
		is_interface = false;
	}
}
