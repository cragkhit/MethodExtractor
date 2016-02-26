import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.github.javaparser.ParseException;

public class ExtractorMain {
	public static void main(String[] args) {
		ArrayList<JavaMethod> methodList = new ArrayList<JavaMethod>();
		MethodExtractor extractor = new MethodExtractor();
			
		try {
			methodList = extractor.extractMethod(args[0]);
			// write extracted method to a destination folder
			for (int i=0; i<methodList.size(); i++) {
				JavaMethod method = methodList.get(i);
				String outfile = args[1] + "/" + args[0].replace(".java", "") + "$" + method.getMethodName() + ".java";
				FileWriter fw = new FileWriter(outfile);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("public" + method.getMethodName() + " {" + method.getMethod() + "}");
				bw.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
