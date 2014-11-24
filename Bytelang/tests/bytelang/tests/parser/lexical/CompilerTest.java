package bytelang.tests.parser.lexical;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.util.Arrays;

import org.junit.Test;

import bytelang.Bytelang;

public class CompilerTest {
	private static boolean compareFiles(String first, String second) {
		try {
			FileInputStream firstStream  = new FileInputStream(first);
			FileInputStream secondStream = new FileInputStream(second);
			
			byte[] firsts  = new byte[firstStream.available()];
			byte[] seconds = new byte[secondStream.available()];
			
			firstStream.read(firsts);
			secondStream.read(seconds);
			
			firstStream.close();
			secondStream.close();
			
			return Arrays.equals(firsts, seconds);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	private static boolean compileAndCheck(String name) {
		Bytelang.main(new String[]{"res/" + name + ".jsm", "res/tmp/" + name + ".class"});
		return compareFiles("res/" + name + ".class", "res/tmp/" + name + ".class");
	}
	
	@Test public void testHelloWorld()  { assertTrue(compileAndCheck("HelloWorld"));  }
	@Test public void testHelloWorld2() { assertTrue(compileAndCheck("HelloWorld2")); }
	@Test public void testNumbers()     { assertTrue(compileAndCheck("Numbers"));     }
	@Test public void testSum()         { assertTrue(compileAndCheck("Sum"));         }
}
