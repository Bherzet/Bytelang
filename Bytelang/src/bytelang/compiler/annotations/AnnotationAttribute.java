package bytelang.compiler.annotations;

import java.util.ArrayList;
import java.util.Hashtable;

import bytelang.classes.Attribute;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueArray;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public class AnnotationAttribute extends BasicAnnotation implements Factory<AnnotationAttribute> {
	private Value        name   = null;
	private ValueInteger length = null;
	private ValueArray   data   = null;
	
	public AnnotationAttribute() {
		this.addMandatoryParam("name", new ValueType[]{ValueType.INTEGER, ValueType.STRING});
		this.addNonMandatoryParam("length", new ValueType[]{ValueType.INTEGER});
		this.addNonMandatoryParam("data", new ValueType[]{ValueType.ARRAY});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);

		Hashtable<String, Value> parameters  = elementAnnotation.getParameters();
		Value                    valueName   = parameters.get("name");
		Value                    valueLength = parameters.get("length");
		Value                    valueData   = parameters.get("data");

		this.name = valueName;
		if (valueLength != null) this.length = (ValueInteger) valueLength;
		if (valueData   != null) this.data   = (ValueArray) valueData;
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.ATTRIBUTE;
	}

	@Override
	public AnnotationAttribute newInstance() {
		return new AnnotationAttribute();
	}
	
	public Value getName() {
		return name;
	}
	
	public Value getLength() {
		return length;
	}
	
	public Value getData() {
		return data;
	}
	
	public Attribute toAttribute(ConstantPoolBuilder constantPoolBuilder) {
		int     nameIndex = 0;
		int     length    = 0;
		short[] data      = new short[]{};
		
		if (this.name.getType() == ValueType.INTEGER) {
			nameIndex = (int) ((ValueInteger) this.name).getValue();
		} else if (this.name.getType() == ValueType.STRING) {
			nameIndex = constantPoolBuilder.addItemUtf8(((ValueString) this.name).getString());
		}
		
		if (this.data != null) {
			ArrayList<Value> values = this.data.getValues();
			data                    = new short[values.size()];
			
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i).getType() != ValueType.INTEGER) {
					throw new RuntimeException(
						"Parameter \"data\" for annotation @attribute expects an array of integers."
					);
				}
				
				data[i] = (short) ((ValueInteger) values.get(i)).getValue();
			}
		}
		
		if (this.length == null) {
			length = data.length;
		} else {
			length = (int) this.length.getValue();
		}
		
		return new Attribute(nameIndex, length, data);
	}
}
