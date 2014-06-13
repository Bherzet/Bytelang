package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetType;

public class CPItemDouble extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 6;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(staticLength = 4)
	public short[] highBytes;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(staticLength = 4)
	public short[] lowBytes;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------	
	public CPItemDouble() {
		super(CPItemType.DOUBLE);
	}

	public CPItemDouble(String id, short[] highBytes, short[] lowBytes) {
		this();
		this.identifier = id;
		this.highBytes  = highBytes;
		this.lowBytes   = lowBytes;
	}
}
