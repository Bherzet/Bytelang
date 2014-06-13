package bytelang.classes.constantpool;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetIgnored;
import com.googlecode.jawb.clues.SetPreVisitListener;
import com.googlecode.jawb.clues.SetType;

public class CPItemUtf8 extends CPItem {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	public final int tag = 1; 
	
	@SetType(type = BinaryType.UINT_16)
	public int length;
	
	@SetType(type = BinaryType.UINT_8)
	@SetPreVisitListener(methodName = "onBytesPreVisit")
	@SetArray(dynamicLength = "actualLength")
	public short[] bytes;
	
	// ---------------------------------------------------------------------------------------------
	// ------------------------------- Control variables -------------------------------------------
	@SetIgnored()
	private int actualLength;

	// ---------------------------------------------------------------------------------------------
	// --------------------------- Control callback methods ----------------------------------------
	@SuppressWarnings("unused")
	private void onBytesPreVisit() {
		this.actualLength = bytes.length;
	}
	
	// ---------------------------------------------------------------------------------------------
	// --------------------------------- Implementation --------------------------------------------
	public CPItemUtf8() {
		super(CPItemType.UTF8);
	}

	public CPItemUtf8(String id, int length, short[] bytes) {
		this();
		this.identifier = id;
		this.length     = length;
		this.bytes      = bytes;
	}
	
	public String getBytesAsString() {
		if (bytes == null) {
			return null;
		}
		
		char[] charArray = new char[bytes.length];
		
		for (int i = 0; i < bytes.length; i++) {
			charArray[i] = (char) bytes[i];
		}
		
		return String.valueOf(charArray);
	}
}
