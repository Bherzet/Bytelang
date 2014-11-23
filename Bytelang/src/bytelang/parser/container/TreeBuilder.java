package bytelang.parser.container;

import java.util.LinkedList;
import java.util.List;

import bytelang.CompilationErrorException;
import bytelang.parser.container.code.SourceLine;
import bytelang.parser.container.code.SourceLineInstruction;
import bytelang.parser.container.elements.AnnotableElement;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.elements.ElementClass;
import bytelang.parser.container.elements.ElementField;
import bytelang.parser.container.elements.ElementMethod;
import bytelang.parser.container.values.Value;
import bytelang.parser.syntactical.keywords.KeywordType;

public class TreeBuilder {
	private LinkedList<ElementAnnotation> annotations  = new LinkedList<ElementAnnotation>();
	private ElementClass                  elementClass = null;
	
	public void addAnnotation(ElementAnnotation annotation) {
		this.annotations.add(annotation);
	}
	
	public void setClass(ElementClass elementClass) {
		this.elementClass = elementClass;
		flushAnnotations(elementClass);
	}
	
	public void setClassModifiers(List<KeywordType> modifiers) {
		this.elementClass.setModifiers(modifiers);
	}
	
	public void addField(ElementField elementField) {
		flushAnnotations(elementField);
		elementClass.addField(elementField);
	}
	
	public void addMethod(ElementMethod elementMethod) {
		flushAnnotations(elementMethod);
		elementClass.addMethod(elementMethod);
	}
	
	public void addSourceLine(SourceLine sourceLine) {
		List<ElementMethod> methods = this.elementClass.getMethods();
		methods.get(methods.size() - 1).addSourceCodeLine(sourceLine);
	}
	
	public void addInstructionParameter(Value parameter) {
		List<ElementMethod> methods = this.elementClass.getMethods();
		List<SourceLine>    code    = methods.get(methods.size() - 1).getCode();
		SourceLine          line    = code.get(code.size() - 1);
		
		if (line instanceof SourceLineInstruction) {
			((SourceLineInstruction) line).addParameter(parameter);
		} else {
			throw new CompilationErrorException("Parameter must be preceded by the instruction.");
		}
	}
	
	private void flushAnnotations(AnnotableElement element) {
		for (ElementAnnotation annotation : annotations) {
			element.addAnnotation(annotation);
		}
		
		annotations.clear();
	}
	
	public ElementClass getElementClass() {
		return elementClass;
	}
}
