package bytelang.parser.lexical.states;

public class StateLBracket extends OneCharState {
	public StateLBracket() {
		super('[', LexicalStateType.S_LBRACKET);
	}
}
