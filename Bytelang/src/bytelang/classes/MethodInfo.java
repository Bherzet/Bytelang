package bytelang.classes;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetPreVisitListener;
import com.googlecode.jawb.clues.SetType;

public class MethodInfo {
	// ---------------------------------------------------------------------------------------------
	// --------------------------------------- Values ----------------------------------------------
	@SetType(type = BinaryType.UINT_16)
	public int accessFlags;
	
	@SetType(type = BinaryType.UINT_16)
	public int nameIndex;
	
	@SetType(type = BinaryType.UINT_16)
	public int descriptorIndex;
	
	@SetType(type = BinaryType.UINT_16)
	public int attributesCount;
	
	@SetType(type = BinaryType.OBJECT)
	@SetArray(dynamicLength = "realAttributesCount")
	@SetPreVisitListener(methodName = "onAttributesPreVisit")
	public Attribute[] attributes = new Attribute[0];
	
	// ---------------------------------------------------------------------------------------------
	// -------------------------------- Control variables ------------------------------------------
	@SuppressWarnings("unused")
	private int realAttributesCount;
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------- Control callback methods ---------------------------------------
	@SuppressWarnings("unused")
	private void onAttributesPreVisit() {
		this.realAttributesCount = attributes.length;
	}
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------------- Implementation -------------------------------------------
	public MethodInfo() {
		//
	}

	public MethodInfo(int accessFlags, int nameIndex, int descriptorIndex, int attributesCount, Attribute[] attributes) {
		this.accessFlags = accessFlags;
		this.nameIndex = nameIndex;
		this.descriptorIndex = descriptorIndex;
		this.attributesCount = attributesCount;
		this.attributes = attributes;
	}
}
