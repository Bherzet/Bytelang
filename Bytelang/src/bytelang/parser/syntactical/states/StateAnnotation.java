package bytelang.parser.syntactical.states;

import java.util.ArrayList;
import java.util.LinkedList;

import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueArray;
import bytelang.parser.lexical.states.LexicalState;
import bytelang.parser.lexical.states.LexicalStateType;
import bytelang.parser.lexical.states.ReadableState;
import bytelang.parser.lexical.states.StateWord;
import static bytelang.parser.syntactical.Utils.*;

public class StateAnnotation implements SyntacticalState {
	private ElementAnnotation elementAnnotation = null;
	
	@Override
	@SuppressWarnings("unused")
	public SyntacticalStateResult match(LinkedList<LexicalState> tokens) {
		LexicalState      s_at       = next(tokens, LexicalStateType.S_AT,           "S_AT was expected.");
		LexicalState      s_aname    = next(tokens, LexicalStateType.WORD,           "Name of the annotation was expected."); 
		LexicalState      s_lpar     = next(tokens, LexicalStateType.S_LPARENTHESIS, "Left parenthesis was expected in the annotation.");
		ElementAnnotation annotation = new ElementAnnotation(((StateWord) s_aname).getWord()); 
		
		boolean nextParam = false;
		while ((next(tokens, "Closing parenthesis expected in the annotation.").getType() != LexicalStateType.S_RPARENTHESIS) || nextParam) {
			nextParam = false;
			
			LexicalState s_pname = next(tokens, LexicalStateType.WORD,    "Closing parenthesis or parameter expected in the annotation.");
			LexicalState s_equal = next(tokens, LexicalStateType.S_EQUAL, "S_EQUAL was expected.");
			
			LexicalState s_value = next(tokens, new LexicalStateType[]{
				LexicalStateType.INTEGER, LexicalStateType.STRING, LexicalStateType.REFERENCE,
				LexicalStateType.S_LBRACKET,LexicalStateType.S_COMMA
			}, "Integer, string or array expected in the annotation's parameter.");
			
			if (s_value.getType() == LexicalStateType.S_LBRACKET) {
				ArrayList<Value> values    = new ArrayList<Value>();
				boolean          nextValue = false;
				
				while (next(tokens, "Closing parenthesis expected in the annotation.").getType() != LexicalStateType.S_RBRACKET || nextValue) {
					nextValue = false;
					
					LexicalState s_item = next(tokens, new LexicalStateType[]{
						LexicalStateType.INTEGER, LexicalStateType.STRING, LexicalStateType.S_COMMA
					}, "Integer or string expected in the array.");
					
					if (s_item.getType() == LexicalStateType.S_COMMA) {
						nextValue = true;
						continue;
					}

					values.add(((ReadableState) s_item).toTypedValue()); 
				}
				
				annotation.addParameter(((StateWord) s_pname).getWord(), new ValueArray(values));
				tokens.removeFirst();
			} else {
				annotation.addParameter(((StateWord) s_pname).getWord(), ((ReadableState) s_value).toTypedValue());
			}
			
			if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_COMMA) {
				nextParam = true;
				tokens.removeFirst();
				continue;
			} else {
				break;
			}
		}

		tokens.removeFirst();
		this.elementAnnotation = annotation;
		return new SyntacticalStateResult(tokens, null);
	}

	@Override
	public SyntacticalStateType getType() {
		return SyntacticalStateType.ANNOTATION;
	}
	
	public ElementAnnotation getElementAnnotation() {
		return elementAnnotation;
	}
}
