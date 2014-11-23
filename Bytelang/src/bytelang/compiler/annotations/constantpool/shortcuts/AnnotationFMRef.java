package bytelang.compiler.annotations.constantpool.shortcuts;

import bytelang.compiler.annotations.general.AnnotationType;

public class AnnotationFMRef extends AnnotationFastRef {
	@Override
	public AnnotationType getType() {
		return AnnotationType.FMREF;
	}
}
