package bytelang.parser.lexical.states;

public class StateRAngleBracket extends OneCharState {
	public StateRAngleBracket() {
		super('>', LexicalStateType.S_RANGLEBRACKET);
	}
}
