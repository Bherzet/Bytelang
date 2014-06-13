package bytelang.parser.container.elements;

import java.util.List;

public interface AnnotableElement {
	public void                    addAnnotation(ElementAnnotation annotation);
	public List<ElementAnnotation> getAnnotations();
}
