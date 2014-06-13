package bytelang.parser.syntactical.keywords;

public enum KeywordType {
	// The Java Programming language
	ABSTRACT     ("abstract",     true,  false, true,  false, AccClass.ABSTRACT,   null,               AccMethod.ABSTRACT     ),
	SYNCHRONIZED ("synchronized", false, false, true,  false, null,                null,               AccMethod.SYNCHRONIZED ),
	PRIVATE      ("private",      false, true,  true,  false, null,                AccField.PRIVATE,   AccMethod.PRIVATE      ),
	IMPLEMENTS   ("implements",   false, false, false, false, null,                null,               null                   ),
	PROTECTED    ("protected",    false, true,  true,  false, null,                AccField.PROTECTED, AccMethod.PROTECTED    ),
	PUBLIC       ("public",       true,  true,  true,  false, AccClass.PUBLIC,     AccField.PUBLIC,    AccMethod.PUBLIC       ),
	THROWS       ("throws",       false, false, false, false, null,                null,               null                   ),
	ENUM         ("enum",         false, false, false, true,  AccClass.ENUM,       AccField.ENUM,      null                   ),
	TRANSIENT    ("transient",    false, true,  false, false, null,                AccField.TRANSIENT, null                   ),
	EXTENDS      ("extends",      false, false, false, false, null,                null,               null                   ),
	FINAL        ("final",        true,  true,  true,  false, AccClass.FINAL,      AccField.FINAL,     AccMethod.FINAL        ),
	INTERFACE    ("interface",    false, false, false, true,  AccClass.INTERFACE,  null,               null                   ),
	STATIC       ("static",       false, true,  true,  false, null,                AccField.STATIC,    AccMethod.STATIC       ),
	CLASS        ("class",        false, false, false, true,  AccClass.CLASS,      null,               null                   ),
	STRICTFP     ("strictfp",     true,  false, true,  false, null,                null,               null                   ),
	VOLATILE     ("volatile",     false, true,  false, false, null,                AccField.VOLATILE,  null                   ),
	NATIVE       ("native",       false, false, true,  false, null,                null,               AccMethod.NATIVE       ),
	SUPER        ("super",        true,  false, false, false, AccClass.SUPER,      null,               null                   ),
	                                                                                                                          
	// Bytelang                                                                                                               
	SYNTHETIC    ("synthetic",    true,  true,  true,  false, AccClass.SYNTHETIC,  AccField.SYNTHETIC, AccMethod.SYNTHETIC    ),
	ANNOTATION   ("annotation",   true,  false, false, false, AccClass.ANNOTATION, null,               null                   ),
	BRIDGE       ("bridge",       false, false, true,  false, null,                null,               AccMethod.BRIDGE       ),
	VARARGS      ("varargs",      false, false, true,  false, null,                null,               AccMethod.VARARGS      ),
	STRICT       ("strict",       false, false, true,  false, null,                null,               AccMethod.STRICT       );
	
	private String    keyword;
	private boolean   classModifier;
	private boolean   fieldModifier;
	private boolean   methodModifier;
	private boolean   classType;
	private AccClass  accClass;
	private AccField  accField;
	private AccMethod accMethod;
	
	private KeywordType(String keyword, boolean classModifier, boolean fieldModifier, boolean methodModifier, boolean classType, AccClass accClass, AccField accField, AccMethod accMethod) {
		this.keyword        = keyword;
		this.classModifier  = classModifier;
		this.fieldModifier  = fieldModifier;
		this.methodModifier = methodModifier;
		this.classType      = classType;
		this.accClass       = accClass;
		this.accField       = accField;
		this.accMethod      = accMethod;
	}
	
	public static KeywordType findKeyword(String name) {
		name = name.trim();
		
		for (KeywordType type : KeywordType.values()) {
			if (type.getKeyword().equals(name)) {
				return type;
			}
		}
		
		return null;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public boolean isClassModifier() {
		return classModifier;
	}
	
	public boolean isFieldModifier() {
		return fieldModifier;
	}
	
	public boolean isMethodModifier() {
		return methodModifier;
	}
	
	public boolean isClassType() {
		return classType;
	}
	
	public AccClass getAccClass() {
		return accClass;
	}
	
	public AccField getAccField() {
		return accField;
	}
	
	public AccMethod getAccMethod() {
		return accMethod;
	}
}
