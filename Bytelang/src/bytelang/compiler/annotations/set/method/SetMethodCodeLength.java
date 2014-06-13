package bytelang.compiler.annotations.set.method;

import bytelang.classes.MethodInfo;
import bytelang.compiler.annotations.AnnotationSet.MethodCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class SetMethodCodeLength implements MethodCommandProcessor {
	@Override
	public void apply(Value value, MethodInfo method) {
		if (value.getType() == ValueType.INTEGER) {
			int intValue = (int) ((ValueInteger) value).getValue();
			
			method.attributes[0].data[4] = (short) (intValue >> 24);
			method.attributes[0].data[5] = (short) ((intValue >> 16) & 0xFF);
			method.attributes[0].data[6] = (short) ((intValue >> 8) & 0xFF);
			method.attributes[0].data[7] = (short) (intValue & 0xFF);
		}
	}
}
