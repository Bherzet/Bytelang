package bytelang.compiler.annotations.set.classFile;

import java.util.ArrayList;

import bytelang.CompilationErrorException;
import bytelang.classes.ClassFile;
import bytelang.compiler.annotations.AnnotationSet.ClassCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueArray;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class SetClassFileMagic implements ClassCommandProcessor {
	@Override
	public void apply(Value value, ClassFile classFile) {
		ValueArray       valueArray = (ValueArray) value;
		ArrayList<Value> values    = valueArray.getValues();
		
		if (values.size() != 4) {
			throw new CompilationErrorException(
				"Annotation @set for the value \"magic\" expects an array with exactly 4 integers."
			);
		}
		
		classFile.magic = new short[4];
		for (int i = 0; i < 4; i++) {
			if (values.get(i).getType() != ValueType.INTEGER) {
				throw new CompilationErrorException("Annotation @set for the value \"magic\" expects an array of integers.");
			}
			
			classFile.magic[i] = (short) ((ValueInteger) values.get(i)).getValue();
		}
	}
}
