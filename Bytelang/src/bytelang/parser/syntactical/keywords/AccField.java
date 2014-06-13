package bytelang.parser.syntactical.keywords;

import java.util.List;

public enum AccField {
	PUBLIC    (0x0001),
	PRIVATE   (0x0002),
	PROTECTED (0x0004),
	STATIC    (0x0008),
	FINAL     (0x0010),
	VOLATILE  (0x0040),
	TRANSIENT (0x0080),
	SYNTHETIC (0x1000),
	ENUM      (0x4000);
	
	private int value;

	private AccField(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static int getAccessFlags(List<KeywordType> keywords) {
		int result = 0;
		
		for (KeywordType keywordType : keywords) {
			result |= keywordType.getAccField().getValue();
		}
		
		return result;
	}
}
