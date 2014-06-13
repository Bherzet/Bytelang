package bytelang.compiler.annotations.set.field;

import bytelang.classes.FieldInfo;
import bytelang.compiler.annotations.AnnotationSet.FieldCommandProcessor;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class SetFieldAttributesCount implements FieldCommandProcessor {
	@Override
	public void apply(Value value, FieldInfo field) {
		if (value.getType() == ValueType.INTEGER) {
			field.attributesCount = (int) ((ValueInteger) value).getValue();
		}
	}
}
