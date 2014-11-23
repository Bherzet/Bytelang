package bytelang.parser.container.code;

import java.util.LinkedList;

import bytelang.CompilationErrorException;
import bytelang.parser.container.values.Value;
import bytelang.parser.syntactical.keywords.InstructionType;

public class SourceLineInstruction implements SourceLine {
	private InstructionType   instructionType = null;
	private LinkedList<Value> parameters      = new LinkedList<Value>();

	public SourceLineInstruction(InstructionType instructionType) {
		this.instructionType = instructionType;
	}
	
	@Override
	public SourceLineType getType() {
		return SourceLineType.INSTRUCTION;
	}
	
	public void addParameter(Value parameter) {
		this.parameters.add(parameter);
	}
	
	public InstructionType getInstructionType() {
		return instructionType;
	}
	
	public LinkedList<Value> getParameters() {
		return parameters;
	}
	
	public int getInstructionSize() {
		int result = 1;
		
		for (int i = 0; i < parameters.size(); i++) {
			int paramSize = instructionType.getPrefferedOperandSize(i);
			
			if (paramSize != -1) {
				result += paramSize;
			} else {
				switch (parameters.get(i).getType()) {
					case INTEGER:   result += 1; break;
					case LABEL:     result += 2; break;
					case REFERENCE: result += 2; break;

					case ARRAY:
					case STRING:
						throw new CompilationErrorException(
							"Parameter of a type " + parameters.get(i).getType() + " is not acceptable for the instruction."
						);
				}
			}
		}
		
		return result;
	}
}
