package bytelang.classes;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetType;

public class Version {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_16)
	public int minorVersion = 0;
	
	@SetType(type = BinaryType.UINT_16)
	public int majorVersion = 0x33;
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------------- Implementation -------------------------------------------
	public Version() {
		//
	}

	public Version(int minorVersion, int majorVersion) {
		this.minorVersion = minorVersion;
		this.majorVersion = majorVersion;
	}
}
