package bytelang.parser.container.values;

public class ValueInteger implements Value {
	private final long value;
	
	public ValueInteger(long value) {
		this.value = value;
	}
	
	@Override
	public ValueType getType() {
		return ValueType.INTEGER;
	}
	
	public long getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
