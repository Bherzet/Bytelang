@constantPoolBegin()
	//@class(id = "ClassSystem", name = "java/lang/System")
	@nameAndType(id = "NTout", name = "out", descriptor = "Ljava/io/PrintStream;")
	@fieldref(id = "SystemOut", class = "java/lang/System", nameAndType = #NTout)
	//@fieldref(id = "SystemOut", class = #ClassSystem, nameAndType = #NTout)
	@string(id = "StrHello", string = "Hello, I am Bytelang!")

	@class(id = "ClassPrintStream", name = "java/io/PrintStream")
	@nameAndType(id = "NTprintln", name = "println", descriptor = "(Ljava/lang/String;)V")
	@methodref(id = "println", class = #ClassPrintStream, nameAndType = #NTprintln)
@constantPoolEnd()

public class HelloWorld extends java.lang.Object {
	@set(maxStack = 2)
	@set(maxLocals = 1)
	public static void main(java.lang.String[]) {
		getstatic     #SystemOut
		ldc           #StrHello
		invokevirtual #println
		return
	}
}
