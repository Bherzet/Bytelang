package bytelang.parser.lexical.states;

public class StateDot extends OneCharState {
	protected StateDot() {
		super('.', LexicalStateType.S_DOT);
	}
}
