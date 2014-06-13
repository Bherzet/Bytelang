package bytelang.parser.lexical.states;

public class StateLineComment implements LexicalState {
	@Override
	public StateResult match(String input) {
		input = input.substring(2);

		int index = input.indexOf('\n');
		if (index == -1) {
			index = input.length();
		}
		
		return new StateResult(input.substring(index), null);
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.LINE_COMMENT;
	}
}
