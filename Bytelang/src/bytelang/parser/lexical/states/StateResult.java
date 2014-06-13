package bytelang.parser.lexical.states;

public class StateResult {
	public final String processedInput;
	public final LexicalState  newState;
	
	public StateResult(String processedInput, LexicalState newState) {
		this.processedInput = processedInput;
		this.newState       = newState;
	}
}
