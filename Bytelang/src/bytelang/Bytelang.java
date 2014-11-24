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
		if (args.length == 0 || args.length > 2) {
			printHelp();
		} else if (args.length == 1) {
			switch (args[0]) {
				case "-license": printLicense(); return;
				default:         printHelp();    return;
			}
		} else if (args.length == 2) {
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
				System.err.printf("File not found: %s\n", e.getMessage());
			} catch (IOException e) {
				System.err.println("Fatal error:");
				System.err.println(e.getMessage());
			} catch (CompilationErrorException e) {
				System.err.println("Compilation error: " + e.getMessage());
			}
		}
	}

	private static void printHelp() {
		System.out.println("Bytelang (version 2.0.0) by Tomas Zima");
		System.out.println("Usage: ");
		System.out.println("  bytelang <inputFile> <outputFile>");
		System.out.println("  bytelang -license");
	}

	private static void printLicense() {
		System.out.println("Copyright (c) 2014, Tomáš Zíma. All rights reserved.");
        System.out.println();
		System.out.println("Redistribution and use in source and binary forms, with or without");
		System.out.println("modification, are permitted provided that the following conditions are met:");
        System.out.println();
		System.out.println("* Redistributions of source code must retain the above copyright notice, this");
		System.out.println("  list of conditions and the following disclaimer.");
        System.out.println();
		System.out.println("* Redistributions in binary form must reproduce the above copyright notice,");
		System.out.println("  this list of conditions and the following disclaimer in the documentation");
		System.out.println("  and/or other materials provided with the distribution.");
        System.out.println();
		System.out.println("* Neither the name of Bytelang nor the names of its");
		System.out.println("  contributors may be used to endorse or promote products derived from");
		System.out.println("  this software without specific prior written permission.");
        System.out.println();
		System.out.println("THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"");
		System.out.println("AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE");
		System.out.println("IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE");
		System.out.println("DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE");
		System.out.println("FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL");
		System.out.println("DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR");
		System.out.println("SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER");
		System.out.println("CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,");
		System.out.println("OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE");
		System.out.println("OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
	}
}
