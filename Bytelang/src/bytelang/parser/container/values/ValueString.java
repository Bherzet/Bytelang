package bytelang.parser.container.values;

public class ValueString implements Value {
	private final String string;
	
	public ValueString(String string) {
		this.string = string;
	}

	@Override
	public ValueType getType() {
		return ValueType.STRING;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return '"' + string + '"';
	}
}
