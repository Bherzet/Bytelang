package bytelang.compiler.annotations;

import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueReference;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationInterface extends BasicAnnotation implements Factory<AnnotationInterface> {
	private Value value;
	
	public AnnotationInterface() {
		this.addMandatoryParam("classIndex", new ValueType[]{ValueType.INTEGER, ValueType.STRING, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		this.value = elementAnnotation.getParameters().get("classIndex");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.INTERFACE;
	}
	
	@Override
	public AnnotationInterface newInstance() {
		return new AnnotationInterface();
	}
	
	public int toInterfaceNumber(ConstantPoolBuilder constantPoolBuilder) {
		if (value.getType() == ValueType.INTEGER) {
			return (int) ((ValueInteger) value).getValue();
		} else if (value.getType() == ValueType.STRING) {
			return constantPoolBuilder.addItemClass(((ValueString) value).getString());
		} else if (value.getType() == ValueType.REFERENCE) {
			return constantPoolBuilder.getCPItemIndexById(((ValueReference) value).getValue());
		} else {
			throw new RuntimeException("Internal error.");
		}
	}
}
