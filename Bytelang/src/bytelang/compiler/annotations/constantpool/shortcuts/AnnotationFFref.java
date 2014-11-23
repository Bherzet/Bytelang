package bytelang.compiler.annotations.constantpool.shortcuts;

import bytelang.compiler.annotations.general.AnnotationType;

public class AnnotationFFref extends AnnotationFastRef {
	@Override
	public AnnotationType getType() {
		return AnnotationType.FFREF;
	}
}
