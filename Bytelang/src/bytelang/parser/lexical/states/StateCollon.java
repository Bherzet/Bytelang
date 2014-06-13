package bytelang.parser.lexical.states;

public class StateCollon extends OneCharState {
	public StateCollon() {
		super(':', LexicalStateType.S_COLLON);
	}
}
