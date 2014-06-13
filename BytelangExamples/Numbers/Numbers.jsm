@constantPoolBegin()
	@class(id = "ClassSystem", name = "java/lang/System")
	@nameAndType(id = "NTout", name = "out", descriptor = "Ljava/io/PrintStream;")
	@fieldref(id = "SystemOut", class = #ClassSystem, nameAndType = #NTout)
	@string(id = "StrSep", string = " ")

	@class(id = "ClassPrintStream", name = "java/io/PrintStream")
	
	@nameAndType(id = "NTprintlni", name = "println", descriptor = "(I)V")
	@methodref(id = "printlni", class = #ClassPrintStream, nameAndType = #NTprintlni)

	@nameAndType(id = "NTprinti", name = "print", descriptor = "(I)V")
	@methodref(id = "printi", class = #ClassPrintStream, nameAndType = #NTprinti)

	@nameAndType(id = "NTprintlnv", name = "println", descriptor = "()V")
	@methodref(id = "printlnv", class = #ClassPrintStream, nameAndType = #NTprintlnv)

	@nameAndType(id = "NTprints", name = "print", descriptor = "(Ljava/lang/String;)V")
	@methodref(id = "prints", class = #ClassPrintStream, nameAndType = #NTprints)
@constantPoolEnd()

public class Numbers extends java.lang.Object {
	@set(maxStack = 15)
	@set(maxLocals = 1)
	public static void main(java.lang.String[]) {
		getstatic #SystemOut

		iconst_1
		istore_0

		.rep:
			dup
			iload_0
			invokevirtual #printi
			iinc 0, 1

			iload_0
			bipush 15
			isub
			ifgt .end

			dup
			ldc #StrSep
			invokevirtual #prints
			goto .rep
		.end:
			invokevirtual #printlnv
			return
	}
}
