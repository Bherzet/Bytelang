package bytelang.compiler;

import java.util.ArrayList;
import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.classes.constantpool.CPItem;
import bytelang.classes.constantpool.CPItemClass;
import bytelang.classes.constantpool.CPItemNameAndType;
import bytelang.classes.constantpool.CPItemType;
import bytelang.classes.constantpool.CPItemUtf8;
import bytelang.compiler.annotations.constantpool.CPItemAnnotation;
import bytelang.compiler.annotations.general.AnnotationType;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueInteger;
import bytelang.parser.container.values.ValueReference;
import bytelang.parser.container.values.ValueType;

public class ConstantPoolBuilder {
	private boolean           locked            = false;
	private ArrayList<CPItem> constantPoolItems = new ArrayList<>();
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public void fromAnotations(ArrayList<CPItemAnnotation> annotations) {
		replaceReferences(annotations);
		
		for (int i = 0; i < annotations.size(); i++) {
			if (annotations.get(i).isIndependent()) {
				constantPoolItems.add(annotations.get(i).toCPItem(null));
			} else {
				constantPoolItems.add(null);
			}
		}
		
		for (int i = 0; i < annotations.size(); i++) {
			if (constantPoolItems.get(i) == null) {
				constantPoolItems.set(i, annotations.get(i).toCPItem(this));
			}
		}
	}
	
	public int addItemUtf8(String string) {
		for (int i = 0; i < constantPoolItems.size(); i++) {
			if (constantPoolItems.get(i) != null && constantPoolItems.get(i).getType() == CPItemType.UTF8) {
				short[] containedStringBytes = ((CPItemUtf8) constantPoolItems.get(i)).bytes;
				char[]  containedStringChars = new char[containedStringBytes.length];
				
				for (int j = 0; j < containedStringBytes.length; j++) {
					containedStringChars[j] = (char) containedStringBytes[j];
				}
				
				String containedString = String.valueOf(containedStringChars);
				if (containedString.equals(string)) {
					return getConstantPoolIndex(i);
				}
			}
		}
		
		if (!locked) {
			byte[]  newStringBytes  = string.getBytes();
			short[] newStringShorts = new short[newStringBytes.length];
			
			for (int i = 0; i < newStringBytes.length; i++) {
				newStringShorts[i] = newStringBytes[i];
			}
			
			this.constantPoolItems.add(new CPItemUtf8(null, newStringShorts.length, newStringShorts));
			return getConstantPoolIndex(constantPoolItems.size() - 1);
		} else {
			throw new CompilationErrorException(
				"Annotation @lock has been applied, but there are constant-pool items (UTF8) to be generated."
			);
		}
	}
	
	public int addItemClass(String className) {
		for (int i = 0; i < constantPoolItems.size(); i++) {
			if (constantPoolItems.get(i) != null && constantPoolItems.get(i).getType() == CPItemType.CLASS) {
				CPItemClass itemClass = (CPItemClass) constantPoolItems.get(i);
				int         nameIndex = itemClass.nameIndex;
				int         itemIndex = getItemIndex(nameIndex);
				
				if (itemIndex != -1) {
					CPItem item = constantPoolItems.get(itemIndex);
					
					if (item.getType() == CPItemType.UTF8) {
						CPItemUtf8 utf8  = (CPItemUtf8) item;
						String     value = utf8.getBytesAsString();
						
						if (value != null && value.equals(className)) {
							return getConstantPoolIndex(i);
						}
					}
				}
			}
		}
		
		if (!locked) {
			int         stringNameCPIndex = addItemUtf8(className);
			CPItemClass itemClass         = new CPItemClass(null, stringNameCPIndex);
			
			constantPoolItems.add(itemClass);
			return getConstantPoolIndex(constantPoolItems.size() - 1);
		} else {
			throw new CompilationErrorException(
				"Annotation @lock has been applied, but there are constant-pool items (CLASS) to be generated."
			);
		}
	}
	
	public int addItemNameAndType(int nameIndex, int descriptorIndex) {
		for (int i = 0; i < constantPoolItems.size(); i++) {
			if (constantPoolItems.get(i) != null && constantPoolItems.get(i).getType() == CPItemType.NAME_AND_TYPE) {
				CPItemNameAndType itemNameAndType         = (CPItemNameAndType) constantPoolItems.get(i);
				int               existingNameIndex       = getItemIndex(itemNameAndType.nameIndex);
				int               existingDescriptorIndex = getItemIndex(itemNameAndType.descriptorIndex);
				
				if (existingNameIndex == nameIndex && existingDescriptorIndex == descriptorIndex) {
					return getConstantPoolIndex(i);
				}
			}
		}
		
		if (!locked) {
			CPItemNameAndType cpItemNameAndType = new CPItemNameAndType(null, nameIndex, descriptorIndex);
			constantPoolItems.add(cpItemNameAndType);
			return getConstantPoolIndex(constantPoolItems.size() - 1);
		} else {
			throw new CompilationErrorException(
				"Annotation @lock has been applied, but there are constant-pool items (NAME_AND_TYPE) to be generated."
			);
		}
	}
	
	public int getItemIndex(int constantPoolIndex) {
		for (int i = 0; i < constantPoolItems.size(); i++) {
			if (getConstantPoolIndex(i) == constantPoolIndex) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getConstantPoolIndex(int itemIndex) {
		int constantPoolIndex = 1;
		
		for (int i = 0; i < itemIndex; i++) {
			if (constantPoolItems.get(i) != null) {
				switch (constantPoolItems.get(i).getType()) {
					case LONG:
					case DOUBLE:
						constantPoolIndex += 2;
						continue;
						
					default:
						break;
				}
			}
			
			constantPoolIndex++;
		}
		
		return constantPoolIndex;
	}
	
	private void replaceReferences(ArrayList<CPItemAnnotation> annotations) {
		Hashtable<String, Integer> referencesTable = buildReferencesTable(annotations);
		
		for (CPItemAnnotation annotation : annotations) {
			annotation.replaceReferences(referencesTable);
		}
	}
	
	private Hashtable<String, Integer> buildReferencesTable(ArrayList<CPItemAnnotation> annotations) {
		Hashtable<String, Integer> result       = new Hashtable<>();
		int                        currentIndex = 1;
		
		for (CPItemAnnotation cpItemAnnotation : annotations) {
			if (cpItemAnnotation.hasId()) {
				if (result.get(cpItemAnnotation.getId()) == null) {
					result.put(cpItemAnnotation.getId(), currentIndex);
				} else {
					throw new CompilationErrorException("Multiple same identifiers found.");
				}
			}
		
			if (cpItemAnnotation.getType() == AnnotationType.DOUBLE || cpItemAnnotation.getType() == AnnotationType.LONG) {
				currentIndex += 2;
			} else {
				currentIndex++;
			}
		}
		
		return result;
	}
	
	public static Value replaceReference(Hashtable<String, Integer> referencesTable, Value value) {
		if (value.getType() == ValueType.REFERENCE) {
			String  referenceName   = ((ValueReference) value).getValue();
			Integer referenceNumber = referencesTable.get(referenceName);
			
			if (referenceNumber == null) {
				throw new CompilationErrorException("Identifier " + referenceName + " doesn't exist.");
			} else {
				return new ValueInteger(referenceNumber);
			}
		} else {
			return value;
		}
	}
	
	public CPItem[] toArray() {
		CPItem[] result = new CPItem[constantPoolItems.size()];
		
		for (int i = 0 ; i < constantPoolItems.size(); i++) {
			result[i] = constantPoolItems.get(i);
		}
		
		return result;
	}
	
	public int getCPItemIndexById(String id) {
		for (int i = 0; i < constantPoolItems.size(); i++) {
			CPItem currentItem = constantPoolItems.get(i);
			
			if (currentItem.identifier != null && currentItem.identifier.equals(id)) {
				return getConstantPoolIndex(i);
			}
		}
		
		throw new CompilationErrorException(
			"Item with identifier \"" + id + "\" is undefined."
		);
	}
}
