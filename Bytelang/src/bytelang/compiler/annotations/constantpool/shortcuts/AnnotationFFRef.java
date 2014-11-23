package bytelang.compiler.annotations.constantpool.shortcuts;

import bytelang.compiler.annotations.general.AnnotationType;

public class AnnotationFFRef extends AnnotationFastRef {
	@Override
	public AnnotationType getType() {
		return AnnotationType.FFREF;
	}
}
