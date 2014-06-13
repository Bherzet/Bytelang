package bytelang.compiler.annotations.general;

import java.util.LinkedList;
import java.util.List;

import bytelang.helpers.Factory;
import bytelang.parser.container.elements.ElementAnnotation;

public abstract class AnnotationProcessor {
	public static LinkedList<Annotation> process(List<ElementAnnotation> elementAnnotations) {
		LinkedList<Annotation> annotations = new LinkedList<Annotation>();
		
		for (ElementAnnotation elementAnnotation : elementAnnotations) {
			Factory<? extends BasicAnnotation> factory    = AnnotationType.findAnnotationFactory(elementAnnotation.getName());
			BasicAnnotation                    annotation = factory.newInstance();

			annotation.fromElementAnnotation(elementAnnotation);
			annotations.add(annotation);
		}
		
		return annotations;
	}
}
