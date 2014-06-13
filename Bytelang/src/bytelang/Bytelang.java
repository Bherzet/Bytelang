package bytelang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bytelang.classes.ClassFile;
import bytelang.compiler.Compiler;
import bytelang.parser.lexical.LexicalParser;
import bytelang.parser.syntactical.SyntacticalParser;

public class Bytelang {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: bytelang <inputFile> <outputFile>");
		} else {
			try {
				String            inputFileName     = args[0];
				String            outputFileName    = args[1];
	
				LexicalParser     lexicalParser     = new LexicalParser();
				SyntacticalParser syntacticalParser = new SyntacticalParser();
				
				lexicalParser.parse(new FileInputStream(inputFileName));
				syntacticalParser.parse(lexicalParser.getStates());
				
				Compiler          compiler          = new Compiler(syntacticalParser.getElementClass());
				ClassFile         classFile         = compiler.compile();
				
				classFile.saveTo(new FileOutputStream(outputFileName));
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (RuntimeException e) {
				System.err.println("Compilation error: " + e.getMessage());
			}
		}
	}
}
