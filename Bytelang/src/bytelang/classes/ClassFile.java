package bytelang.classes;

import java.io.IOException;
import java.io.OutputStream;

import bytelang.classes.constantpool.CPItem;

import com.googlecode.jawb.BinaryType;
import com.googlecode.jawb.JAWB;
import com.googlecode.jawb.clues.SetArray;
import com.googlecode.jawb.clues.SetPreVisitListener;
import com.googlecode.jawb.clues.SetType;

@SuppressWarnings("unused")
public class ClassFile {
	// ---------------------------------------------------------------------------------------------
	// -------------------------------------- Values ----------------------------------------------
	@SetType(type = BinaryType.UINT_8)
	@SetArray(staticLength = 4)
	public short[] magic = new short[]{0xCA, 0xFE, 0xBA, 0xBE};
	
	@SetType(type = BinaryType.OBJECT)
	public Version version = new Version();

	@SetType(type = BinaryType.UINT_16)
	public int constantPoolCount;
	
	@SetType(type = BinaryType.OBJECT, dynamicLengthType = CPItem.CPItemLoader.class)
	public CPItem[] constantPoolItems = new CPItem[0];
	
	@SetType(type = BinaryType.UINT_16)
	public int accessFlags;
	
	@SetType(type = BinaryType.UINT_16)
	public int thisClass;
	
	@SetType(type = BinaryType.UINT_16)
	public int superClass;
	
	@SetType(type = BinaryType.UINT_16)
	public int interfacesCount;
	
	@SetType(type = BinaryType.UINT_16)
	@SetPreVisitListener(methodName = "onInterfacesPreVisit")
	@SetArray(dynamicLength = "realInterfacesCount")
	public int[] interfaces = new int[0];
	
	@SetType(type = BinaryType.UINT_16)
	public int fieldsCount;
	
	@SetType(type = BinaryType.OBJECT)
	@SetPreVisitListener(methodName = "onFieldsPreVisit")
	@SetArray(dynamicLength = "realFieldsCount")
	public FieldInfo[] fields = new FieldInfo[0];
	
	@SetType(type = BinaryType.UINT_16)
	public int methodsCount;

	@SetType(type = BinaryType.OBJECT)
	@SetPreVisitListener(methodName = "onMethodsPreVisit")
	@SetArray(dynamicLength = "realMethodsCount")
	public MethodInfo[] methods = new MethodInfo[0];
	
	@SetType(type = BinaryType.UINT_16)
	public int attributesCount;

	@SetType(type = BinaryType.OBJECT)
	@SetPreVisitListener(methodName = "onAttributesPreVisit")
	@SetArray(dynamicLength = "realAttributesCount")
	public Attribute[] attributes = new Attribute[0];
	
	// ---------------------------------------------------------------------------------------------
	// -------------------------------- Control variables ------------------------------------------
	private int realInterfacesCount = 0;
	private int realFieldsCount     = 0;
	private int realMethodsCount    = 0;
	private int realAttributesCount = 0;
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------- Control callback methods ---------------------------------------
	private void onInterfacesPreVisit() { this.realInterfacesCount = interfaces.length; }
	private void onFieldsPreVisit()     { this.realFieldsCount     = fields.length;     }
	private void onMethodsPreVisit()    { this.realMethodsCount    = methods.length;    }
	private void onAttributesPreVisit() { this.realAttributesCount = attributes.length; }

	// ---------------------------------------------------------------------------------------------
	// ---------------------------------- Implementation -------------------------------------------
	public ClassFile() {
	}
	
	public void saveTo(OutputStream outputStream) throws IOException {
		JAWB.saveTo(outputStream, this);
	}
}
