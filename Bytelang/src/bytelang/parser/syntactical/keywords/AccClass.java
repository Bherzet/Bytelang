package bytelang.parser.syntactical.keywords;

import java.util.List;

public enum AccClass {
	CLASS      (0x0000),
	PUBLIC     (0x0001),
	FINAL      (0x0010),
	SUPER      (0x0020),
	INTERFACE  (0x0200),
	ABSTRACT   (0x0400),
	SYNTHETIC  (0x1000),
	ANNOTATION (0x2000),
	ENUM       (0x4000);
	
	private int value;

	private AccClass(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static int getAccessFlags(List<KeywordType> keywords) {
		int result = 0;
		
		for (KeywordType keywordType : keywords) {
			result |= keywordType.getAccClass().getValue();
		}
		
		return result;
	}
}
