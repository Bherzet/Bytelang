package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemMethodType;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationMethodType extends BasicAnnotation implements Factory<AnnotationMethodType>, CPItemAnnotation {
	private String id;
	private Value  descriptor;
	
	public AnnotationMethodType() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("descriptor", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE, ValueType.STRING});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id         = grepId(elementAnnotation.getParameters());
		this.descriptor = elementAnnotation.getParameters().get("descriptor");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.METHOD_TYPE;
	}

	@Override
	public AnnotationMethodType newInstance() {
		return new AnnotationMethodType();
	}
	
	@Override
	public String getId() {
		return id;
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
		descriptor = ConstantPoolBuilder.replaceReference(referencesTable, descriptor);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		if (descriptor.getType() == ValueType.INTEGER) {
			return new CPItemMethodType(id, (int) ((ValueInteger) descriptor).getValue());
		} else if (descriptor.getType() == ValueType.STRING) {
			return new CPItemMethodType(id, constantPoolBuilder.addItemUtf8(((ValueString) descriptor).getString()));
		} else {
			throw new CompilationErrorException();
		}
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
