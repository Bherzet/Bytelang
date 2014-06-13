package bytelang.parser.container.elements;

import java.util.Hashtable;

import bytelang.parser.container.values.Value;

public class ElementAnnotation implements Element {
	private String                   name       = null;
	private Hashtable<String, Value> parameters = new Hashtable<String, Value>();
	
	public ElementAnnotation(String name) {
		this.name = name;
	}

	@Override
	public ElementType getType() {
		return ElementType.ANNOTATION;
	}
	
	public void addParameter(String name, Value value) {
		parameters.put(name, value);
	}
	
	public String getName() {
		return name;
	}
	
	public Hashtable<String, Value> getParameters() {
		return parameters;
	}
	
	@Override
	public String toString() {
		return "[Annotation: " + name + ", parameters: " + parameters + "]";
	}
}
