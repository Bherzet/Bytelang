package bytelang.parser.lexical.states;

public class StateRParenthesis extends OneCharState {
	public StateRParenthesis() {
		super(')', LexicalStateType.S_RPARENTHESIS);
	}
}
