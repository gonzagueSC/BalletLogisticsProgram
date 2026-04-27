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
	public static final String format = "PERFORMANCE-%04d";
	
	public PerformancesRegistryEntity ( File Production ) throws IllegalClassFormatException, IOException {
		
		File Registry = new File(Production, RegistryName);
		
		super(Registry, CastEntity.class);
		
	}
	
	public String getPerformanceID ( String performanceName ) {
		
		return this.entityData.get(performanceName);
		
	}
	
	public void addPerformance ( String performanceName ) throws IOException {
		
		this.entityData.put(performanceName, getNextPerformanceID());
		this.save();
		
	}
	
	public String getNextPerformanceID () {
		
		return this.getNextID(format);
		
	}
	
	public PerformanceEntity getPerformance ( String performanceName ) throws IllegalClassFormatException,
		   IOException, InvocationTargetException, NoSuchMethodException, InstantiationException,
		   IllegalAccessException {
		
		if ( !this.entityData.containsKey(performanceName) ) {
			throw new IllegalArgumentException("Performance not found");
		}
		
		return this.getPerformance(this.getPerformanceID(performanceName), true);
		
	}
	
	public PerformanceEntity getPerformance ( String performanceID, boolean withID ) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if ( withID ) {
			
			return this.getEntity(performanceID, true);
			
		}
		
		return this.getPerformance(performanceID);
		
	}
	
	public List<String> getAllPerformances () {
		
		return new ArrayList<>(this.entityData.keySet());
		
	}
	
	public void changeRole ( String oldRole, String newRole ) throws IOException, InvocationTargetException,
		   NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		List<String> performances = getAllPerformances();
		
		for ( String performance : performances ) {
			
			PerformanceEntity performanceEntity = this.getEntity(performance, true);
			performanceEntity.editRole(oldRole, newRole);
			
		}
		
	}
	
	public void removePerformance ( String performanceName ) throws IOException, InvocationTargetException,
		   NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if ( !this.entityData.containsKey(performanceName) )
			throw new IllegalArgumentException("Performance does not exist in registry");
		
		PerformanceEntity performance = this.getEntity(this.getPerformanceID(performanceName), true);
		
		performance.killFile();
		
		this.entityData.remove(performanceName);
		this.save();
		
	}
	
	public void removeRole ( String role ) throws IOException, InvocationTargetException, NoSuchMethodException,
		   InstantiationException, IllegalAccessException {
		
		List<String> performances = getAllPerformances();
		
		for ( String performance : performances ) {
			
			PerformanceEntity performanceEntity = this.getEntity(this.getPerformanceID(performance), true);
			performanceEntity.removeRole(role);
			
		}
		
	}
	
	public void removeCast ( String castName ) throws IOException, InvocationTargetException, NoSuchMethodException,
		   InstantiationException, IllegalAccessException {
		
		List<String> performances = getAllPerformances();
		
		for ( String performance : performances ) {
			
			PerformanceEntity performanceEntity = this.getEntity(this.getPerformanceID(performance), true);
			performanceEntity.removeCast(castName);
			
		}
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(FileType);
		
	}
	
}
