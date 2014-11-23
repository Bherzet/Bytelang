package bytelang.compiler.annotations.general;

import bytelang.compiler.annotations.AnnotationAttribute;
import bytelang.compiler.annotations.AnnotationCodeAttribute;
import bytelang.compiler.annotations.AnnotationField;
import bytelang.compiler.annotations.AnnotationInterface;
import bytelang.compiler.annotations.AnnotationMethod;
import bytelang.compiler.annotations.AnnotationSet;
import bytelang.compiler.annotations.constantpool.AnnotationClass;
import bytelang.compiler.annotations.constantpool.AnnotationConstantPoolBegin;
import bytelang.compiler.annotations.constantpool.AnnotationConstantPoolEnd;
import bytelang.compiler.annotations.constantpool.AnnotationDouble;
import bytelang.compiler.annotations.constantpool.AnnotationFieldref;
import bytelang.compiler.annotations.constantpool.AnnotationFloat;
import bytelang.compiler.annotations.constantpool.AnnotationInteger;
import bytelang.compiler.annotations.constantpool.AnnotationInterfaceMethodref;
import bytelang.compiler.annotations.constantpool.AnnotationInvokeDynamic;
import bytelang.compiler.annotations.constantpool.AnnotationLock;
import bytelang.compiler.annotations.constantpool.AnnotationLong;
import bytelang.compiler.annotations.constantpool.AnnotationMethodHandle;
import bytelang.compiler.annotations.constantpool.AnnotationMethodType;
import bytelang.compiler.annotations.constantpool.AnnotationMethodref;
import bytelang.compiler.annotations.constantpool.AnnotationNameAndType;
import bytelang.compiler.annotations.constantpool.AnnotationString;
import bytelang.compiler.annotations.constantpool.AnnotationUtf8;
import bytelang.compiler.annotations.constantpool.shortcuts.AnnotationFFref;
import bytelang.compiler.annotations.constantpool.shortcuts.AnnotationFMRef;
import bytelang.helpers.Factory;

public enum AnnotationType {
	LOCK                ("lock",               new AnnotationLock()),
	SET                 ("set",                new AnnotationSet()),
	
	CONSTANT_POOL_BEGIN ("constantPoolBegin",  new AnnotationConstantPoolBegin()),
	CONSTANT_POOL_END   ("constantPoolEnd",    new AnnotationConstantPoolEnd()),
	
	CLASS               ("class",              new AnnotationClass()),
	FIELDREF            ("fieldref",           new AnnotationFieldref()),
	METHODREF           ("methodref",          new AnnotationMethodref()),
	INTERFACEMETHODREF  ("interfaceMethodref", new AnnotationInterfaceMethodref()),
	STRING              ("string",             new AnnotationString()),
	INTEGER             ("integer",            new AnnotationInteger()),
	FLOAT               ("float",              new AnnotationFloat()),
	LONG                ("long",               new AnnotationLong()),
	DOUBLE              ("double",             new AnnotationDouble()),
	NAME_AND_TYPE       ("nameAndType",        new AnnotationNameAndType()),
	UTF8                ("utf8",               new AnnotationUtf8()),
	METHOD_HANDLE       ("methodHandle",       new AnnotationMethodHandle()),
	METHOD_TYPE         ("methodType",         new AnnotationMethodType()),
	INVOKE_DYNAMIC      ("invokeDynamic",      new AnnotationInvokeDynamic()),
	
	METHOD              ("method",             new AnnotationMethod()),
	FIELD               ("field",              new AnnotationField()),
	ATTRIBUTE           ("attribute",          new AnnotationAttribute()),
	INTERFACE           ("interface",          new AnnotationInterface()),
	
	CODE_ATTRIBUTE      ("codeAttribute",      new AnnotationCodeAttribute()),
	
	// ----------------------------------------------------------------------
	// --------------------------- Aliases ----------------------------------
	FREF                ("fref",               new AnnotationFieldref()),
	MREF                ("mref",               new AnnotationMethodref()),
	IMREF               ("imref",              new AnnotationInterfaceMethodref()),
	NATYPE              ("nat",                new AnnotationNameAndType()),
	MHANDLE             ("mhandle",            new AnnotationMethodHandle()),
	MTYPE               ("mtype",              new AnnotationMethodType()),
	IDYN                ("idyn",               new AnnotationInvokeDynamic()),
	
	// ----------------------------------------------------------------------
	// --------------------- Language shortcuts -----------------------------
	FFREF               ("ffref",              new AnnotationFFref()),
	FMREF               ("fmref",              new AnnotationFMRef());

	private String                             name    = null;
	private Factory<? extends BasicAnnotation> factory = null;
	
	private AnnotationType(String name, Factory<? extends BasicAnnotation> factory) {
		this.name    = name;
		this.factory = factory;
	}
	
	public static Factory<? extends BasicAnnotation> findAnnotationFactory(String name) {
		for (AnnotationType annotationType : AnnotationType.values()) {
			if (annotationType.name.equals(name)) {
				return annotationType.factory;
			}
		}

		throw new RuntimeException("Unknown annotation (" + name + ").");
	}
	
	public String getName() {
		return name;
	}
}
