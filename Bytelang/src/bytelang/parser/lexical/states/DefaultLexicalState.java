package bytelang.parser.lexical.states;

public class DefaultLexicalState implements LexicalState {
	@Override
	public StateResult match(String input) {
		char first = input.charAt(0);
		
		if (Character.isWhitespace(first)) {
			return new StateResult(input.substring(1), null);
		}

		if (input.startsWith("//")) return new StateResult(input, new StateLineComment());
		if (input.startsWith("/*")) return new StateResult(input, new StateBlockComment());
		
		switch (first) {
			case '@':  return new StateResult(input, new StateAt());
			case ',':  return new StateResult(input, new StateComma());
			case ';':  return new StateResult(input, new StateSemicoln());
			case ':':  return new StateResult(input, new StateCollon());
			case '.':  return new StateResult(input, new StateDot());
			case '=':  return new StateResult(input, new StateEqual());
			case '#':  return new StateResult(input, new StateReference());
			case '<':  return new StateResult(input, new StateLAngleBracket());
			case '>':  return new StateResult(input, new StateRAngleBracket());
			case '(':  return new StateResult(input, new StateLParenthesis());
			case ')':  return new StateResult(input, new StateRParenthesis());
			case '[':  return new StateResult(input, new StateLBracket());
			case ']':  return new StateResult(input, new StateRBracket());
			case '{':  return new StateResult(input, new StateLBrace());
			case '}':  return new StateResult(input, new StateRBrace());
			case '"':  return new StateResult(input, new StateString());
		}

		if (Character.isDigit(first)) {
			return new StateResult(input, new StateInteger());
		} else if (first >= 'A' && first <= 'z'){
			return new StateResult(input, new StateWord());
		} else {
			return null;
		}
	}

	@Override
	public LexicalStateType getType() {
		return LexicalStateType.DEFAULT;
	}
}
