package bytelang.parser.container.elements;

import java.util.ArrayList;
import java.util.List;

import bytelang.parser.syntactical.keywords.KeywordType;

public class ElementField implements Element, AnnotableElement {
	private String                  name        = null;
	private String                  typeName    = null;
	private List<KeywordType>       modifiers   = null;
	private List<ElementAnnotation> annotations = new ArrayList<ElementAnnotation>();
	
	public ElementField(String name, String typeName, List<KeywordType> modifiers) {
		this.name      = name;
		this.typeName  = typeName;
		this.modifiers = modifiers;
	}
	
	@Override
	public ElementType getType() {
		return ElementType.FIELD;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public List<KeywordType> getModifiers() {
		return modifiers;
	}
	
	@Override
	public String toString() {
		return "[Field: " + name + ", type: " + typeName + ", modifiers: " + modifiers + "]";
	}
	
	@Override
	public void addAnnotation(ElementAnnotation annotation) {
		this.annotations.add(annotation);
	}
	
	@Override
	public List<ElementAnnotation> getAnnotations() {
		return annotations;
	}
}
