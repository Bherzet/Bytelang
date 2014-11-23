package bytelang;

@SuppressWarnings("serial")
public class CompilationErrorException extends RuntimeException {
	/**
	 * Non-parametric constructor. Do nothing.
	 */
	public CompilationErrorException() {
		super();
	}

	/**
	 * Passes value to the Exception's constructor.
	 *
	 * @param message Error message describing the error.
	 * @param cause   Original exception which caused the error.
	 */
	public CompilationErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Passes value to the Exception's constructor.
	 *
	 * @param message Error message describing the error.
	 */
	public CompilationErrorException(String message) {
		super(message);
	}

	/**
	 * Passes value to the Exception's constructor.
	 *
	 * @param cause Original exception which caused the error.
	 */
	public CompilationErrorException(Throwable cause) {
		super(cause);
	}
}
