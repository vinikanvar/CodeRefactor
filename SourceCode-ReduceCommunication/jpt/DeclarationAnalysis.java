package jpt;

import java.util.Optional;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

public class DeclarationAnalysis {

	void traverseMethod(BodyDeclaration body)
	{
		BlockStmt method_block = null;
		MethodDeclaration method = body.asMethodDeclaration();
		Optional<BlockStmt> block = method.getBody();
		if (block.isPresent())
			method_block = block.get();
		else 
			return;
		NodeList<Statement> statements = method_block.getStatements();
		//System.out.println("*****************************************");
		for (Statement stmt :statements)
		{
			if (stmt.isTryStmt())
				System.out.println(" Statement: " + stmt);
		}
	}
}
