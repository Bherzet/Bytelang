package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetType;

public class CPItemLong extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	private final int tag = 5;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(staticLength = 4)
	private short[] highBytes;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(staticLength = 4)
	private short[] lowBytes;

	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemLong() {
		super(CPItemType.LONG);
	}

	public CPItemLong(String id, short[] highBytes, short[] lowBytes) {
		this();
		this.identifier = id;
		this.highBytes  = highBytes;
		this.lowBytes   = lowBytes;
	}
}
