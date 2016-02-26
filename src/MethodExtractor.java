import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

public class MethodExtractor {
	public MethodExtractor() {
		
	}
	
	public ArrayList<JavaMethod> extractMethod(String filePath)
			throws FileNotFoundException, com.github.javaparser.ParseException {
		FileInputStream in = new FileInputStream(filePath);
		CompilationUnit cu;
		ArrayList<JavaMethod> methodList = new ArrayList<JavaMethod>();
		// parse the file
		cu = JavaParser.parse(in);
		int count = 0;
		List<TypeDeclaration> typeDeclarations = cu.getTypes();
		for (TypeDeclaration typeDec : typeDeclarations) {
			List<BodyDeclaration> members = typeDec.getMembers();
			if (members != null) {
				for (BodyDeclaration member : members) {
					// extract the constructors
					if (member instanceof ConstructorDeclaration) {
						ConstructorDeclaration constructor = (ConstructorDeclaration) member;
						JavaMethod jm = new JavaMethod(count, filePath, constructor.getName(),
								constructor.getDeclarationAsString() + constructor.getBlock(),
								constructor.getBeginLine(), constructor.getEndLine(), "");
						methodList.add(jm);
						count++;
						// extract all the methods
					} else if (member instanceof MethodDeclaration) {
						MethodDeclaration method = (MethodDeclaration) member;
						JavaMethod jm = new JavaMethod(count, filePath, method.getName(),
								method.getDeclarationAsString() + method.getBody().toString(), method.getBeginLine(),
								method.getEndLine(), "");
						methodList.add(jm);
						count++;
					}
				}
			}
		}

		return methodList;
	}
}
