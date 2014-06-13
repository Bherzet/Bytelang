package bytelang.tests.parser.lexical;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import bytelang.parser.lexical.LexicalParser;
import bytelang.parser.lexical.states.LexicalState;
import bytelang.parser.lexical.states.LexicalStateType;

public class Utils {
	public static boolean compareList(LinkedList<LexicalState> realStates, LexicalStateType[] expected) {
		if (realStates.size() != expected.length) {
			return false;
		}
		
		for (int i = 0; i < realStates.size(); i++) {
			if (realStates.get(i).getType() != expected[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean checkString(String string, LexicalStateType[] states) {
		LexicalParser lexicalParser = new LexicalParser();
		lexicalParser.parse(string);
		
		return compareList(lexicalParser.getStates(), states);
	}

	public static boolean checkFile(String file, LexicalStateType[] states) throws FileNotFoundException {
		LexicalParser lexicalParser = new LexicalParser();
		lexicalParser.parse(new FileInputStream(file));
		
		return compareList(lexicalParser.getStates(), states);
	}
}
