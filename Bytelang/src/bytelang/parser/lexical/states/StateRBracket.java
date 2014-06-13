package bytelang.parser.lexical.states;

public class StateRBracket extends OneCharState {
	public StateRBracket() {
		super(']', LexicalStateType.S_RBRACKET);
	}
}
