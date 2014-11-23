package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemLong;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.ValueArray;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class AnnotationLong extends BasicAnnotation implements Factory<AnnotationLong>, CPItemAnnotation {
	private String     id;
	private ValueArray highBytes;
	private ValueArray lowBytes;
	
	public AnnotationLong() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("highBytes", new ValueType[]{ValueType.ARRAY});
		this.addMandatoryParam("lowBytes", new ValueType[]{ValueType.ARRAY});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id        = grepId(elementAnnotation.getParameters());
		this.highBytes = (ValueArray) elementAnnotation.getParameters().get("highBytes");
		this.lowBytes  = (ValueArray) elementAnnotation.getParameters().get("lowBytes");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.FLOAT;
	}

	@Override
	public AnnotationLong newInstance() {
		return new AnnotationLong();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public ValueArray getHighBytes() {
		return highBytes;
	}
	
	public ValueArray getLowBytes() {
		return lowBytes;
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
		short[] lowValues  = new short[this.lowBytes.getValues().size()];
		short[] highValues = new short[this.highBytes.getValues().size()];
		
		for (int i = 0; i < lowValues.length; i++) {
			if (this.lowBytes.getValues().get(i).getType() == ValueType.INTEGER) {
				lowValues[i] = (short) ((ValueInteger) this.lowBytes.getValues().get(i)).getValue();
			} else {
				throw new CompilationErrorException("Annotation @long only accepts array of integer values.");
			}
		}
		
		for (int i = 0; i < highValues.length; i++) {
			if (this.highBytes.getValues().get(i).getType() == ValueType.INTEGER) {
				highValues[i] = (short) ((ValueInteger) this.highBytes.getValues().get(i)).getValue();
			} else {
				throw new CompilationErrorException("Annotation @long only accepts array of integer values.");
			}
		}
		
		return new CPItemLong(id, lowValues, highValues);
	}

	@Override
	public boolean isIndependent() {
		return true;
	}
}
