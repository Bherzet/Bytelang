package bytelang.parser.syntactical;

import java.util.ArrayList;
import java.util.LinkedList;

import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.elements.ElementClass;
import bytelang.parser.lexical.states.LexicalState;
import bytelang.parser.syntactical.states.DefaultSyntacticalState;
import bytelang.parser.syntactical.states.StateAnnotation;
import bytelang.parser.syntactical.states.StateClassDeclaration;
import bytelang.parser.syntactical.states.SyntacticalState;
import bytelang.parser.syntactical.states.SyntacticalStateResult;
import bytelang.parser.syntactical.states.SyntacticalStateType;

public class SyntacticalParser {
	private SyntacticalState defaultState = new DefaultSyntacticalState();
	private SyntacticalState currentState = defaultState;
	private ElementClass     elementClass = null;
	
	private static LinkedList<LexicalState> removeComments(LinkedList<LexicalState> tokens) {
		LinkedList<LexicalState> result = new LinkedList<LexicalState>();
		
		for (int i = 0; i < tokens.size(); i++) {
			switch (tokens.get(i).getType()) {
				case LINE_COMMENT:
				case BLOCK_COMMENT:
					break;
					
				default:
					result.add(tokens.get(i));
					continue;
			}
		}
		
		return result;
	}
	
	public void parse(LinkedList<LexicalState> tokens) {
		tokens                                   = removeComments(tokens);
		ArrayList<ElementAnnotation> annotations = new ArrayList<ElementAnnotation>();

		while (tokens.size() > 0) {
			SyntacticalStateResult result = currentState.match(tokens);
			
			if (result != null) {
				tokens = result.processedTokens;

				if (this.currentState != defaultState && this.currentState != result.newState) {
					if (this.currentState.getType() == SyntacticalStateType.ANNOTATION) {
						annotations.add(((StateAnnotation) this.currentState).getElementAnnotation());
					} else if (this.currentState.getType() == SyntacticalStateType.CLASS_DECLARATION) {
						elementClass = ((StateClassDeclaration) currentState).getElementClass();
						
						for (ElementAnnotation annotation : annotations) {
							elementClass.addAnnotation(annotation);
						}
						
						annotations.clear();
					}
				}
				
				if (result.newState == null) {
					this.currentState = defaultState;
				} else {
					this.currentState = result.newState;
				}
			} else {
				throw new RuntimeException("Unexpected token found.");
			}
		}
	}
	
	public ElementClass getElementClass() {
		return elementClass;
	}
}
