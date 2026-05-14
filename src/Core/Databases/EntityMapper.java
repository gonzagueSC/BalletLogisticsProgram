package Core.Databases;

import Core.Utilities.AppWarning;
import Entities.TopLevelEntities.*;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

public class EntityMapper {
	
	private static EntityMapper INSTANCE;
	@SuppressWarnings("unchecked")
	public Class<? extends DBEntity>[] EntitiesClasses = new Class[]{
		   StudentsRegistryEntity.class,
		   ConfigEntity.class,
		   ProductionsRegistryEntity.class,
		   SchedulesRegistry.class,
		   TeachersRegistry.class,
		   StudiosRegistry.class,
		   LevelsRegistry.class,
		   AdminRolesRegistry.class,
		   AdminRegistry.class
	};
	
	public Object[] EntitiesList = new Object[EntitiesClasses.length];
	
	@SuppressWarnings("all")
	public EntityMapper () throws NoSuchMethodException {
		
		try {
			
			for (int i = 0; i < EntitiesList.length; i++) {
				
				EntitiesList[i] = EntitiesClasses[i].getConstructor().newInstance();
				
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public <T extends DBEntity> T getEntity ( Class<T> EntityClass ) throws IllegalClassFormatException {
		
		for (int i = 0; i < EntitiesClasses.length; i++) {
			
			if (EntitiesClasses[i] == EntityClass) {
				
				if (EntityClass.isInstance(EntitiesList[i])) {
					return (T) EntitiesList[i];
				} else {
					
					throw new IllegalClassFormatException("EntitiesList and EntitiesClasses are not correctly " + "lined up.");
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	@SuppressWarnings({"unchecked", "unused"})
	public <T extends DBEntity> void UpdateEntities ( Class<T>[] UpdateList ) throws IllegalClassFormatException, IOException, AppWarning {
		
		for (int i = 0; i < EntitiesClasses.length; i++) {
			
			for ( Class<T> tClass : UpdateList ) {
				
				if ( tClass == EntitiesClasses[i] ) {
					
					if ( tClass.isInstance(EntitiesList[i]) ) {
						((T) EntitiesList[i]).collectData();
					}
					
				}
				
			}
			
		}
		
	}
	
	public static EntityMapper getInstance() {
		
		if (INSTANCE == null) {
			
			try {
				
				INSTANCE = new EntityMapper();
				
			} catch (NoSuchMethodException e) {
				
				System.err.println("At least 1 entity has not been correctly set up");
				
			}
		
		}
		
		return INSTANCE;
		
	}
	
}
