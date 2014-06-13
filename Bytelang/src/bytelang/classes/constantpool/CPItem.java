package bytelang.classes.constantpool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;

import com.googlecode.jawb.JAWB;
import com.googlecode.jawb.clues.types.DynamicLengthType;

public abstract class CPItem {
	private CPItemType type       = null;
	public  String     identifier = null;

	public static class CPItemLoader implements DynamicLengthType {
		@Override
		public Object fromByteArray(InputStream stream) {
			return null;
		}

		@Override
		public byte[] toByteArray(Object object) {
			try {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				
				for (int i = 0; i < Array.getLength(object); i++) {
					JAWB.saveTo(outputStream, Array.get(object, i));
				}
				
				return outputStream.toByteArray();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	protected CPItem(CPItemType type) {
		this.type = type;
	}
	
	public CPItemType getType() {
		return type;
	}
}
