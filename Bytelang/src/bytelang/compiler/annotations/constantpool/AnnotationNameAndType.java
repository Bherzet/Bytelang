package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemNameAndType;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationNameAndType extends BasicAnnotation implements Factory<AnnotationNameAndType>, CPItemAnnotation {
	private String id;
	private Value  name;
	private Value  descriptor;
	
	public AnnotationNameAndType() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("name", new ValueType[]{ValueType.INTEGER, ValueType.STRING, ValueType.REFERENCE});
		this.addMandatoryParam("descriptor", new ValueType[]{ValueType.INTEGER, ValueType.STRING, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id         = grepId(elementAnnotation.getParameters());
		this.name       = elementAnnotation.getParameters().get("name");
		this.descriptor = elementAnnotation.getParameters().get("descriptor");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.NAME_AND_TYPE;
	}

	@Override
	public AnnotationNameAndType newInstance() {
		return new AnnotationNameAndType();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public Value getName() {
		return name;
	}
	
	public Value getDescriptor() {
		return descriptor;
	}
	
	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public void replaceReferences(Hashtable<String, Integer> referencesTable) {
		name       = ConstantPoolBuilder.replaceReference(referencesTable, name);
		descriptor = ConstantPoolBuilder.replaceReference(referencesTable, descriptor);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		int name;
		int descriptor;
		
		if (this.name.getType() == ValueType.INTEGER) {
			name = (int) ((ValueInteger) this.name).getValue();
		} else if (this.name.getType() == ValueType.STRING) {
			name = constantPoolBuilder.addItemUtf8(((ValueString) this.name).getString());
		} else {
			throw new CompilationErrorException("Internal error.");
		}
		
		if (this.descriptor.getType() == ValueType.INTEGER) {
			descriptor = (int) ((ValueInteger) this.descriptor).getValue();
		} else if (this.descriptor.getType() == ValueType.STRING) {
			descriptor = constantPoolBuilder.addItemUtf8(((ValueString) this.descriptor).getString());
		} else {
			throw new CompilationErrorException("Internal error.");
		}
		
		return new CPItemNameAndType(id, name, descriptor);
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
