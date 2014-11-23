package bytelang.compiler.annotations.general;

import java.util.Hashtable;

import bytelang.CompilationErrorException;
import bytelang.parser.container.elements.ElementAnnotation;
import bytelang.parser.container.values.Value;
import bytelang.parser.container.values.ValueString;
import bytelang.parser.container.values.ValueType;

public abstract class BasicAnnotation implements Annotation {
	private Hashtable<String, ValueType[]> mandatoryParams    = new Hashtable<String, ValueType[]>();
	private Hashtable<String, ValueType[]> nonMandatoryParams = new Hashtable<String, ValueType[]>();
	
	public abstract void           fromElementAnnotation(ElementAnnotation elementAnnotation);
	public abstract AnnotationType getType();
	
	protected void addMandatoryParam(String name, ValueType[] allowedTypes) {
		this.mandatoryParams.put(name, allowedTypes);
	}
	
	protected void addNonMandatoryParam(String name, ValueType[] allowedTypes) {
		this.nonMandatoryParams.put(name, allowedTypes);
	}
	
	protected void validate(ElementAnnotation elementAnnotation) {
		Hashtable<String, Boolean> mandatoryTable = createMandatoryParamsTable();
		Hashtable<String, Value>   parameters     = elementAnnotation.getParameters();
		
		for (String paramName : parameters.keySet()) {
			if (mandatoryParams.get(paramName) != null) {
				if (!checkType(parameters.get(paramName).getType(), mandatoryParams.get(paramName))) {
					throw new CompilationErrorException(
						"Annotation @" + elementAnnotation.getName() + " doesn't take argument of type " +
						parameters.get(paramName).getType() + " for mandatory argument \"" + paramName + "\"."
					);
				}
				
				mandatoryTable.put(paramName, true);
			} else if (nonMandatoryParams.get(paramName) != null) {
				if (!checkType(parameters.get(paramName).getType(), nonMandatoryParams.get(paramName))) {
					throw new CompilationErrorException(
						"Annotation @" + elementAnnotation.getName() + " doesn't take argument of type " +
						parameters.get(paramName).getType() + " for non-mandatory argument \"" + paramName + "\"."
					);
				}
			} else {
				throw new CompilationErrorException(
					"Annotation " + elementAnnotation.getName() + " doesn't take argument " + paramName + "."
				);
			}
		}
		
		for (String key : mandatoryTable.keySet()) {
			if (mandatoryTable.get(key) == false) {
				throw new CompilationErrorException(
					"Missing argument \"" + key + "\" for annotation @" + elementAnnotation.getName() + "."
				);
			}
		}
	}
	
	private Hashtable<String, Boolean> createMandatoryParamsTable() {
		Hashtable<String, Boolean> result = new Hashtable<String, Boolean>();
		
		for (String key : mandatoryParams.keySet()) {
			result.put(key, false);
		}
		
		return result;
	}
	
	private boolean checkType(ValueType realType, ValueType[] allowedTypes) {
		for (ValueType allowedType : allowedTypes) {
			if (realType.equals(allowedType)) {
				return true;
			}
		}
		
		return false;
	}
	
	protected String grepId(Hashtable<String, Value> parameters) {
		if (parameters.get("id") == null) {
			return null;
		} else {
			if (parameters.get("id").getType() == ValueType.STRING) {
				return ((ValueString) parameters.get("id")).getString();
			} else {
				throw new CompilationErrorException("Identifier must be a string.");
			}
		}
	}
}
