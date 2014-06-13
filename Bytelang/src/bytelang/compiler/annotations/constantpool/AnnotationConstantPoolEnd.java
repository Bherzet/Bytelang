package bytelang.compiler.annotations.constantpool;

import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.NonParametricAnnotation;
import bytelang.helpers.Factory;

public class AnnotationConstantPoolEnd extends NonParametricAnnotation implements Factory<AnnotationConstantPoolEnd> {
	@Override
	public AnnotationType getType() {
		return AnnotationType.CONSTANT_POOL_END;
	}

	@Override
	public AnnotationConstantPoolEnd newInstance() {
		return new AnnotationConstantPoolEnd();
	}
}
