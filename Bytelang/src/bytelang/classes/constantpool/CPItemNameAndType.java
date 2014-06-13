package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemNameAndType extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 12;
	
	@SetType(type = BinaryType.UINT_16)
	public int nameIndex;
	
	@SetType(type = BinaryType.UINT_16)
	public int descriptorIndex;

	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemNameAndType() {
		super(CPItemType.NAME_AND_TYPE);
	}

	public CPItemNameAndType(String id, int nameIndex, int descriptorIndex) {
		this();
		this.identifier      = id;
		this.nameIndex       = nameIndex;
		this.descriptorIndex = descriptorIndex;
	}
}
