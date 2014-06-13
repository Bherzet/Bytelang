package bytelang.compiler.annotations.constantpool;

import java.util.Hashtable;

import bytelang.classes.constantpool.CPItem;
import bytelang.compiler.ConstantPoolBuilder;
import bytelang.compiler.annotations.general.Annotation;

public interface CPItemAnnotation extends Annotation {
	public String  getId();
	public boolean hasId();
	public void    replaceReferences(Hashtable<String, Integer> referencesTable);
	public CPItem  toCPItem(ConstantPoolBuilder constantPoolBuilder);
	public boolean isIndependent();
}
