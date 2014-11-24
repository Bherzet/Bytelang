@constantPoolBegin()
	@string(id = "StrHello", string = "Hello, I am Bytelang!")
	@ffref(id="SystemOut", class="java/lang/System", name="out", type="Ljava/io/PrintStream;")
	@fmref(id="println", class="java/io/PrintStream", name="println", type="(Ljava/lang/String;)V")
@constantPoolEnd()

public class HelloWorld2 extends java.lang.Object {
	@set(maxStack = 2)
	@set(maxLocals = 1)
	public static void main(java.lang.String[]) {
		getstatic     #SystemOut
		ldc           #StrHello
		invokevirtual #println
		return
	}
}
