package bytelang.parser.lexical.states;

public class StateBlockComment implements LexicalState {
	@Override
	public StateResult match(String input) {
		if (!input.startsWith("/*")) {
			return null;
		}
		
		input = input.substring(2);
		
		int index = input.indexOf("*/");
		if (index == -1) {
			throw new RuntimeException("Block comment should be closed.");
		}
		
		return new StateResult(input.substring(index + 2), null);
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.BLOCK_COMMENT;
	}
}
