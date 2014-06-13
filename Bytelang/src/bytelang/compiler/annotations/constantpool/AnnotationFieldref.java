package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemFieldref;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class AnnotationFieldref extends BasicAnnotation implements Factory<AnnotationFieldref>, CPItemAnnotation {
	private String id          = null;
	private Value  classIndex  = null;
	private Value  nameAndType = null;
	
	public AnnotationFieldref() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("class", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE});
		this.addMandatoryParam("nameAndType", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id          = grepId(elementAnnotation.getParameters());
		this.classIndex  = elementAnnotation.getParameters().get("class");
		this.nameAndType = elementAnnotation.getParameters().get("nameAndType");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.FIELDREF;
	}

	@Override
	public AnnotationFieldref newInstance() {
		return new AnnotationFieldref();
	}
	
	public String getId() {
		return id;
	}
	
	public Value getClassIndex() {
		return classIndex;
	}
	
	public Value getNameAndType() {
		return nameAndType;
	}
	
	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public void replaceReferences(Hashtable<String, Integer> referencesTable) {
		classIndex  = ConstantPoolBuilder.replaceReference(referencesTable, classIndex);
		nameAndType = ConstantPoolBuilder.replaceReference(referencesTable, nameAndType);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		int classIndex       = 0;
		int nameAndTypeIndex = 0;
		
		switch (this.classIndex.getType()) {
			case INTEGER:
				classIndex = (int) ((ValueInteger) this.classIndex).getValue();
				break;

			default:
				throw new RuntimeException();
		}
		
		switch (this.nameAndType.getType()) {
			case INTEGER:
				nameAndTypeIndex = (int) ((ValueInteger) this.nameAndType).getValue();
				break;
				
			default:
				throw new RuntimeException();
		}
		
		return new CPItemFieldref(id, classIndex, nameAndTypeIndex);
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
