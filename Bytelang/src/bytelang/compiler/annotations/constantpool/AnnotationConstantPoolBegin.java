package bytelang.compiler.annotations.constantpool;

import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.NonParametricAnnotation;
import bytelang.helpers.Factory;

public class AnnotationConstantPoolBegin extends NonParametricAnnotation implements Factory<AnnotationConstantPoolBegin> {
	@Override
	public AnnotationType getType() {
		return AnnotationType.CONSTANT_POOL_BEGIN;
	}

	@Override
	public AnnotationConstantPoolBegin newInstance() {
		return new AnnotationConstantPoolBegin();
	}
}
