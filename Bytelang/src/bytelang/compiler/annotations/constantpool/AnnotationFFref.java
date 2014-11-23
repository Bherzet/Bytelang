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
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationFFref extends BasicAnnotation implements Factory<AnnotationFFref>, CPItemAnnotation {
	private String id;
	private Value classIndex;
	private Value nameIndex;
	private Value descriptorIndex;
	
	public AnnotationFFref() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("class", new ValueType[]{ValueType.STRING, ValueType.REFERENCE, ValueType.INTEGER});
		this.addMandatoryParam("name", new ValueType[]{ValueType.STRING, ValueType.REFERENCE, ValueType.INTEGER});
		this.addMandatoryParam("type", new ValueType[]{ValueType.STRING, ValueType.REFERENCE, ValueType.INTEGER});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		Hashtable<String,Value> parameters = elementAnnotation.getParameters();
		this.id              = grepId(parameters);
		this.classIndex      = parameters.get("class");
		this.nameIndex       = parameters.get("name");
		this.descriptorIndex = parameters.get("type");
	}
	
	@Override
	public AnnotationType getType() {
		return AnnotationType.FFREF;
	}
	
	@Override
	public AnnotationFFref newInstance() {
		return new AnnotationFFref();
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public void replaceReferences(Hashtable<String, Integer> referencesTable) {
		classIndex      = ConstantPoolBuilder.replaceReference(referencesTable, classIndex);
		nameIndex       = ConstantPoolBuilder.replaceReference(referencesTable, nameIndex);
		descriptorIndex = ConstantPoolBuilder.replaceReference(referencesTable, descriptorIndex);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		int cpClassIndex = 0;
		int cpNTIndex = 0;
		int cpNameIndex = 0;
		int cpDescIndex = 0;
		
		switch (this.classIndex.getType()) {
			case INTEGER:
				cpClassIndex = (int) ((ValueInteger) this.classIndex).getValue();
				break;
				
			case STRING:
				cpClassIndex = constantPoolBuilder.addItemClass(((ValueString) this.classIndex).getString());
				break;
				
			default:
		}
		
		switch (this.nameIndex.getType()) {
			case INTEGER:
				cpNameIndex = (int) ((ValueInteger) this.nameIndex).getValue();
				break;
				
			case STRING:
				cpNameIndex = constantPoolBuilder.addItemUtf8(((ValueString) this.nameIndex).getString());
				break;
				
			default:
		}
		
		switch (this.descriptorIndex.getType()) {
			case INTEGER:
				cpDescIndex = (int) ((ValueInteger) this.descriptorIndex).getValue();
				break;
				
			case STRING:
				cpDescIndex = constantPoolBuilder.addItemUtf8(((ValueString) this.descriptorIndex).getString());
				break;
				
			default:
		};
		
		cpNTIndex = constantPoolBuilder.addItemNameAndType(cpNameIndex, cpDescIndex);
		return new CPItemFieldref(id, cpClassIndex, cpNTIndex);
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
