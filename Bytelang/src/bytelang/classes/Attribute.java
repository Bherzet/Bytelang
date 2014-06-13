package bytelang.classes;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetPreVisitListener;
import com.googlecode.jawb.clues.SetType;

public class Attribute {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_16)
	public int nameIndex;
	
	@SetType(type = BinaryType.UINT_32)
	public long length;

	@SetType(type = BinaryType.UINT_8)
	@SetPreVisitListener(methodName = "onDataPreVisit")
	@SetArray(dynamicLength = "realLength")
	public short[] data = new short[0];
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Control variables -----------------------------------------
	@SuppressWarnings("unused")
	private int realLength;
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------- Control callback methods ----------------------------------------
	@SuppressWarnings("unused")
	private final void onDataPreVisit() {
		this.realLength = data.length;
	}

	// ---------------------------------------------------------------------------------------------
	// -------------------------------- Implementation ---------------------------------------------
	public Attribute() {
		//
	}
	
	public Attribute(int nameIndex, long length, short[] data) {
		this.nameIndex = nameIndex;
		this.length    = length;
		this.data      = data;
	}

}
