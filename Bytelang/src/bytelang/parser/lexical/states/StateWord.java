package bytelang.parser.lexical.states;

public class StateWord implements LexicalState {
	private String word;
	
	@Override
	public StateResult match(String input) {
		String word = "";
		
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) || c == '_' || Character.isDigit(c)) {
				word += c;
			} else {
				break;
			}
		}
		
		this.word = word;
		return new StateResult(input.substring(word.length()), null);
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.WORD;
	}
	
	public String getWord() {
		return word;
	}
	
	@Override
	public String toString() {
		return word;
	}
}
