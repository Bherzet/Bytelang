package bytelang.parser.lexical.states;

import bytelang.CompilationErrorException;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;

public class StateInteger implements LexicalState, ReadableState {
	private long value;
	
	@Override
	public StateResult match(String input) {
		boolean hexadecimal = input.startsWith("0x");
		String  word        = "";
		
		if (hexadecimal) {
			input = input.substring(2);
		}
		
		while (
			input.length() > 0 &&
				Character.isDigit(input.charAt(0)) ||
				(hexadecimal && (
					(input.charAt(0) >= 'A' && input.charAt(0) <= 'Z') ||
					(input.charAt(0) >= 'a' && input.charAt(0) <= 'z')
				))
		) {
			word  += input.charAt(0);
			input  = input.substring(1);
		}
		
		try {
			value = Long.valueOf(word, hexadecimal ? 16 : 10);
			return new StateResult(input, null);
		} catch (NumberFormatException e) {
			throw new CompilationErrorException("Internal error: String couldn't be converted to an integer value.");
		}
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.INTEGER;
	}
	
	public long getValue() {
		return value;
	}

	@Override
	public Value toTypedValue() {
		return new ValueInteger(value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
