package bytelang.compiler.annotations.general;

import bytelang.parser.container.elements.ElementAnnotation;

public abstract class NonParametricAnnotation extends BasicAnnotation {
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		if (elementAnnotation.getParameters().size() > 0) {
			throw new RuntimeException("Annotation @" + getType().getName() + " doesn't take any parameter.");
		}
	}
}
