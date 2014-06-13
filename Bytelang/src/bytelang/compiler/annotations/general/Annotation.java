package bytelang.compiler.annotations.general;

import bytelang.parser.container.elements.ElementAnnotation;

public interface Annotation {
	public void           fromElementAnnotation(ElementAnnotation elementAnnotation);
	public AnnotationType getType();
}
