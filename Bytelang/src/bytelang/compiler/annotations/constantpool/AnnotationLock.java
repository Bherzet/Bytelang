package bytelang.compiler.annotations.constantpool;

import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.compiler.annotations.general.NonParametricAnnotation;
import bytelang.helpers.Factory;

public class AnnotationLock extends NonParametricAnnotation implements Factory<AnnotationLock> {
	@Override
	public AnnotationType getType() {
		return AnnotationType.LOCK;
	}

	@Override
	public AnnotationLock newInstance() {
		return new AnnotationLock();
	}
}
