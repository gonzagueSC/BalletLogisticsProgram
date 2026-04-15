package Core;

public class EntityField {
	
	private final String name;
	private final boolean mutable;
	private final Object dataType;
	private final Class<?> fieldType;
	
	public <T> EntityField(String name, boolean mutable, T dataType) {
	
		this.name = name;
		this.mutable = mutable;
		this.dataType = dataType;
		this.fieldType = this.dataType.getClass();
	
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public boolean isMutable() {
		
		return this.mutable;
		
	}
	
	public Class<?> getFieldType() {
		
		return this.fieldType;
		
	}
	
}
