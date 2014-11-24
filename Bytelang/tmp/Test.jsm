//lock()

//@set(magic   =        [0xCA, 0xFE, 0xBA, 0xBE])
//@set(version =        "1.7")

//@set(accessFlags =     1)
//@set(thisClass   =     #cltest)
//@set(superClass  =     #clobject)

//@set(interfacesCount = 1)
//@set(fieldsCount     = 1)
//@set(methodsCount    = 1)
//@set(attributesCount = 1)

// Constant pool
@constantPoolBegin()
	@class(name = "java/lang/NullPointerException")
	@class(id = "cltest", name = #test)
	@class(id = "clobject", name = "java/lang/Object")
	@utf8(id = "test", bytes = "Test")
	@nameAndType(id = "objectConNT", name = "<init>", descriptor = "()V")
	@methodref(id = "methodObjectConstructor", class = #clobject, nameAndType = #objectConNT)
@constantPoolEnd()

//@method(accessFlags = 9, name = "main", descriptor = "([Ljava/lang/String;)V")
//@field (accessFlags = 9, name = "hell", descriptor = "I")

//@attribute(name = "CustomAttr", length = 3, data = [0xAA, 0xBB, 0xCC])
//@attribute(name = "CustomAttr", data = [0xAA, 0xBB, 0xCC])

//@interface(classIndex = "java/io/Serializable")

public class Test extends java.lang.Object implements java.io.Serializable {
//	@set(fieldAttributesCount = 2)
//	@attribute(name = "CustomAttr", data = [0xAA, 0xBB, 0xCC])
//	@attribute(name = "Data", data = [0xA0, 0xB1, 0xC2, 0xD3, 0xE4, 0xF5])
	public final java.lang.Object object;
	
//	@attribute(name = "CustomAttr", data = [0xAA])
//	public native void foo();
	
/*	public void <init>() {
		aload_0
		invokespecial #methodObjectConstructor
		return
	}*/

//	@set(methodAttributesCount = 1)
//	@set(maxStack = 10)
//	@set(maxLocals = 10)
//	@set(codeLength = 1)
//	@attribute(name = "CustomAttr", data = [0xAA])
	@codeAttribute(name = "Exceptions", data = [0x00, 0x01, 0x00, 0x01])
	public static void main(java.lang.String[]) {
		.zacatek:
			nop
			nop
			nop
			nop
			sipush 456
			sipush 666
			goto .zacatek // offset -4 (0xFFFC)
			goto .konec // offset 8
			bipush 2
			return
			ldc #cltest
			bipush 123
			sipush 456
		.konec:
			return
	}
}
