package bytelang.compiler.annotations.set.method;

import bytelang.classes.MethodInfo;
import bytelang.compiler.annotations.AnnotationSet.MethodCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class SetMethodAttributesCount implements MethodCommandProcessor {
	@Override
	public void apply(Value value, MethodInfo method) {
		if (value.getType() == ValueType.INTEGER) {
			method.attributesCount = (int) ((ValueInteger) value).getValue();
		}
	}
}
