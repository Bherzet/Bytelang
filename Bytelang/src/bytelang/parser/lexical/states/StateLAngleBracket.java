package bytelang.parser.lexical.states;

public class StateLAngleBracket extends OneCharState {
	public StateLAngleBracket() {
		super('<', LexicalStateType.S_LANGLEBRACKET);
	}
}
