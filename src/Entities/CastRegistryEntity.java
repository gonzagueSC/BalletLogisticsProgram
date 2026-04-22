package Entities;

import Core.RegistryEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Core.DataConstants.*;

public class CastRegistryEntity extends RegistryEntity {
	
	public static final String FileType = JSON_TYPE;
	public static final String RegistryName = "Casts";
	
	public CastRegistryEntity(File Production) throws IllegalClassFormatException, IOException {
	
		File Registry = new File(Production, RegistryName);
		
		super(Registry, CastEntity.class);
	
	}
	
	public CastEntity getCast(String castName) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return this.getEntity(castName, true);
		
	}
	
	public void removeCast(String castName) throws IOException {
		
		this.removeEntity(castName);
		
	}
	
	public boolean castExists( String castName) {
		
		return this.entityData.containsKey(castName);
		
	}
	
	public List<String> getAllCasts() {
		
		List<String> casts = new ArrayList<String>(this.entityData.keySet());
		
		return casts;
	
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(FileType);
	
	}
	
}
