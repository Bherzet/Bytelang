package bytelang.parser.lexical.states;

public class StateRBrace extends OneCharState {
	public StateRBrace() {
		super('}', LexicalStateType.S_RBRACE);
	}
}
