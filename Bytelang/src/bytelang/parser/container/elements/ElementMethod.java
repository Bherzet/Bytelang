package bytelang.parser.container.elements;

import java.util.ArrayList;
import java.util.List;

import bytelang.parser.container.code.SourceLine;
import bytelang.parser.syntactical.keywords.KeywordType;

public class ElementMethod implements Element, AnnotableElement {
	private String                  name        = null;
	private String                  returnType  = null;
	private List<String>            parameters  = new ArrayList<String>();
	private List<ElementAnnotation> annotations = new ArrayList<ElementAnnotation>();
	private List<SourceLine>        code        = new ArrayList<SourceLine>();
	private List<KeywordType>       modifiers   = null;
	
	public ElementMethod(String name, String returnType, List<KeywordType> modifiers, List<String> parameters) {
		this.name       = name;
		this.returnType = returnType;
		this.modifiers  = modifiers;
		this.parameters = parameters;
	}
	
	@Override
	public ElementType getType() {
		return ElementType.METHOD;
	}
	
	public String getName() {
		return name;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public List<String> getParameters() {
		return parameters;
	}
	
	public void addSourceCodeLine(SourceLine line) {
		this.code.add(line);
	}

	@Override
	public void addAnnotation(ElementAnnotation annotation) {
		this.annotations.add(annotation);
	}
	
	@Override
	public List<ElementAnnotation> getAnnotations() {
		return annotations;
	}
	
	public List<SourceLine> getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return "[Method: " + name + ", return type: " + returnType + ", parameters: " + parameters + "]";
	}
	
	public List<KeywordType> getModifiers() {
		return modifiers;
	}
}
