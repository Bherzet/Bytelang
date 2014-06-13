package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemString;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationString extends BasicAnnotation implements Factory<AnnotationString>, CPItemAnnotation {
	private String id;
	private Value  string;
	
	public AnnotationString() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addMandatoryParam("string", new ValueType[]{ValueType.STRING, ValueType.INTEGER, ValueType.REFERENCE});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id     = grepId(elementAnnotation.getParameters());
		this.string = elementAnnotation.getParameters().get("string");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.STRING;
	}

	@Override
	public AnnotationString newInstance() {
		return new AnnotationString();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public Value getString() {
		return string;
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
		switch (string.getType()) {
			case INTEGER:
				return new CPItemString(id, (int) ((ValueInteger) this.string).getValue());

			case STRING:
				return new CPItemString(id, constantPoolBuilder.addItemUtf8(((ValueString) this.string).getString()));
				
			default:
				throw new RuntimeException();
		}
	}

	@Override
	public boolean isIndependent() {
		return false;
	}
}
