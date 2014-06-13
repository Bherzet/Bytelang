package bytelang.parser.lexical.states;

public class StateLParenthesis extends OneCharState {
	public StateLParenthesis() {
		super('(', LexicalStateType.S_LPARENTHESIS);
	}
}
