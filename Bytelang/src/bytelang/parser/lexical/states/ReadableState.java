package bytelang.parser.lexical.states;

import bytelang.parser.container.values.Value;

public interface ReadableState {
	public Value toTypedValue();
}
