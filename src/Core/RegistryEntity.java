package Core;

import Entities.StudentEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Core.DataConstants.*;

public abstract class RegistryEntity extends DBEntity {
	
	protected static final String FileType = JSON_TYPE;
	
	private final Class<? extends DBEntity> ObjectType;
	
	Map<String, DBEntity> loadedEntitiesList = new HashMap<String, DBEntity>();
	
	public <T extends DBEntity> RegistryEntity ( File registry, Class<T> type ) throws IOException,
		   IllegalClassFormatException {
		
		super(registry);
		
		this.ObjectType = type;
		
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <T extends DBEntity> T getEntity ( String filePath ) throws IllegalClassFormatException,
		   IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if (!entityIsLoaded(filePath)) {
			
			createEntity(filePath);
			
		}
		
		return (T) loadedEntitiesList.get(filePath);
		
	}
	
	public boolean entityIsLoaded ( String filePath ) {
		
		return (loadedEntitiesList.containsKey(filePath));
		
	}
	
	public void createEntity ( String filePath ) throws IllegalClassFormatException, IOException,
		   NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		
		DBEntity newLoad = this.ObjectType.getConstructor(String.class).newInstance(filePath);
		
		loadedEntitiesList.put(filePath, newLoad);
		
	}
	
	
	@SuppressWarnings("unchecked")
	public synchronized <T extends DBEntity> T getEntity ( String filePath, boolean buildFromPath ) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if (!entityIsLoaded(filePath)) {
			
			createEntity(filePath, new File(file.toPath().getParent().toString()));
			
		}
		
		return (T) loadedEntitiesList.get(filePath);
		
	}
	
	public synchronized void removeEntity(String filePath) throws IOException {
		
		DBEntity Entity = this.loadedEntitiesList.get(filePath);
		
		Entity.file.delete();
		
		this.loadedEntitiesList.remove(filePath);
		
		this.save();
		
	}
	
	public void createEntity ( String filePath, File folder) throws IllegalClassFormatException, IOException,
		   NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		
		DBEntity newLoad = this.ObjectType.getConstructor(String.class).newInstance(filePath, folder);
		
		loadedEntitiesList.put(filePath, newLoad);
		
	}
	
	public Map<String, String> getData() {
		
		return this.entityData;
		
	}
	
}
