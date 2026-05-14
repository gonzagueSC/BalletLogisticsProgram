package Core.Databases;

import Core.Utilities.AppWarning;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Core.Utilities.DataConstants.*;

public abstract class RegistryEntity extends DBEntity {
	
	protected static final String FileType = JSON_TYPE;
	
	private final Class<? extends DBEntity> ObjectType;
	
	Map<String, DBEntity> loadedEntitiesList = new HashMap<>();
	
	public <T extends DBEntity> RegistryEntity ( File registry, Class<T> type ) throws IOException, IllegalClassFormatException, AppWarning {
		
		super(registry);
		
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
	
	public String getNextID(String format) {
		
		if (this.entityData.isEmpty()) {
			
			return String.format(format, 1);
			
		} else {
			
			HashMap<Integer, String> IDs = new HashMap<>();
			
			int maxID = 0;
			
			for (String cast : this.entityData.keySet()) {
				
				int ID = Integer.parseInt(this.entityData.get(cast).substring(5));
				
				maxID = Math.max(maxID, ID);
				
				IDs.put(ID, cast);
				
			}
			
			return getIDValue(format, IDs, maxID);
			
		}
	
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
