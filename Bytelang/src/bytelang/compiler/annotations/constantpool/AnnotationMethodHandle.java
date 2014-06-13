package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemMethodHandle;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class AnnotationMethodHandle extends BasicAnnotation implements Factory<AnnotationMethodHandle>, CPItemAnnotation {
	private String id;
	private int    referenceKind;
	private Value  referenceIndex;
	
	public AnnotationMethodHandle() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("referenceKind", new ValueType[]{ValueType.INTEGER});
		this.addMandatoryParam("referenceIndex", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id             = grepId(elementAnnotation.getParameters());
		this.referenceKind  = (int) ((ValueInteger) elementAnnotation.getParameters().get("referenceKind")).getValue();
		this.referenceIndex = elementAnnotation.getParameters().get("referenceIndex");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.METHOD_HANDLE;
	}

	@Override
	public AnnotationMethodHandle newInstance() {
		return new AnnotationMethodHandle();
	}

	@Override
	public String getId() {
		return id;
	}
	
	public int getReferenceKind() {
		return referenceKind;
	}
	
	public Value getReferenceIndex() {
		return referenceIndex;
	}
	
	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public void replaceReferences(Hashtable<String, Integer> referencesTable) {
		referenceIndex = ConstantPoolBuilder.replaceReference(referencesTable, referenceIndex);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		return new CPItemMethodHandle(id, (short) referenceKind, (int) ((ValueInteger) referenceIndex).getValue());
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
