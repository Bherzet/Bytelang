package bytelang.compiler.annotations;

import bytelang.CompilationErrorException;
import bytelang.classes.ClassFile;
import bytelang.classes.FieldInfo;
import bytelang.classes.MethodInfo;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.BasicAnnotation;
import bytelang.compiler.annotations.set.classFile.SetClassFileAccessFlags;
import bytelang.compiler.annotations.set.classFile.SetClassFileAttributesCount;
import bytelang.compiler.annotations.set.classFile.SetClassFileFieldsCount;
import bytelang.compiler.annotations.set.classFile.SetClassFileInterfacesCount;
import bytelang.compiler.annotations.set.classFile.SetClassFileMagic;
import bytelang.compiler.annotations.set.classFile.SetClassFileMethodsCount;
import bytelang.compiler.annotations.set.classFile.SetClassFileSuperClass;
import bytelang.compiler.annotations.set.classFile.SetClassFileThisClass;
import bytelang.compiler.annotations.set.classFile.SetClassFileVersion;
import bytelang.compiler.annotations.set.field.SetFieldAttributesCount;
import bytelang.compiler.annotations.set.method.SetMethodAttributesCount;
import bytelang.compiler.annotations.set.method.SetMethodCodeLength;
import bytelang.compiler.annotations.set.method.SetMethodMaxLocals;
import bytelang.compiler.annotations.set.method.SetMethodMaxStack;
import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueType;

public class AnnotationSet extends BasicAnnotation implements Factory<AnnotationSet> {
	private TargetType targetType = null;
	private SetType    setType    = null;
	private Value      value      = null;
	
	public static enum TargetType {
		CLASS_FILE,
		METHOD,
		FIELD;
	}
	
	public static interface ClassCommandProcessor {
		public void apply(Value value, ClassFile classFile);
	}
	
	public static interface MethodCommandProcessor {
		public void apply(Value value, MethodInfo method);
	}
	
	public static interface FieldCommandProcessor {
		public void apply(Value value, FieldInfo field);
	}
	
	public static enum SetType implements ClassCommandProcessor, MethodCommandProcessor, FieldCommandProcessor {
		// Class
		MAGIC            ("magic",           TargetType.CLASS_FILE, new ValueType[]{ValueType.ARRAY                       }, new SetClassFileMagic(),           null, null),
		VERSION          ("version",         TargetType.CLASS_FILE, new ValueType[]{ValueType.ARRAY,   ValueType.STRING   }, new SetClassFileVersion(),         null, null),
		ACCESS_FLAGS     ("accessFlags",     TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER                     }, new SetClassFileAccessFlags(),     null, null),
		THIS_CLASS       ("thisClass",       TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE}, new SetClassFileThisClass(),       null, null),
		SUPER_CLASS      ("superClass",      TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER, ValueType.REFERENCE}, new SetClassFileSuperClass(),      null, null),
		INTERFACES_COUNT ("interfacesCount", TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER                     }, new SetClassFileInterfacesCount(), null, null),
		FIELDS_COUNT     ("fieldsCount",     TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER                     }, new SetClassFileFieldsCount(),     null, null),
		METHODS_COUNT    ("methodsCount",    TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER                     }, new SetClassFileMethodsCount(),    null, null),
		ATTRIBUTES_COUNT ("attributesCount", TargetType.CLASS_FILE, new ValueType[]{ValueType.INTEGER                     }, new SetClassFileAttributesCount(), null, null),
		
		// Method
		METHOD_ATTRIBUTES_COUNT ("methodAttributesCount", TargetType.METHOD, new ValueType[]{ValueType.INTEGER}, null, new SetMethodAttributesCount(), null),
		MAX_STACK               ("maxStack",              TargetType.METHOD, new ValueType[]{ValueType.INTEGER}, null, new SetMethodMaxStack(),        null),
		MAX_LOCALS              ("maxLocals",             TargetType.METHOD, new ValueType[]{ValueType.INTEGER}, null, new SetMethodMaxLocals(),       null),
		CODE_LENGTH             ("codeLength",            TargetType.METHOD, new ValueType[]{ValueType.INTEGER}, null, new SetMethodCodeLength(),      null),
		
		// Fields
		FIELD_ATTRIBUTES_COUNT  ("fieldAttributesCount",  TargetType.FIELD,  new ValueType[]{ValueType.INTEGER}, null, null, new SetFieldAttributesCount());
		
		private String                 name;
		private ValueType[]            allowedValues;
		private ClassCommandProcessor  classCommandProcessor;
		private MethodCommandProcessor methodCommandProcessor;
		private FieldCommandProcessor  fieldCommandProcessor;
		private TargetType             targetType;
		
		private SetType(String name, TargetType targetTypes, ValueType[] allowedValues, ClassCommandProcessor classCommandProcessor, MethodCommandProcessor methodCommandProcessor, FieldCommandProcessor fieldCommandProcessor) {
			this.name                   = name;
			this.targetType             = targetTypes;
			this.allowedValues          = allowedValues;
			this.classCommandProcessor  = classCommandProcessor;
			this.methodCommandProcessor = methodCommandProcessor;
			this.fieldCommandProcessor  = fieldCommandProcessor;
		}
		
		public String getName() {
			return name;
		}
		
		public ValueType[] getAllowedValues() {
			return allowedValues;
		}
		
		public static SetType findValue(String name) {
			for (SetType setType : SetType.values()) {
				if (setType.getName().equals(name)) {
					return setType;
				}
			}
			
			return null;
		}

		@Override
		public void apply(Value value, ClassFile classFile) {
			this.classCommandProcessor.apply(value, classFile);
		}

		@Override
		public void apply(Value value, MethodInfo method) {
			this.methodCommandProcessor.apply(value, method);
		}
		
		@Override
		public void apply(Value value, FieldInfo field) {
			this.fieldCommandProcessor.apply(value, field);
		}
		
		public TargetType getTargetType() {
			return targetType;
		}
	}
	
	public AnnotationSet() {
		for (SetType setType : SetType.values()) {
			this.addNonMandatoryParam(setType.getName(), setType.getAllowedValues());
		}
	}
	
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		if (elementAnnotation.getParameters().size() != 1) {
			throw new CompilationErrorException("Annotation @set expects exactly one parameter.");
		}
		
		this.validate(elementAnnotation);
		
		String paramName = elementAnnotation.getParameters().keys().nextElement();
		this.value       = elementAnnotation.getParameters().get(paramName);
		
		SetType setType  = SetType.findValue(paramName);
		if (setType != null) {
			this.setType    = setType;
			this.targetType = setType.getTargetType();
		} else {
			throw new CompilationErrorException(
				"Unrecognized @set annotation (" + paramName + ")."
			);
		}
	}

	@Override
	public AnnotationType getType() {
		return AnnotationType.SET;
	}

	@Override
	public AnnotationSet newInstance() {
		return new AnnotationSet();
	}
	
	public TargetType getTargetType() {
		return targetType;
	}
	
	public void applyToClassFile(ClassFile classFile) {
		this.setType.apply(value, classFile);
	}
	
	public void applyToMethod(MethodInfo method) {
		this.setType.apply(value, method);
	}
	
	public void applyToField(FieldInfo field) {
		this.setType.apply(value, field);
	}
}
