package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class CPItemInvokeDynamic extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 18;
	
	@SetType(type = BinaryType.UINT_16)
	public int bootstrapMethodAttrIndex;
	
	@SetType(type = BinaryType.UINT_16)
	public int nameAndTypeIndex;

	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemInvokeDynamic() {
		super(CPItemType.INVOKE_DYNAMIC);
	}

	public CPItemInvokeDynamic(String id, int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
		this();
		this.identifier               = id;
		this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
		this.nameAndTypeIndex         = nameAndTypeIndex;
	}
}
