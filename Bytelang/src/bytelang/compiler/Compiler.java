package bytelang.compiler;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import bytelang.classes.Attribute;
import bytelang.classes.AttributeCode;
import bytelang.classes.ClassFile;
import bytelang.classes.ExceptionTableItem;
import bytelang.classes.FieldInfo;
import bytelang.classes.MethodInfo;
import bytelang.compiler.annotations.AnnotationAttribute;
import bytelang.compiler.annotations.AnnotationField;
import bytelang.compiler.annotations.AnnotationInterface;
import bytelang.compiler.annotations.AnnotationMethod;
import bytelang.compiler.annotations.AnnotationSet;
import bytelang.compiler.annotations.AnnotationSet.TargetType;
import bytelang.compiler.annotations.constantpool.CPItemAnnotation;
import bytelang.compiler.annotations.general.Annotation;
import bytelang.compiler.annotations.general.AnnotationProcessor;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.parser.container.code.SourceLine;
import bytelang.parser.container.code.SourceLineInstruction;
import bytelang.parser.container.code.SourceLineLabel;
import bytelang.parser.container.code.SourceLineType;
import bytelang.parser.container.elements.ElementClass;
import bytelang.parser.container.elements.ElementField;
import bytelang.parser.container.elements.ElementMethod;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueLabel;
import bytelang.parser.container.values.ValueReference;
import bytelang.parser.syntactical.keywords.AccClass;
import bytelang.parser.syntactical.keywords.AccField;
import bytelang.parser.syntactical.keywords.AccMethod;

public class Compiler {
	private ElementClass                   elementClass         = null;
	private ClassFile                      classFile            = new ClassFile();
	private ConstantPoolBuilder            constantPoolBuilder  = new ConstantPoolBuilder();
	private LinkedList<Annotation>         classAnnotations     = null;

	private ArrayList<AnnotationInterface> annotationsInterface = new ArrayList<>();
	private ArrayList<AnnotationMethod>    annotationsMethod    = new ArrayList<>();
	private ArrayList<AnnotationField>     annotationsField     = new ArrayList<>();
	
	public Compiler(ElementClass elementClass) {
		this.elementClass     = elementClass;
		this.classAnnotations = AnnotationProcessor.process(elementClass.getAnnotations());
	}

	public ClassFile compile() {
		this.constantPoolBuilder.setLocked(isConstantPoolLocked(classAnnotations));
		this.constantPoolBuilder.fromAnotations(getCPAnnotations());

		this.processClassAnnotationsInterfaces();
		this.processClassAnnotationsField();
		this.processClassAnnotationsMethod();
		
		this.compileClassInfo();
		this.compileFields();
		this.compileMethods();
		
		this.processClassAnnotationsAttributes();
		
		this.classFile.constantPoolCount = constantPoolBuilder.toArray().length + 1;
		this.classFile.constantPoolItems = constantPoolBuilder.toArray();

		this.processClassAnnotationsSet();
		return classFile;
	}
	
	public void compileClassInfo() {
		this.classFile.accessFlags     = AccClass.getAccessFlags(elementClass.getModifiers());
		this.classFile.thisClass       = constantPoolBuilder.addItemClass(elementClass.getName());
		this.classFile.superClass      = (elementClass.getSuperClass() == null) ?
			0 : constantPoolBuilder.addItemClass(elementClass.getSuperClass().replace('.', '/'));
		
		this.classFile.interfacesCount = elementClass.getImplemented().size() + annotationsInterface.size();
		this.classFile.interfaces      = new int[classFile.interfacesCount];
		for (int i = 0; i < elementClass.getImplemented().size(); i++) {
			this.classFile.interfaces[i] = constantPoolBuilder.addItemClass(
				elementClass.getImplemented().get(i).replace('.', '/')
			);
		}
		for (int i = elementClass.getImplemented().size(); i < classFile.interfacesCount; i++) {
			this.classFile.interfaces[i] = annotationsInterface.get(
				i - elementClass.getImplemented().size()
			).toInterfaceNumber(constantPoolBuilder);
		}
	}

	public void compileFields() {
		List<ElementField> fields  = elementClass.getFields();
		this.classFile.fieldsCount = fields.size() + annotationsField.size();
		this.classFile.fields      = new FieldInfo[classFile.fieldsCount];

		for (int i = 0; i < fields.size(); i++) {
			ElementField field                       = fields.get(i);
			this.classFile.fields[i]                 = new FieldInfo();
			this.classFile.fields[i].accessFlags     = AccField.getAccessFlags(field.getModifiers());
			this.classFile.fields[i].nameIndex       = constantPoolBuilder.addItemUtf8(field.getName());
			this.classFile.fields[i].descriptorIndex = constantPoolBuilder.addItemUtf8(getDescriptor(field.getTypeName()));

			LinkedList<Annotation>         annotations           = AnnotationProcessor.process(field.getAnnotations());
			ArrayList<AnnotationAttribute> annotationsAttributes = grepAnnotationsAttributes(annotations);
			
			this.classFile.fields[i].attributesCount = annotationsAttributes.size();
			this.classFile.fields[i].attributes      = new Attribute[annotationsAttributes.size()];
			for (int j = 0; j < annotationsAttributes.size(); j++) {
				this.classFile.fields[i].attributes[j] = annotationsAttributes.get(j).toAttribute(constantPoolBuilder);
			}
			
			for (Annotation annotation : annotations) {
				if (annotation.getType() == AnnotationType.SET) {
					AnnotationSet annotationSet = (AnnotationSet) annotation;
					
					if (annotationSet.getTargetType() != TargetType.FIELD) {
						throw new RuntimeException(
							"Unrecognized annotation @set has been found for the field."
						);
					}
					
					annotationSet.applyToField(this.classFile.fields[i]);
				} else {
					throw new RuntimeException("Unrecognized annotation has been attached to the field.");
				}
			}
		}
		
		for (int i = fields.size(); i < classFile.fieldsCount; i++) {
			this.classFile.fields[i] = annotationsField.get(i - fields.size()).toFieldInfo(constantPoolBuilder);
		}
	}
	
	public void compileMethods() {
		List<ElementMethod> methods = elementClass.getMethods();
		this.classFile.methodsCount = methods.size() + annotationsMethod.size();
		this.classFile.methods      = new MethodInfo[classFile.methodsCount];
		
		for (int i = 0; i < methods.size(); i++) {
			ElementMethod method                      = methods.get(i);
			this.classFile.methods[i]                 = new MethodInfo();
			this.classFile.methods[i].accessFlags     = AccMethod.getAccessFlags(method.getModifiers());
			this.classFile.methods[i].nameIndex       = constantPoolBuilder.addItemUtf8(method.getName());
			this.classFile.methods[i].descriptorIndex = constantPoolBuilder.addItemUtf8(
				getMethodDescriptor(method.getReturnType(), method.getParameters())
			);

			LinkedList<Annotation>         annotations              = AnnotationProcessor.process(method.getAnnotations());
			ArrayList<AnnotationAttribute> annotationsAttribute     = grepAnnotationsAttributes(annotations);
			ArrayList<AnnotationAttribute> annotationsCodeAttribute = grepAnnotationsCodeAttribute(annotations);
			boolean                        hasCode                  = method.getCode().size() > 0;
			
			this.classFile.methods[i].attributesCount           = annotationsAttribute.size() + (hasCode ? 1 : 0);
			this.classFile.methods[i].attributes                = new Attribute[this.classFile.methods[i].attributesCount];
			
			if (method.getCode().size() > 0) {
				int         codeAttrsCount = annotationsCodeAttribute.size();
				Attribute[] codeAttrs      = new Attribute[codeAttrsCount];

				for (int j = 0; j < codeAttrsCount; j++) {
					codeAttrs[j] = annotationsCodeAttribute.get(j).toAttribute(constantPoolBuilder);
				}
				
				short[] compiled                          = compileMethodCode(method.getCode());
				this.classFile.methods[i].attributes[0]   = new AttributeCode(
					0xFFFF, 0xFFFF, compiled.length, compiled,
					0, new ExceptionTableItem[0],
					codeAttrsCount, codeAttrs
				).toAttribute(constantPoolBuilder.addItemUtf8("Code"), -1);
			}
			
			for (int j = (hasCode ? 1 : 0); j < this.classFile.methods[i].attributesCount; j++) {
				this.classFile.methods[i].attributes[j] = annotationsAttribute.get(j - (hasCode ? 1 : 0)).toAttribute(
					constantPoolBuilder
				);
			}
			
			for (Annotation annotation : annotations) {
				if (annotation.getType() == AnnotationType.SET) {
					AnnotationSet annotationSet = (AnnotationSet) annotation;
					
					if (annotationSet.getTargetType() != TargetType.METHOD) {
						throw new RuntimeException(
							"Unrecognized annotation @set found in the method."
						);
					}
					
					annotationSet.applyToMethod(this.classFile.methods[i]);
				}
			}
		}
		
		for (int i = methods.size(); i < classFile.methodsCount; i++) {
			this.classFile.methods[i] = annotationsMethod.get(i - methods.size()).toMethodInfo(constantPoolBuilder);
		}
	}
	
	public short[] compileMethodCode(List<SourceLine> sourceLines) {
		Hashtable<String, Integer> labels             = getLabelsPosition(sourceLines);
		List<Short>                compiled           = new ArrayList<>();
		int                        currentInstruction = 0;
		
		for (SourceLine sourceLine : sourceLines) {
			SourceLineInstruction instruction = (SourceLineInstruction) sourceLine;
			List<Value>           parameters  = instruction.getParameters();
			
			if (instruction.getParameters().size() != instruction.getInstructionType().getParameters().length) {
				Formatter formatter = new Formatter();
				formatter.format(
					"Instruction %s excepts exactly %d arguments (%d were given).",
					instruction.getInstructionType(),
					instruction.getInstructionType().getParameters().length,
					instruction.getParameters().size()
				);
				
				String errorMessage = formatter.toString();
				formatter.close();
				throw new RuntimeException(errorMessage);
			}
			
			compiled.add(instruction.getInstructionType().getOpcode());
			for (int i = 0; i < parameters.size(); i++) {
				Value   parameter      = parameters.get(i);
				int     prefferedSize  = instruction.getInstructionType().getPrefferedOperandSize(i);
				long    value          = 0;
				short[] convertedValue = null;

				switch (parameter.getType()) {
					case INTEGER:
						value = ((ValueInteger) parameter).getValue();
						break;

					case LABEL:
						value = detJumpOffset(
							sourceLines, labels, currentInstruction,
							((ValueLabel) parameter).getLabelName()
						);
						break;
						
					case REFERENCE:
						value = constantPoolBuilder.getCPItemIndexById(
							((ValueReference) parameter).getValue()
						);
						break;
						
					case ARRAY:
					case STRING:
					default:
						throw new RuntimeException(
							"Value of type " + parameter.getType() + " is not acceptable as an instruction's parameter."
						);
				}
				
				if (prefferedSize != -1) {
					boolean unsigned = instruction.getInstructionType().isUnsigned(i);
					
					if (unsigned) {
						switch (prefferedSize) {
							case 1: convertedValue = getUnsignedInt1B((short) value); break;
							case 2: convertedValue = getUnsignedInt2B((int) value);   break;
							case 4: convertedValue = getUnsignedInt4B(value);         break;
							default:
								throw new UnsupportedOperationException();
						}
					} else {
						switch (prefferedSize) {
							case 1: convertedValue = getSignedInt1B((short) value); break;
							case 2: convertedValue = getSignedInt2B((int) value);   break;
							case 4: convertedValue = getSignedInt4B(value);         break;
							default:
								throw new UnsupportedOperationException();
						}
					}
				} else {
					switch (parameter.getType()) {
						case INTEGER:   convertedValue = getUnsignedInt2B((int) value); break;
						case REFERENCE: convertedValue = getUnsignedInt2B((int) value); break;
						case LABEL:     convertedValue = getSignedInt2B((int) value);   break;
							
						case ARRAY:
						case STRING:
						default:
							throw new UnsupportedOperationException();
					}
				}
				
				for (short singleByte : convertedValue) {
					compiled.add(singleByte);
				}
			}
			
			currentInstruction++;
		}
		
		short[] result = new short[compiled.size()];
		for (int i = 0; i < compiled.size(); i++) {
			result[i] = compiled.get(i);
		}
		
		return result;
	}
	
	public void processClassAnnotationsSet() {
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.SET) {
				AnnotationSet annotation = (AnnotationSet) classAnnotations.get(i);
				if (annotation.getTargetType() == TargetType.CLASS_FILE) {
					annotation.applyToClassFile(classFile);
				} else {
					throw new RuntimeException("Invalid annotation found for the class file.");
				}
				
				classAnnotations.remove(i--);
			}
		}
	}
	
	public void processClassAnnotationsAttributes() {
		List<AnnotationAttribute> annotations = new ArrayList<>();
		
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.ATTRIBUTE) {
				annotations.add((AnnotationAttribute) classAnnotations.get(i));
				classAnnotations.remove(i--);
			}
		}
		
		this.classFile.attributes      = new Attribute[annotations.size()];
		this.classFile.attributesCount = annotations.size();
		for (int i = 0; i < annotations.size(); i++) {
			this.classFile.attributes[i] = annotations.get(i).toAttribute(constantPoolBuilder);
		}
	}
	
	public void processClassAnnotationsInterfaces() {
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.INTERFACE) {
				this.annotationsInterface.add((AnnotationInterface) classAnnotations.get(i));
				this.classAnnotations.remove(i--);
			}
		}
	}
	
	public ArrayList<AnnotationAttribute> grepAnnotationsAttributes(LinkedList<Annotation> annotations) {
		ArrayList<AnnotationAttribute> result = new ArrayList<>();
		for (int i = 0; i < annotations.size(); i++) {
			if (annotations.get(i).getType() == AnnotationType.ATTRIBUTE) {
				result.add((AnnotationAttribute) annotations.get(i));
				annotations.remove(i--);
			}
		}
		
		return result;
	}
	
	public ArrayList<AnnotationAttribute> grepAnnotationsCodeAttribute(LinkedList<Annotation> annotations) {
		ArrayList<AnnotationAttribute> result = new ArrayList<>();
		for (int i = 0; i < annotations.size(); i++) {
			if (annotations.get(i).getType() == AnnotationType.CODE_ATTRIBUTE) {
				result.add((AnnotationAttribute) annotations.get(i));
				annotations.remove(i--);
			}
		}
		
		return result;
	}
	
	public void processClassAnnotationsMethod() {
		ArrayList<AnnotationMethod> result = new ArrayList<>();
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.METHOD) {
				result.add((AnnotationMethod) classAnnotations.get(i));
				classAnnotations.remove(i--);
			}
		}
		
		this.annotationsMethod = result;
	}
	
	public void processClassAnnotationsField() {
		ArrayList<AnnotationField> result = new ArrayList<>();
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.FIELD) {
				result.add((AnnotationField) classAnnotations.get(i));
				classAnnotations.remove(i--);
			}
		}
		
		this.annotationsField = result;
	}
	
	public Hashtable<String, Integer> getLabelsPosition(List<SourceLine> sourceLines) {
		Hashtable<String, Integer> result = new Hashtable<>();
		
		for (int i = 0; i < sourceLines.size(); i++) {
			SourceLine sourceLine = sourceLines.get(i);
			
			if (sourceLine.getType() == SourceLineType.LABEL) {
				SourceLineLabel label = (SourceLineLabel) sourceLine;
				
				result.put(label.getLabelName(), i);
				sourceLines.remove(i--);
			}
		}
		
		return result;
	}
	
	public int detJumpOffset(List<SourceLine> sourceLines, Hashtable<String, Integer> labels, int currentInstruction, String toLabel) {
		Integer jumpTo = labels.get(toLabel);
		if (jumpTo == null) {
			throw new RuntimeException("Instruction reffers to a label (" + toLabel + ") which doesn't exist.");
		}
		
		boolean forward = currentInstruction <= jumpTo;
		int     result  = 0;
		for (int i = (forward ? currentInstruction : jumpTo); i < (forward ? jumpTo : currentInstruction); i++) {
			result += ((SourceLineInstruction) sourceLines.get(i)).getInstructionSize();
		}
		
		return forward ? result : -result;
	}

	private static short[] getSignedInt1B(short value) {
		return new short[]{
			(short) ((value < 0) ? (0x100 + value) : value)
		};
	}
	
	private static short[] getSignedInt2B(int value) {
		int k = (value < 0) ? (0x10000 + value) : value;
		return new short[]{
			(short) (k >> 8), (short) (k & 0xFF)
		};
	}
	
	private static short[] getSignedInt4B(long value) {
		throw new UnsupportedOperationException();
	}
	
	public static short[] getUnsignedInt1B(short value) {
		return new short[]{value};
	}
	
	private static short[] getUnsignedInt2B(int value) {
		return new short[]{
			(short) (value >> 8), (short) (value & 0xFF)
		};
	}
	
	private static short[] getUnsignedInt4B(long value) {
		throw new UnsupportedOperationException();
	}
	
	private String getDescriptor(String type) {
		type = type.trim();
		
		int    dimensions = getArrayDimensions(type);
		String typeName   = type.replaceAll("\\[\\]", "");
		String array      = getArrayDescriptor(dimensions);
		
		switch (typeName) {
			case "byte":    return array + "B";
			case "char":    return array + "C";
			case "double":  return array + "D";
			case "float":   return array + "F";
			case "int":     return array + "I";
			case "long":    return array + "J";
			case "short":   return array + "S";
			case "boolean": return array + "Z";
			case "void":    return "V";
		}
		
		return array + "L" + typeName.replace('.', '/') + ";";
	}
	
	private String getMethodDescriptor(String returnType, List<String> parameters) {
		String tmp = "";
		
		for (String parameter : parameters) {
			tmp += getDescriptor(parameter);
		}
		
		return "(" + tmp + ")" + getDescriptor(returnType);
	}
	
	private int getArrayDimensions(String type) {
		int result = 0;
		
		while (type.contains("[]")) {
			result++;
			type = type.replaceFirst("\\[\\]", "");
		}
		
		return result;
	}
	
	private String getArrayDescriptor(int count) {
		String result = "";
		
		for (int i = 0; i < count; i++) {
			result += '[';
		}
		
		return result;
	}
	
	private ArrayList<CPItemAnnotation> getCPAnnotations() {
		boolean                     inConstantPoolDef       = false;
		ArrayList<CPItemAnnotation> constantPoolAnnotations = new ArrayList<CPItemAnnotation>();
		
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.CONSTANT_POOL_BEGIN) {
				inConstantPoolDef = true;
				classAnnotations.remove(i--);
			} else if (classAnnotations.get(i).getType() == AnnotationType.CONSTANT_POOL_END) {
				inConstantPoolDef = false;
				classAnnotations.remove(i--);
			} else if (inConstantPoolDef) {
				switch (classAnnotations.get(i).getType()) {
					// basic constant-pool items
					case CLASS:
					case FIELDREF:
					case METHODREF:
					case INTERFACEMETHODREF:
					case STRING:
					case INTEGER:
					case FLOAT:
					case LONG:
					case DOUBLE:
					case NAME_AND_TYPE:
					case UTF8:
					case METHOD_HANDLE:
					case METHOD_TYPE:
					case INVOKE_DYNAMIC:
					// language shortcuts
					case FFREF:
					case FMREF:
						constantPoolAnnotations.add((CPItemAnnotation) classAnnotations.get(i));
						classAnnotations.remove(i--);
						break;
						
					default:
						throw new RuntimeException(
							"Inappropriate annotation (" + classAnnotations.get(i).getType() +
							") found in the constant pool section."
						);
				}
			}
		}
		
		return constantPoolAnnotations;
	}
	
	private boolean isConstantPoolLocked(LinkedList<Annotation> classAnnotations) {
		for (int i = 0; i < classAnnotations.size(); i++) {
			if (classAnnotations.get(i).getType() == AnnotationType.LOCK) {
				classAnnotations.remove(i);
				return true;
			}
		}
		
		return false;
	}
}
