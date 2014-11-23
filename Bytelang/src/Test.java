import java.io.FileInputStream;
import java.io.FileOutputStream;

import bytelang.classes.ClassFile;
import bytelang.compiler.Compiler;
import bytelang.parser.lexical.LexicalParser;
import bytelang.parser.syntactical.SyntacticalParser;

public class Test {
	public static void main(String[] args) throws Exception {
		LexicalParser     lexicalParser     = new LexicalParser();
		SyntacticalParser syntacticalParser = new SyntacticalParser();

		lexicalParser.parse(new FileInputStream("res/Test.jsm"));
//		lexicalParser.parse(new FileInputStream("res/HelloWorld.jsm"));
		
//		lexicalParser.parse("@set(magic = [0xCA, 0xFE, 0xBA, 0xBE, \"AHOJ\"], alpha=#123, beta=\"hovno\", karel=[\"NAPROSTY KRETEN\", \"ZKURVYSYN\", 666])");

		syntacticalParser.parse(lexicalParser.getStates());
		
		Compiler  compiler  = new Compiler(syntacticalParser.getElementClass());
		ClassFile classFile = compiler.compile();
		
		classFile.saveTo(new FileOutputStream("res/Test.class"));
//		classFile.saveTo(new FileOutputStream("res/HelloWorld.class"));
		
//		parser.parse(new FileInputStream("tests/bytelang/tests/parser/lexical/Test01.bytel"));

/*		ClassFile classFile = new ClassFile();

		classFile.constantPoolCount = 19;
		classFile.constantPoolItems = new CPItem[]{
			new CPItemMethodref(3, 12),
			new CPItemClass(13),
			new CPItemClass(14),
			new CPItemUtf8( 6, new short[]{'<', 'i', 'n', 'i', 't', '>'}),
			new CPItemUtf8( 3, new short[]{'(', ')' ,'V'}),
			new CPItemUtf8( 4, new short[]{'C', 'o', 'd', 'e'}),
			new CPItemUtf8(15, new short[]{'L', 'i', 'n', 'e', 'N', 'u', 'm', 'b', 'e', 'r', 'T', 'a', 'b', 'l', 'e'}),
			new CPItemUtf8( 4, new short[]{'m', 'a', 'i', 'n'}),
			new CPItemUtf8(22, new short[]{'(', '[', 'L', 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/', 'S', 't', 'r', 'i', 'n', 'g', ';', ')', 'V'}),
			new CPItemUtf8(10, new short[]{'S', 'o', 'u', 'r', 'c', 'e', 'F', 'i', 'l', 'e'}),
			new CPItemUtf8( 9, new short[]{'T', 'e', 's', 't', '.', 'j', 'a', 'v', 'a'}),
			new CPItemNameAndType(4, 5),
			new CPItemUtf8( 4, new short[]{'T', 'e', 's', 't'}),
			new CPItemUtf8(16, new short[]{'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/', 'O', 'b', 'j', 'e', 'c', 't'}),
			new CPItemUtf8( 5, new short[]{'(', 'I', 'I', ')', 'I'}),
			new CPItemUtf8( 3, new short[]{'s', 'u', 'm'}),
			new CPItemMethodref(2, 18),
			new CPItemNameAndType(16, 15)
		};

		classFile.accessFlags     = 1;
		classFile.thisClass       = 2;
		classFile.superClass      = 3;
		classFile.interfacesCount = 0;
		classFile.fieldsCount     = 0;
		classFile.methodsCount    = 2;
		classFile.methods         = new MethodInfo[]{
			new MethodInfo(9, 8, 9, 1, new Attribute[]{
				new AttributeCode(
					2, // max stack
					1, // max locals
					6, // code length
					new short[]{
						0x04, 0x05, 0xB8, 0x00, 0x11, 0xB1
					},
					0, new ExceptionTableItem[0], 0, new Attribute[0]
				).toAttribute(6, -1)
			}),
			new MethodInfo(9, 16, 15, 1, new Attribute[]{
				new AttributeCode(
					3,
					4,
					4,
					new short[]{
						0x1A, 0x1B, 0x60, 0xAC
					},
					0, new ExceptionTableItem[0], 0, new Attribute[0]
				).toAttribute(6, -1)
			})
		};
		
		classFile.saveTo(new FileOutputStream("res/Test.class"));*/
	}
}
