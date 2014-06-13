package bytelang.parser.container.elements;

import java.util.ArrayList;
import java.util.List;

import bytelang.parser.syntactical.keywords.KeywordType;

public class ElementClass implements Element, AnnotableElement {
	private String                  name                = null;
	private String                  superClass          = null;
	private List<KeywordType>       modifiers           = new ArrayList<>();
	private List<String>            implemented         = new ArrayList<String>();
	private List<ElementAnnotation> attachedAnnotations = new ArrayList<ElementAnnotation>();
	private List<ElementField>      fields              = new ArrayList<ElementField>();
	private List<ElementMethod>     methods             = new ArrayList<ElementMethod>();
	
	public ElementClass(String name, String superClass, List<String> implemented) {
		this.name        = name;
		this.superClass  = superClass;
		this.implemented = implemented;
	}

	@Override
	public ElementType getType() {
		return ElementType.CLASS;
	}
	
	public void addField(ElementField field) {
		this.fields.add(field);
	}
	
	public void addMethod(ElementMethod method) {
		this.methods.add(method);
	}
	
	public String getName() {
		return name;
	}
	
	public String getSuperClass() {
		return superClass;
	}
	
	public List<String> getImplemented() {
		return implemented;
	}
	
	public List<ElementField> getFields() {
		return fields;
	}
	
	public List<ElementMethod> getMethods() {
		return methods;
	}

	@Override
	public void addAnnotation(ElementAnnotation annotation) {
		this.attachedAnnotations.add(annotation);
	}
	
	@Override
	public List<ElementAnnotation> getAnnotations() {
		return attachedAnnotations;
	}
	
	public void setModifiers(List<KeywordType> modifiers) {
		this.modifiers = modifiers;
	}
	
	public List<KeywordType> getModifiers() {
		return modifiers;
	}
}
