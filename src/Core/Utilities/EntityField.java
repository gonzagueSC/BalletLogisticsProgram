package Core.Utilities;

public class EntityField {
	
	private final String name;
	private final boolean mutable;
	private final Class<?> dataType;
	private final boolean notNull;
	private final Object defaultValue;
	
	public <T> EntityField(String name, Class<T> dataType) {
		
		this.name = name;
		this.mutable = true;
		this.dataType = dataType;
		this.notNull = false;
		this.defaultValue = null;
		
	}
	
	public <T> EntityField(String name, boolean mutable, Class<T> dataType) {
	
		this.name = name;
		this.mutable = mutable;
		this.dataType = dataType;
		this.notNull = false;
		this.defaultValue = null;
	
	}
	
	public <T> EntityField(String name, Class<T> dataType, boolean notNull) {
		
		this.name = name;
		this.mutable = true;
		this.dataType = dataType;
		this.notNull = notNull;
		this.defaultValue = null;
		
	}
	
	public <T> EntityField(String name, Class<T> dataType, boolean notNull, Object defaultValue) {
		
		this.name = name;
		this.mutable = true;
		this.dataType = dataType;
		this.notNull = notNull;
		this.defaultValue = defaultValue;
		
	}
	
	public <T> EntityField(String name, boolean mutable, Class<T> dataType, boolean notNull) {
		
		this.name = name;
		this.mutable = mutable;
		this.dataType = dataType;
		this.notNull = notNull;
		this.defaultValue = null;
		
	}
	
	public <T> EntityField(String name, boolean mutable, Class<T> dataType, boolean notNull, Object defaultValue) {
		
		this.name = name;
		this.mutable = mutable;
		this.dataType = dataType;
		this.notNull = notNull;
		this.defaultValue = defaultValue;
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public boolean isImmutable () {
		
		return !this.mutable;
		
	}
	
	public Class<?> getFieldType() {
		
		return this.dataType;
		
	}
	
	public boolean isNotNull() {
		
		return this.notNull;
		
	}
	
	public Object getDefaultValue() { return this.defaultValue;}
	
}
