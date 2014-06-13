package bytelang.compiler.annotations.set.method;

import bytelang.classes.MethodInfo;
import bytelang.compiler.annotations.AnnotationSet.MethodCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class SetMethodMaxLocals implements MethodCommandProcessor {
	@Override
	public void apply(Value value, MethodInfo method) {
		if (value.getType() == ValueType.INTEGER) {
			int intValue = (int) ((ValueInteger) value).getValue();
			
			method.attributes[0].data[2] = (short) (intValue >> 8);
			method.attributes[0].data[3] = (short) (intValue & 0xFF);
		}
	}
}
