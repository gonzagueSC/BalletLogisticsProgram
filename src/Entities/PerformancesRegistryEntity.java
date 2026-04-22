package Entities;

import Core.RegistryEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static Core.DataConstants.*;

public class PerformancesRegistryEntity extends RegistryEntity {
	
	public static final String FileType = JSON_TYPE;
	public static final String RegistryName = "Performances";
	
	public PerformancesRegistryEntity(File Production) throws IllegalClassFormatException, IOException {
		
		File Registry = new File(Production, RegistryName);
		
		super(Registry, CastEntity.class);
		
	}
	
	public String getPerformanceID(String performanceName) {
	
		return this.entityData.get(performanceName);
	
	}
	
	public void addPerformance(String performanceName) throws IOException {
		
		this.entityData.put(performanceName, getPerformanceID(performanceName));
		this.save();
		
	}
	
	public String getNextPerformanceID() {
		
		return String.format("PERFORMANCE-%04d", this.entityData.size() + 1);
		
	}
	
	public PerformanceEntity getPerformance(String performanceID) throws IllegalClassFormatException, IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return this.getEntity(performanceID, true);
	
	}
	
	public List<String> getAllPerformances() {
		
		List<String> performances = new ArrayList<String>(this.entityData.keySet());
		
		return performances;
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(FileType);
	
	}
	
}
