package bytelang.parser.lexical.states;

public interface LexicalState {
	public StateResult match(String input);
	public LexicalStateType   getType();
}
