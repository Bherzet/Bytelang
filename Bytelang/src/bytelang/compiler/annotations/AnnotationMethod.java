package bytelang.compiler.annotations;

import bytelang.classes.Attribute;
import bytelang.classes.MethodInfo;
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

public class AnnotationMethod extends BasicAnnotation implements Factory<AnnotationMethod> {
	private int   accessFlags = 0;
	private Value name        = null;
	private Value descriptor  = null;
	
	public AnnotationMethod() {
		this.addMandatoryParam("accessFlags", new ValueType[]{ValueType.INTEGER});
		this.addMandatoryParam("name", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE, ValueType.STRING});
		this.addMandatoryParam("descriptor", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE, ValueType.STRING});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.accessFlags = (int) ((ValueInteger) elementAnnotation.getParameters().get("accessFlags")).getValue();
		this.name        = elementAnnotation.getParameters().get("name");
		this.descriptor  = elementAnnotation.getParameters().get("descriptor");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.METHOD;
	}

	@Override
	public AnnotationMethod newInstance() {
		return new AnnotationMethod();
	}
	
	public int getAccessFlags() {
		return accessFlags;
	}
	
	public Value getName() {
		return name;
	}
	
	public Value getDescriptor() {
		return descriptor;
	}
	
	public MethodInfo toMethodInfo(ConstantPoolBuilder constantPoolBuilder) {
		int accessFlags     = this.accessFlags;
		int nameIndex       = 0;
		int descriptorIndex = 0;
		
		if (this.name.getType() == ValueType.INTEGER) {
			nameIndex = (int) ((ValueInteger) name).getValue();
		} else if (this.name.getType() == ValueType.STRING) {
			nameIndex = constantPoolBuilder.addItemUtf8(((ValueString) name).getString());
		} else if (this.name.getType() == ValueType.REFERENCE) {
			nameIndex = constantPoolBuilder.getCPItemIndexById(((ValueReference) name).getValue());
		} else {
			throw new RuntimeException();
		}
		
		if (this.descriptor.getType() == ValueType.INTEGER) {
			descriptorIndex = (int) ((ValueInteger) descriptor).getValue();
		} else if (this.descriptor.getType() == ValueType.STRING) {
			descriptorIndex = constantPoolBuilder.addItemUtf8(((ValueString) descriptor).getString());
		} else if (this.descriptor.getType() == ValueType.REFERENCE) {
			descriptorIndex = constantPoolBuilder.getCPItemIndexById(((ValueReference) descriptor).getValue());
		} else {
			throw new RuntimeException();
		}

		return new MethodInfo(accessFlags, nameIndex, descriptorIndex, 0, new Attribute[0]);
	}
}
