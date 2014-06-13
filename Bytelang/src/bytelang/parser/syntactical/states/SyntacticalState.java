package bytelang.parser.syntactical.states;
import java.util.LinkedList;

import bytelang.parser.lexical.states.LexicalState;

public interface SyntacticalState {
	public SyntacticalStateResult match(LinkedList<LexicalState> tokens);
	public SyntacticalStateType   getType();
}
