package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemString extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 8;
	
	@SetType(type = BinaryType.UINT_16)
	public int stringIndex;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemString() {
		super(CPItemType.STRING);
	}

	public CPItemString(String id, int stringIndex) {
		this();
		this.identifier  = id;
		this.stringIndex = stringIndex;
	}
}
