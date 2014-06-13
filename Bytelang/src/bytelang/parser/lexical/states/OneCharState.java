package bytelang.parser.lexical.states;

public class OneCharState implements LexicalState {
	private final char      character;
	private final LexicalStateType stateType;
	
	protected OneCharState(char character, LexicalStateType stateType) {
		this.character = character;
		this.stateType = stateType;
	}
	
	@Override
	public StateResult match(String input) {
		if (input.charAt(0) == character) {
			return new StateResult(input.substring(1), null);
		} else {
			return null;
		}
	}

	@Override
	public LexicalStateType getType() {
		return stateType;
	}
}
