package bytelang.parser.lexical.states;

public class StateEqual extends OneCharState {
	public StateEqual() {
		super('=', LexicalStateType.S_EQUAL);
	}
}
