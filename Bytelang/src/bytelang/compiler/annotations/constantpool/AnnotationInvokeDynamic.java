package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemInvokeDynamic;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueType;

public class AnnotationInvokeDynamic extends BasicAnnotation implements Factory<AnnotationInvokeDynamic>, CPItemAnnotation {
	private String id                  = null;
	private int    bootstrapMethodAttr = 0;
	private Value  nameAndType         = null;
	
	public AnnotationInvokeDynamic() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("bootstrapMethodAttr", new ValueType[]{ValueType.INTEGER});
		this.addMandatoryParam("nameAndType", new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id                  = grepId(elementAnnotation.getParameters());
		this.bootstrapMethodAttr = (int) ((ValueInteger) elementAnnotation.getParameters().get("bootstrapMethodAttr")).getValue();
		this.nameAndType         = elementAnnotation.getParameters().get("nameAndType");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.INVOKE_DYNAMIC;
	}

	@Override
	public AnnotationInvokeDynamic newInstance() {
		return new AnnotationInvokeDynamic();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public int getBootstrapMethodAttr() {
		return bootstrapMethodAttr;
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
		nameAndType = ConstantPoolBuilder.replaceReference(referencesTable, nameAndType);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		return new CPItemInvokeDynamic(id, bootstrapMethodAttr, (int) ((ValueInteger) nameAndType).getValue());
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
