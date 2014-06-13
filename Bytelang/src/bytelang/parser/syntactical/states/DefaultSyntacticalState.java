package bytelang.parser.syntactical.states;

import java.util.LinkedList;

import bytelang.parser.lexical.states.LexicalState;

public class DefaultSyntacticalState implements SyntacticalState {
	@Override
	public SyntacticalStateResult match(LinkedList<LexicalState> tokens) {
		if (tokens.size() == 0) {
			return null;
		}
		
		switch (tokens.getFirst().getType()) {
			case S_AT: return new SyntacticalStateResult(tokens, new StateAnnotation());
			
			default:
				return new SyntacticalStateResult(tokens, new StateClassDeclaration());
		}
	}

	@Override
	public SyntacticalStateType getType() {
		return SyntacticalStateType.DEFAULT;
	}
}
