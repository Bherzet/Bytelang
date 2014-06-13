package bytelang.parser.lexical.states;

public class StateSemicoln extends OneCharState {
	public StateSemicoln() {
		super(';', LexicalStateType.S_SEMICOLN);
	}
}
