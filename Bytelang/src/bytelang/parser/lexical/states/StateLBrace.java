package bytelang.parser.lexical.states;

public class StateLBrace extends OneCharState {
	public StateLBrace() {
		super('{', LexicalStateType.S_LBRACE);
	}
}
