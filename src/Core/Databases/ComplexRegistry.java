package Core.Databases;

import Core.Utilities.EntityField;
import Core.Utilities.AppWarning;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static Core.Utilities.DataConstants.JSON_TYPE;

public class ComplexRegistry extends SimpleRegistry {
	
	Map<String, DBEntity> loadedEntitiesList = new HashMap<>();
	
	private final Class<? extends DBEntity> ObjectType;
	protected static final String FileType = JSON_TYPE;
	
	public <T extends DBEntity> ComplexRegistry ( File file, EntityField[] fields, String format, Class<T> type) throws IllegalClassFormatException, IOException, AppWarning {
		
		super(file, fields, format);
		this.ObjectType = type;
		
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <T extends DBEntity> T getEntity ( String filePath ) throws InvocationTargetException,
		   NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if ( !entityIsLoaded(filePath) ) {
			
			createEntity(filePath);
			
		}
		
		return (T) loadedEntitiesList.get(filePath);
		
	}
	
	@SuppressWarnings("All")
	public boolean entityIsLoaded ( String filePath ) {
		
		return (loadedEntitiesList.containsKey(filePath));
		
	}
	
	public void createEntity ( String filePath ) throws NoSuchMethodException, InvocationTargetException,
		   InstantiationException, IllegalAccessException {
		
		DBEntity newLoad = this.ObjectType.getConstructor(String.class).newInstance(filePath);
		
		loadedEntitiesList.put(filePath, newLoad);
		
	}
	
	
	@SuppressWarnings({"unchecked", "unused"})
	public synchronized <T extends DBEntity> T getEntity ( String filePath, boolean buildFromPath ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if ( !entityIsLoaded(filePath) && buildFromPath ) {
			
			createEntity(filePath, new File(file.toPath().getParent().toString()));
			
		}
		
		return (T) loadedEntitiesList.get(filePath);
		
	}
	
	@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
	public synchronized void removeEntity ( String filePath ) throws IOException, AppWarning {
		
		DBEntity Entity = this.loadedEntitiesList.get(filePath);
		
		Entity.file.delete();
		
		this.loadedEntitiesList.remove(filePath);
		
		this.save();
		
	}
	
	public void createEntity ( String filePath, File folder ) throws NoSuchMethodException, InvocationTargetException
		   , InstantiationException, IllegalAccessException {
		
		DBEntity newLoad = this.ObjectType.getConstructor(String.class, File.class).newInstance(filePath, folder);
		
		loadedEntitiesList.put(filePath, newLoad);
		
	}
	
	public Map<String, String> getData () {
		
		return this.entityData;
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning {
		
		setDataType(FileType);
	}
	
}
