package bytelang.exec;

import java.io.FileInputStream;
import java.io.IOException;

public class Execution {
	protected static final Injector INJECTOR = new Injector();

	protected static class Injector extends ClassLoader {
		public Class<?> defineClass(byte[] code) {
			return this.defineClass("", code, 0, code.length);
		}
	}
	
	protected static byte[] loadClassContent(String filename) throws IOException {
		FileInputStream inputStream = new FileInputStream(filename);
		byte[]          data        = new byte[inputStream.available()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) inputStream.read();
		}
		
		inputStream.close();
		return data;
	}
	
	protected static Class<?> loadClass(String filename) throws IOException {
		return INJECTOR.defineClass(loadClassContent(filename));
	}
	
	public static Object execute(String filename, String methodName) {
		try {
			return loadClass(filename).getDeclaredMethod(methodName).invoke(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
