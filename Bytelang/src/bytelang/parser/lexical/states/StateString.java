package bytelang.parser.lexical.states;

import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueString;

public class StateString implements LexicalState, ReadableState {
	private String value;
	
	@Override
	public StateResult match(String input) {
		if (input.charAt(0) != '"') {
			throw new RuntimeException("Opening quote ('\"') expected in the string literal.");
		}
		
		int index = input.indexOf('"', 1);
		if (index == -1) {
			throw new RuntimeException("Unclosed string literal.");
		}

		this.value = input.substring(1, index);
		return new StateResult(input.substring(index + 1), null);
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.STRING;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public Value toTypedValue() {
		return new ValueString(value);
	}
}
