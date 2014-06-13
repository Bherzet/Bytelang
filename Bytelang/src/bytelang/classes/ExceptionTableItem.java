package bytelang.classes;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class ExceptionTableItem {
	// ---------------------------------------------------------------------------------------------
	// --------------------------------------- Values ----------------------------------------------
	@SetType(type = BinaryType.UINT_16)
	public int startPC;
	
	@SetType(type = BinaryType.UINT_16)
	public int endPC;
	
	@SetType(type = BinaryType.UINT_16)
	public int handlerPC;
	
	@SetType(type = BinaryType.UINT_16)
	public int catchType;
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------------- Implementation -------------------------------------------
	public ExceptionTableItem() {
		//
	}

	public ExceptionTableItem(int startPC, int endPC, int handlerPC, int catchType) {
		this.startPC   = startPC;
		this.endPC     = endPC;
		this.handlerPC = handlerPC;
		this.catchType = catchType;
	}
}
