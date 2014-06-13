package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemFieldref extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type =  BinaryType.UINT_8)
	public final short tag = 9;
	
	@SetType(type = BinaryType.UINT_16)
	public int classIndex;
	
	@SetType(type = BinaryType.UINT_16)
	public int nameAndTypeIndex;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemFieldref() {
		super(CPItemType.FIELDREF);
	}

	public CPItemFieldref(String id, int classIndex, int nameAndTypeIndex) {
		this();
		this.identifier       = id;
		this.classIndex       = classIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
	}
}
