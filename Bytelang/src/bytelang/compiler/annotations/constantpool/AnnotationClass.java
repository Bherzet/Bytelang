package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemClass;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationClass extends BasicAnnotation implements Factory<AnnotationClass>, CPItemAnnotation {
	private String id   = null;
	private Value  name = null;
	
	public AnnotationClass() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("name", new ValueType[]{ValueType.INTEGER, ValueType.STRING, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id    = grepId(elementAnnotation.getParameters());
		this.name = elementAnnotation.getParameters().get("name");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.CLASS;
	}
	
	@Override
	public AnnotationClass newInstance() {
		return new AnnotationClass();
	}
	
	public String getId() {
		return id;
	}
	
	public Value getName() {
		return name;
	}

	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public void replaceReferences(Hashtable<String, Integer> referencesTable) {
		name = ConstantPoolBuilder.replaceReference(referencesTable, name);
	}

	@Override
	public CPItem toCPItem(ConstantPoolBuilder constantPoolBuilder) {
		switch (name.getType()) {
			case INTEGER:
				return new CPItemClass(id, (int) ((ValueInteger) name).getValue());
				
			case STRING:
				return new CPItemClass(id, constantPoolBuilder.addItemUtf8(((ValueString) name).getString()));
				
			default:
				throw new RuntimeException();
		}
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
