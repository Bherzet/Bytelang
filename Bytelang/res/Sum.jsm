@constantPoolBegin()
	@class(id = "ClassObject",      name = "java/lang/Object")
	@class(id = "ClassSystem",      name = "java/lang/System")
	@class(id = "ClassScanner",     name = "java/util/Scanner")
	@class(id = "ClassPrintStream", name = "java/io/PrintStream")
	@class(id = "ClassInteger",     name = "java/lang/Integer")

	/******************************* CLASS System ***************************************/
	// System.out
	@nameAndType(id = "NTSystemOut", name = "out", descriptor = "Ljava/io/PrintStream;")
	@fieldref(id = "SystemOut", class = #ClassSystem, nameAndType = #NTSystemOut)
	
	// System.in
	@nameAndType(id = "NTSystemIn", name = "in", descriptor = "Ljava/io/InputStream;")
	@fieldref(id = "SystemIn", class = #ClassSystem, nameAndType = #NTSystemIn)

	// void System.out.printf(String[], Object...);
	@nameAndType(id = "NTSystemPrintf", name = "printf", descriptor = "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;")
	@methodref(id = "SystemPrintf", class = #ClassPrintStream, nameAndType = #NTSystemPrintf)

	// void System.out.println();
	@nameAndType(id = "NTSystemPrintlnV", name = "println", descriptor = "()V")
	@methodref(id = "SystemPrintlnV", class = #ClassPrintStream, nameAndType = #NTSystemPrintlnV)

	/******************************* CLASS Scanner **************************************/
	// void Scanner.<init>(InputStream);
	@nameAndType(id = "NTScannerInit", name = "<init>", descriptor = "(Ljava/io/InputStream;)V")
	@methodref(id = "ScannerInit", class = #ClassScanner, nameAndType = #NTScannerInit)

	// int Scanner.nextInt();
	@nameAndType(id = "NTScannerNextInt", name = "nextInt", descriptor = "()I")
	@methodref(id = "ScannerNextInt", class = #ClassScanner, nameAndType = #NTScannerNextInt)

	/******************************* CLASS Integer **************************************/
	@nameAndType(id = "NTIntegerValueOf", name = "valueOf", descriptor = "(I)Ljava/lang/Integer;")
	@methodref(id = "IntegerValueOf", class = #ClassInteger, nameAndType = #NTIntegerValueOf)

	/******************************** DATA **********************************************/
	@string(id = "str1", string = "Zadejte prvni cislo: ")
	@string(id = "str2", string = "Zadejte druhe cislo: ")
	@string(id = "strR", string = "Soucet zadanych cisel je: %d")
@constantPoolEnd()

public class Sum {
	/*
	 * .._____________________________________________
	 * ..|            LOCAL VARIABLES USAGE          |
	 * ..|-------------------------------------------|
	 * ..| index |   type   |         usage          |
	 * ..|-------|----------|------------------------|
	 * ..|   0   | @scanner | new Scanner(System.in) |
	 * ..|   1   | int      | first loaded value     |
	 * ..|   2   | int      | second loaded value    |
	 */
	@set(maxStack = 9)
	@set(maxLocals = 3)
	public static void main(java.lang.String[]) {
		.createScanner:
			new #ClassScanner                    // @scanner
			dup                                  // @scanner, @scanner
			astore_0                             // @scanner
			
			dup                                  // @scanner, @scanner
			getstatic #SystemIn                  // @scanner, @scanner, @in
			invokespecial #ScannerInit           // @scanner
	
		.getSystemOut:                           
			getstatic #SystemOut                 // @scanner, @out 
			
		.loadFirst:
			dup                                  // @scanner, @out, @out
			ldc #str1                            // @scanner, @out, @out, @str1
			aconst_null                          // @scanner, @out, @out, @str1, @null
			invokevirtual #SystemPrintf          // @scanner, @out,
			aload_0                              // @scanner, @out, @scanner
			invokevirtual #ScannerNextInt        // @scanner, @out, int
			istore_1                             // @scanner, @out
			
		.loadSecond:
			dup                                  // @scanner, @out, @out
			ldc #str2                            // @scanner, @out, @out, @str2
			aconst_null                          // @scanner, @out, @out, @str2, @null
			invokevirtual #SystemPrintf          // @scanner, @out
			aload_0                              // @scanner, @out, @scanner
			
			invokevirtual #ScannerNextInt        // @scanner, @out, int
			istore_2                             // @scanner, @out
			
		.printResult:
			dup                                  // @scanner, @out, @out
			ldc #strR                            // @scanner, @out, @out, @strR
			
			iconst_1                             // @scanner, @out, @out, @strR, int:1
			anewarray #ClassObject               // @scanner, @out, @out, @strR, [array:Object:1] 
			dup                                  // @scanner, @out, @out, @strR, [array:Object:1], [array:Object:1]
			
			iconst_0                             // @scanner, @out, @out, @strR, [array:Object:1], [array:Object:1], int:0
			iload_1                              // @scanner, @out, @out, @strR, [array:Object:1], [array:Object:1], int:0, int:var1
			iload_2                              // @scanner, @out, @out, @strR, [array:Object:1], [array:Object:1], int:0, int:var1, int:var2
			iadd                                 // @scanner, @out, @out, @strR, [array:Object:1], [array:Object:1], int:0, int
			invokestatic #IntegerValueOf         // @scanner, @out, @out, @strR, [array:Object:1], [array:Object:1], int:0, @integer
			aastore                              // @scanner, @out, @out, @strR, [array:Object:1]
			
			invokevirtual #SystemPrintf          // @scanner, @out,
			invokevirtual #SystemPrintlnV        // @scanner,
			
		.finish:
			return                               // @scanner
	}
}
