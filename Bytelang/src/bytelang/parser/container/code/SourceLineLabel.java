package bytelang.parser.container.code;

public class SourceLineLabel implements SourceLine {
	private String labelName;

	public SourceLineLabel(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public SourceLineType getType() {
		return SourceLineType.LABEL;
	}
	
	public String getLabelName() {
		return labelName;
	}
}
