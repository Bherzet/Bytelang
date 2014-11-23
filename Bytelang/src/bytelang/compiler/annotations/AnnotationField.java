package bytelang.compiler.annotations;

import bytelang.CompilationErrorException;
import bytelang.classes.Attribute;
import bytelang.classes.FieldInfo;
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

public class AnnotationField extends BasicAnnotation implements Factory<AnnotationField> {
	private int   accessFlags;
	private Value name;
	private Value descriptor;

	public AnnotationField() {
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
		return AnnotationType.FIELD;
	}

	@Override
	public AnnotationField newInstance() {
		return new AnnotationField();
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
	
	public FieldInfo toFieldInfo(ConstantPoolBuilder constantPoolBuilder) {
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
			throw new CompilationErrorException();
		}
		
		if (this.descriptor.getType() == ValueType.INTEGER) {
			descriptorIndex = (int) ((ValueInteger) descriptor).getValue();
		} else if (this.descriptor.getType() == ValueType.STRING) {
			descriptorIndex = constantPoolBuilder.addItemUtf8(((ValueString) descriptor).getString());
		} else if (this.descriptor.getType() == ValueType.REFERENCE) {
			descriptorIndex = constantPoolBuilder.getCPItemIndexById(((ValueReference) descriptor).getValue());
		} else {
			throw new CompilationErrorException();
		}

		return new FieldInfo(accessFlags, nameIndex, descriptorIndex, 0, new Attribute[0]);
	}
}
