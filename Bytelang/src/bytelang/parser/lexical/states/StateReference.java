package bytelang.parser.lexical.states;

import bytelang.CompilationErrorException;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueReference;

public class StateReference implements LexicalState, ReadableState {
	private String value = "";
	
	@Override
	public StateResult match(String input) {
		if (input.charAt(0) == '#') {
			input = input.substring(1);
			
			value = "";
			while (input.length() > 0 && Character.isLetterOrDigit(input.charAt(0))) {
				value += input.charAt(0);
				input     = input.substring(1);
			}
		
			if (value.length() == 0) {
				throw new CompilationErrorException("Missing reference name.");
			}
			
			return new StateResult(input, null);
		}
		
		return null;
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.REFERENCE;
	}

	@Override
	public Value toTypedValue() {
		return new ValueReference(value);
	}
	
	@Override
	public String toString() {
		return "#" + value;
	}
}
