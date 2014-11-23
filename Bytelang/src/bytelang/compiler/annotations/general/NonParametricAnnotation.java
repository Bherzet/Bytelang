package bytelang.compiler.annotations.general;

import bytelang.CompilationErrorException;
import bytelang.parser.container.elements.ElementAnnotation;

public abstract class NonParametricAnnotation extends BasicAnnotation {
	@Override
	public void fromElementAnnotation(ElementAnnotation elementAnnotation) {
		if (elementAnnotation.getParameters().size() > 0) {
			throw new CompilationErrorException("Annotation @" + getType().getName() + " doesn't take any parameter.");
		}
	}
}
