package bytelang.parser.syntactical.states;

import java.util.LinkedList;

import bytelang.parser.lexical.states.LexicalState;

public class SyntacticalStateResult {
	public final LinkedList<LexicalState>   processedTokens;
	public final SyntacticalState           newState;

	public SyntacticalStateResult(LinkedList<LexicalState> processedTokens, SyntacticalState newState) {
		this.processedTokens = processedTokens;
		this.newState        = newState;
	}
}
