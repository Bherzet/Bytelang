package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemMethodType extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 16;
	
	@SetType(type = BinaryType.UINT_16)
	public int descriptorIndex;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemMethodType() {
		super(CPItemType.METHOD_TYPE);
	}

	public CPItemMethodType(String id, int descriptorIndex) {
		this();
		this.identifier      = id;
		this.descriptorIndex = descriptorIndex;
	}
}
