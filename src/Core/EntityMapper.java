package Core;

import java.lang.instrument.IllegalClassFormatException;

public class EntityMapper {
	
	public Class<? extends DBEntity>[] EntitiesClasses = new Class[] {
	
	};
	
	public Object[] EntitiesList = new Object[EntitiesClasses.length];
	
	@SuppressWarnings("all")
	public EntityMapper() throws NoSuchMethodException {
		
		try {
			
			for (int i = 0; i < EntitiesList.length; i++) {
				
				EntitiesList[i] = EntitiesClasses[i].getConstructor().newInstance();
				
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public <T extends DBEntity> T getEntity(Class<T> EntityClass) throws IllegalClassFormatException {
	
		for (int i = 0; i < EntitiesClasses.length; i++) {
			
			if (EntitiesClasses[i] == EntityClass) {
				
				if (EntityClass.isInstance(EntitiesList[i])) {
					return (T) EntitiesList[i];
				} else {
					
					throw new IllegalClassFormatException("EntitiesList and EntitiesClasses are not correctly " +
						   "lined up.");
					
				}
			
			}
			
		}
		
		return null;
	
	}
	
}
