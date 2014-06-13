package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetType;

public class CPItemFloat extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 4;

	@SetType(type = BinaryType.UINT_8)
	@SetArray(staticLength = 4)
	public short[] bytes;

	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemFloat() {
		super(CPItemType.FLOAT);
	}

	public CPItemFloat(String id, short[] bytes) {
		this();
		this.identifier = id;
		this.bytes      = bytes;
	}
}
