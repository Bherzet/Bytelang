package bytelang.parser.container.values;

public class ValueReference implements Value {
	private String value;

	public ValueReference(String value) {
		this.value = value;
	}

	@Override
	public ValueType getType() {
		return ValueType.REFERENCE;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.valueOf("#" + value);
	}
}
