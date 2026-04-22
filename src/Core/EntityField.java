package Core;

public class EntityField {
	
	private final String name;
	private final boolean mutable;
	private final Class<?> dataType;
	
	public <T> EntityField(String name, boolean mutable, Class<T> dataType) {
	
		this.name = name;
		this.mutable = mutable;
		this.dataType = dataType;
	
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public boolean isMutable() {
		
		return this.mutable;
		
	}
	
	public Class<?> getFieldType() {
		
		return this.dataType;
		
	}
	
}
