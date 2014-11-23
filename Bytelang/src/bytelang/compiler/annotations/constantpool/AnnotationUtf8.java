package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemUtf8;
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

public class AnnotationUtf8 extends BasicAnnotation implements Factory<AnnotationUtf8>, CPItemAnnotation {
	private String id     = null;
	private Value  length = null;
	private Value  bytes  = null;
	
	public AnnotationUtf8() {
		this.addNonMandatoryParam("id", new ValueType[]{ValueType.STRING});
		this.addNonMandatoryParam("length", new ValueType[]{ValueType.INTEGER});
		this.addMandatoryParam("bytes", new ValueType[]{ValueType.STRING, ValueType.ARRAY});
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		this.validate(elementAnnotation);
		
		this.id     = grepId(elementAnnotation.getParameters());
		this.length = elementAnnotation.getParameters().get("length");
		this.bytes  = elementAnnotation.getParameters().get("bytes");
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.UTF8;
	}

	@Override
	public AnnotationUtf8 newInstance() {
		return new AnnotationUtf8();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public Value getLength() {
		return length;
	}
	
	public Value getBytes() {
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
		short[] bytes = null;
		
		if (this.bytes.getType() == ValueType.STRING) {
			String str = ((ValueString) this.bytes).getString();
			bytes      = new short[str.length()];
			
			for (int i = 0; i < str.length(); i++) {
				bytes[i] = (short) str.charAt(i);
			}
		} else if (this.bytes.getType() == ValueType.ARRAY) {
			bytes = new short[((ValueArray) this.bytes).getValues().size()];
			
			for (int i = 0; i < bytes.length; i++) {
				if (((ValueArray) this.bytes).getValues().get(i).getType() == ValueType.INTEGER) {
					bytes[i] = (short) ((ValueInteger) ((ValueArray) this.bytes).getValues().get(i)).getValue();
				} else {
					throw new CompilationErrorException("Annotation @utf8 only accepts array of integers.");
				}
			}
		} else {
			throw new CompilationErrorException();
		}
		
		if (this.length != null) {
			return new CPItemUtf8(id, (int) ((ValueInteger) length).getValue(), bytes); 
		} else {
			return new CPItemUtf8(id, bytes.length, bytes);
		}
	}

	@Override
	public boolean isIndependent() {
		return true;
	} 
}
