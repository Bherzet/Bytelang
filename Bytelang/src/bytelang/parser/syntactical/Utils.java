package bytelang.parser.syntactical;

import java.util.LinkedList;

import bytelang.parser.lexical.states.LexicalState;
import bytelang.parser.lexical.states.LexicalStateType;

public abstract class Utils {
	public static LexicalState next(LinkedList<LexicalState> tokens, LexicalStateType type, String message) {
		if (tokens.size() > 0) {
			LexicalState lexicalState = tokens.removeFirst();
			
			if (lexicalState.getType() == type) {
				return lexicalState;
			}
		}
		
		throw new RuntimeException(message);
	}
	
	public static LexicalState next(LinkedList<LexicalState> tokens, String message) {
		if (tokens.size() > 0) {
			return tokens.getFirst();
		}
		
		throw new RuntimeException(message);
	}
	
	public static LexicalState next(LinkedList<LexicalState> tokens, LexicalStateType[] types, String message) {
		if (tokens.size() > 0) {
			LexicalState first = tokens.removeFirst();
			
			for (LexicalStateType type : types) {
				if (first.getType() == type) {
					return first;
				}
			}
		}
		
		throw new RuntimeException(message);
	}
}
