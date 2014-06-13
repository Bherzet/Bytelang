package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemMethodHandle extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 15;
	
	@SetType(type = BinaryType.UINT_8)
	public short referenceKind;
	
	@SetType(type = BinaryType.UINT_16)
	public int referenceIndex;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemMethodHandle() {
		super(CPItemType.METHOD_HANDLE);
	}

	public CPItemMethodHandle(String id, short referenceKind, int referenceIndex) {
		this();
		this.identifier     = id;
		this.referenceKind  = referenceKind;
		this.referenceIndex = referenceIndex;
	}
}
