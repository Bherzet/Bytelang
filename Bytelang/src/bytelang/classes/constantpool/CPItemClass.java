package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemClass extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final short tag = 7;
	
	@SetType(type = BinaryType.UINT_16)
	public int nameIndex;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemClass() {
		super(CPItemType.CLASS);
	}

	public CPItemClass(String id, int nameIndex) {
		this();
		this.identifier = id;
		this.nameIndex  = nameIndex;
	}
}
