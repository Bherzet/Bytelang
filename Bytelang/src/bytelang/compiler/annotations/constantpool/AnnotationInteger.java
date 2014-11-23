package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemInteger;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.ValueArray;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class AnnotationInteger extends BasicAnnotation implements Factory<AnnotationInteger>, CPItemAnnotation {
	private String     id;
	private ValueArray bytes;
	
	public AnnotationInteger() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("bytes", new ValueType[]{ValueType.ARRAY});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id    = grepId(elementAnnotation.getParameters());
		this.bytes = (ValueArray) elementAnnotation.getParameters().get("bytes");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.FLOAT;
	}

	@Override
	public AnnotationInteger newInstance() {
		return new AnnotationInteger();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public ValueArray getBytes() {
		return bytes;
	}
	
	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public void replaceReferences(Hashtable<String, Integer> referencesTable) {
		//
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		short[] values = new short[this.bytes.getValues().size()];
		
		for (int i = 0; i < values.length; i++) {
			if (this.bytes.getValues().get(i).getType() == ValueType.INTEGER) {
				values[i] = (short) ((ValueInteger) this.bytes.getValues().get(i)).getValue();
			} else {
				throw new CompilationErrorException("Annotation @integer only accepts array of integer values.");
			}
		}
		
		return new CPItemInteger(id, values);
	}

	@Override
	public boolean isIndependent() {
		return true;
	}
}
