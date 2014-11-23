package bytelang.compiler.annotations.set.classFile;

import bytelang.CompilationErrorException;
import bytelang.classes.ClassFile;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemType;
import bytelang.compiler.annotations.AnnotationSet.ClassCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueReference;
import bytelang.parser.container.values.ValueType;

public class SetClassFileThisClass implements ClassCommandProcessor {
	@Override
	public void apply(Value value, ClassFile classFile) {
		if (value.getType() == ValueType.INTEGER) {
			classFile.thisClass = (int) ((ValueInteger) value).getValue();
		} else if (value.getType() == ValueType.REFERENCE) {
			String   name    = ((ValueReference) value).getValue();
			int      shift   = 1;
			CPItem[] cpItems = classFile.constantPoolItems;
			
			for (int i = 0; i < cpItems.length; i++) {
				if (cpItems[i].identifier != null && cpItems[i].identifier.equals(name)) {
					classFile.thisClass = i + shift;
					return;
				} else if (cpItems[i].getType() == CPItemType.LONG || cpItems[i].getType() == CPItemType.DOUBLE) {
					shift += 2;
				}
			}
			
			throw new CompilationErrorException(
				"Annotation @set for the value \"thisClass\" reffers to an constant-pool item which doesn't exist."
			);
		}
	}
}
