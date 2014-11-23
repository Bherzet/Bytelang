package bytelang.compiler.annotations.set.classFile;

import java.util.ArrayList;

import bytelang.CompilationErrorException;
import bytelang.classes.ClassFile;
import bytelang.classes.Version;
import bytelang.compiler.annotations.AnnotationSet.ClassCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueArray;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class SetClassFileVersion implements ClassCommandProcessor {
	@Override
	public void apply(Value value, ClassFile classFile) {
		if (value.getType() == ValueType.ARRAY) {
			ValueArray       valueArray = (ValueArray) value;
			ArrayList<Value> values     = valueArray.getValues();
			
			if (values.size() != 2) {
				throw new CompilationErrorException(
					"Annotation @set for the value \"version\" expects an array of exactly 2 integers."
				);
			}
			
			int vals[] = new int[2];
			for (int i = 0; i < 2; i++) {
				if (values.get(i).getType() != ValueType.INTEGER) {
					throw new CompilationErrorException(
						"Annotation @set for the value \"version\" expects an array of integer values."
					);
				}
				
				vals[i] = (int) ((ValueInteger) values.get(i)).getValue();
			}
			
			classFile.version = new Version(vals[1], vals[0]);
		} else if (value.getType() == ValueType.STRING) {
			String version = ((ValueString) value).getString();
			switch (version) {
				case "1.1": classFile.version = new Version(0, 45); break;
				case "1.2": classFile.version = new Version(0, 46); break;
				case "1.3": classFile.version = new Version(0, 47); break;
				case "1.4": classFile.version = new Version(0, 48); break;
				case "1.5": classFile.version = new Version(0, 49); break;
				case "1.6": classFile.version = new Version(0, 50); break;
				case "1.7": classFile.version = new Version(0, 51); break;
				case "1.8": classFile.version = new Version(0, 52); break;
				default:
					throw new CompilationErrorException(
						"Unrecognized version has been passed to the annotation @set for the value \"version\"."
					);
			}
		}
	}
}
