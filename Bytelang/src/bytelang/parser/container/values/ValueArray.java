package bytelang.parser.container.values;

import java.util.ArrayList;

public class ValueArray implements Value {
	private final ArrayList<Value> values;
	
	public ValueArray(ArrayList<Value> values) {
		this.values = values;
	}

	@Override
	public ValueType getType() {
		return ValueType.ARRAY;
	}
	
	public ArrayList<Value> getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		return values.toString();
	}
}
