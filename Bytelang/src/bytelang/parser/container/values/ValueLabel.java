package bytelang.parser.container.values;

public class ValueLabel implements Value {
	private String labelName;
	
	public ValueLabel(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public ValueType getType() {
		return ValueType.LABEL;
	}
	
	public String getLabelName() {
		return labelName;
	}
}
