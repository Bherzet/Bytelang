package bytelang.parser.lexical;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

import bytelang.parser.lexical.states.DefaultLexicalState;
import bytelang.parser.lexical.states.LexicalState;
import bytelang.parser.lexical.states.StateResult;

public class LexicalParser {
	private LinkedList<LexicalState> states       = new LinkedList<LexicalState>();
	private LexicalState             defaultState = new DefaultLexicalState();
	private LexicalState             currentState = defaultState;
	
	public void parse(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream);
		String  result  = "";
		
		while (scanner.hasNextLine()) {
			result += scanner.nextLine() + '\n';
		}
		
		scanner.close();
		parse(result);
	}
	
	public void parse(String input) {
		while (input.length() > 0) {
			StateResult result = currentState.match(input);
			
			if (result != null) {
				if (result.newState != currentState && currentState != defaultState) {
					states.add(currentState);
				}
				
				currentState = (result.newState != null) ? result.newState : defaultState;
				input        = result.processedInput;
			} else {
				throw new RuntimeException("Unknown element found.");
			}
		}
	}
	
	public LinkedList<LexicalState> getStates() {
		return states;
	}
}
