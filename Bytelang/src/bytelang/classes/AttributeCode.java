package bytelang.classes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.JAWB;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetPreVisitListener;
import com.googlecode.jawb.clues.SetType;

@SuppressWarnings("unused")
public class AttributeCode {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values -----------------------------------------------
	@SetType(type = BinaryType.UINT_16)
	public int maxStack;
	
	@SetType(type = BinaryType.UINT_16)
	public int maxLocals;
	
	@SetType(type = BinaryType.UINT_32)
	public long codeLength;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(dynamicLength = "realCodeLength")
	@SetPreVisitListener(methodName = "onCodePreVisit")
	public short[] code;

	@SetType(type = BinaryType.UINT_16)
	public int exceptionTableLength;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(dynamicLength = "realExceptionTableLength")
	@SetPreVisitListener(methodName = "onExceptionTablePreVisit")
	public ExceptionTableItem[] exceptionTable;
	
	@SetType(type = BinaryType.UINT_16)
	public int attributesCount;
	
	@SetType(type = BinaryType.UINT_8)
	@SetArray(dynamicLength = "realAttributesLength")
	@SetPreVisitListener(methodName = "onAttributesPreVisit")
	public Attribute[] attributes;
	
	// ---------------------------------------------------------------------------------------------
	// -------------------------------- Control variables ------------------------------------------
	private int realCodeLength;
	private int realExceptionTableLength;
	private int realAttributesLength;
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------- Control callback methods ---------------------------------------
	private void onCodePreVisit()           { this.realCodeLength = code.length;                     }
	private void onExceptionTablePreVisit() { this.realExceptionTableLength = exceptionTable.length; }
	private void onAttributesPreVisit()     { this.realAttributesLength = attributes.length;         }
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------------- Implementation -------------------------------------------
	public AttributeCode() {
		//
	}
	
	public AttributeCode(int maxStack, int maxLocals, long codeLength, short[] code, int exceptionsTableLength, ExceptionTableItem[] exceptionTable, int attributesCount, Attribute[] attributes) {
		this.maxStack             = maxStack;
		this.maxLocals            = maxLocals;
		this.codeLength           = codeLength;
		this.code                 = code;
		this.exceptionTableLength = exceptionsTableLength;
		this.exceptionTable       = exceptionTable;
		this.attributesCount      = attributesCount;
		this.attributes           = attributes;
	}
	
	public Attribute toAttribute(int nameIndex, int length) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JAWB.saveTo(outputStream, this);
			
			byte[]  output = outputStream.toByteArray();
			short[] result = new short[output.length];
			
			for (int i = 0; i < output.length; i++) {
				result[i] = output[i];
				
				if (result[i] < 0) {
					result[i] += 256;
				}
			}
			
			return new Attribute(nameIndex, (length < 0) ? result.length : length, result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
