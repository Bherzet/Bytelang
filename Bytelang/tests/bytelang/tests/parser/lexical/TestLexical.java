package bytelang.tests.parser.lexical;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import bytelang.parser.lexical.states.LexicalStateType;

public class TestLexical {
	@Test()
	public void test1() {
		assertTrue(Utils.checkString("@set(alpha = [0xFF, 1, 0xA], beta=\"karel\")", new LexicalStateType[]{
			LexicalStateType.S_AT, LexicalStateType.WORD, LexicalStateType.S_LPARENTHESIS, LexicalStateType.WORD, LexicalStateType.S_EQUAL,
			LexicalStateType.S_LBRACKET, LexicalStateType.INTEGER, LexicalStateType.S_COMMA, LexicalStateType.INTEGER, LexicalStateType.S_COMMA,
			LexicalStateType.INTEGER, LexicalStateType.S_RBRACKET, LexicalStateType.S_COMMA, LexicalStateType.WORD, LexicalStateType.S_EQUAL,
			LexicalStateType.STRING, LexicalStateType.S_RPARENTHESIS
		}));
	}
	
	@Test()
	public void test2() {
		assertTrue(Utils.checkString("public class Foo implements Boo {}", new LexicalStateType[]{
			LexicalStateType.WORD, LexicalStateType.WORD, LexicalStateType.WORD, LexicalStateType.WORD, LexicalStateType.WORD,
			LexicalStateType.S_LBRACE, LexicalStateType.S_RBRACE
		}));
	}
	
	@Test()
	public void test3() {
		assertTrue(Utils.checkString("@set(alpha=\"abc\"/*abc*/);", new LexicalStateType[]{
			LexicalStateType.S_AT, LexicalStateType.WORD, LexicalStateType.S_LPARENTHESIS, LexicalStateType.WORD, LexicalStateType.S_EQUAL,
			LexicalStateType.STRING, LexicalStateType.BLOCK_COMMENT, LexicalStateType.S_RPARENTHESIS, LexicalStateType.S_SEMICOLN
		}));
	}
	
	@Test()
	public void test4() throws FileNotFoundException {
		assertTrue(Utils.checkFile("tests/bytelang/tests/parser/lexical/Test01.bytel", new LexicalStateType[] {
				LexicalStateType.S_AT, LexicalStateType.WORD, LexicalStateType.S_LPARENTHESIS, LexicalStateType.WORD, LexicalStateType.S_EQUAL,
				LexicalStateType.STRING, LexicalStateType.S_COMMA, LexicalStateType.WORD, LexicalStateType.S_EQUAL, LexicalStateType.S_LBRACKET,
				LexicalStateType.INTEGER, LexicalStateType.S_RBRACKET, LexicalStateType.S_RPARENTHESIS, LexicalStateType.WORD, LexicalStateType.WORD,
				LexicalStateType.WORD, LexicalStateType.WORD, LexicalStateType.WORD, LexicalStateType.S_LBRACE, LexicalStateType.WORD, LexicalStateType.WORD,
				LexicalStateType.WORD, LexicalStateType.S_LPARENTHESIS, LexicalStateType.WORD, LexicalStateType.S_LBRACKET, LexicalStateType.S_RBRACKET,
				LexicalStateType.S_RPARENTHESIS, LexicalStateType.S_LBRACE, LexicalStateType.LINE_COMMENT, LexicalStateType.S_RBRACE,
				LexicalStateType.BLOCK_COMMENT, LexicalStateType.S_AT, LexicalStateType.WORD, LexicalStateType.S_LPARENTHESIS, LexicalStateType.WORD,
				LexicalStateType.S_EQUAL, LexicalStateType.STRING, LexicalStateType.S_COMMA, LexicalStateType.WORD, LexicalStateType.S_EQUAL,
				LexicalStateType.INTEGER, LexicalStateType.S_COMMA, LexicalStateType.WORD, LexicalStateType.S_EQUAL, LexicalStateType.S_LBRACKET,
				LexicalStateType.INTEGER, LexicalStateType.S_RBRACKET, LexicalStateType.S_RPARENTHESIS, LexicalStateType.WORD, LexicalStateType.WORD,
				LexicalStateType.WORD, LexicalStateType.S_LPARENTHESIS, LexicalStateType.S_RPARENTHESIS, LexicalStateType.S_LBRACE, LexicalStateType.WORD,
				LexicalStateType.S_RBRACE, LexicalStateType.S_RBRACE
		}));
	}
}
