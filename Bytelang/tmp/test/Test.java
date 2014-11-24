public class Test {
	public static void main(String[] args) {
		Object o = null;

		try {
			o.getClass();
		} catch (NullPointerException e) {
			return;
		}
	}
}

