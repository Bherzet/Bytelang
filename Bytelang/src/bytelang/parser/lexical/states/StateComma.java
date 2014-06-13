package bytelang.parser.lexical.states;

public class StateComma extends OneCharState {
	public StateComma() {
		super(',', LexicalStateType.S_COMMA);
	}
}
