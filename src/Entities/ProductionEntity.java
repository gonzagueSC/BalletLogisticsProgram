package Entities;

import Core.EntityField;
import Core.JSONObjectEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ProductionEntity extends JSONObjectEntity {
	
	private final CastRegistryEntity castsRegistry;
	private final PerformancesRegistryEntity performancesRegistry;
	private final RolesEntity rolesEntity;
	private final RehearsalsRegistryEntity rehearsalsEntity;
	
	@SuppressWarnings("All")
	private final File productionsFolder;
	
	public static final EntityField productionName = new EntityField("production_name", true, String.class);
	public static final EntityField startDate = new EntityField("first_performance", true, String.class);
	public static final EntityField endDate = new EntityField("last_performance", true, String.class);
	
	public ProductionEntity ( String Name, File folder ) throws IOException, IllegalClassFormatException {
		
		super(Name, folder);
		
		ENTITY_FIELDS = new EntityField[]{ productionName, startDate, endDate };
		
		productionsFolder = folder;
		
		castsRegistry = new CastRegistryEntity(productionsFolder);
		rolesEntity = new RolesEntity(productionsFolder);
		performancesRegistry = new PerformancesRegistryEntity(productionsFolder);
		rehearsalsEntity = new RehearsalsRegistryEntity(productionsFolder);
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllCasts () {
		
		return this.castsRegistry.getAllCasts();
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllPerformances () {
		
		return this.performancesRegistry.getAllPerformances();
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllRoles () throws IOException {
		
		return rolesEntity.getAllRoles();
		
	}
	
	@SuppressWarnings("unused")
	public Map<String, String> getAllRehearsals (){
		
		return this.rehearsalsEntity.getRehearsals();
		
	}
	
	@SuppressWarnings("unused")
	public CastRegistryEntity getCastsRegistry () {
		
		return this.castsRegistry;
		
	}
	
	@SuppressWarnings("unused")
	public PerformancesRegistryEntity getPerformancesRegistry () {
		
		return this.performancesRegistry;
		
	}
	
	@SuppressWarnings("unused")
	public RehearsalsRegistryEntity getRehearsalsRegistry () {
		
		return this.rehearsalsEntity;
	}
	
	@SuppressWarnings("unused")
	public void addPerformance ( String performanceName ) throws IOException, IllegalClassFormatException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.performancesRegistry.addPerformance(performanceName);
		this.performancesRegistry.getPerformance(performanceName).updateRoles(this.rolesEntity.getAllRoles());
		
	}
	
	@SuppressWarnings("unused")
	public void addRole ( String roleName ) throws IllegalClassFormatException, IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.rolesEntity.addRole(roleName);
		for ( String performanceName : this.performancesRegistry.getAllPerformances() ) {
			
			PerformanceEntity performance = this.performancesRegistry.getPerformance(performanceName);
			
			performance.updateRoles(this.rolesEntity.getAllRoles());
			
		}
		
		for ( String castName : this.castsRegistry.getAllCasts() ) {
			
			CastEntity cast = this.castsRegistry.getCast(castName);
			
			cast.updateRoles(this.rolesEntity.getAllRoles());
			
		}
		
	}
	
	@SuppressWarnings("unused")
	public void addCast ( String castName ) throws IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		CastEntity cast = this.castsRegistry.getCast(castName);
		cast.updateRoles(this.rolesEntity.getAllRoles());
		
	}
	
	@SuppressWarnings("unused")
	public void addRehearsal ( String rehearsalName, String timeStart, String timeEnd, String date ) throws IllegalClassFormatException, IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.rehearsalsEntity.addRehearsal(rehearsalName, timeStart, timeEnd, date);
		
	}
	
	@SuppressWarnings("unused")
	public void removePerformance ( String performanceName ) throws IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.performancesRegistry.removePerformance(performanceName);
		
	}
	
	@SuppressWarnings("unused")
	public void removeRehearsal ( String rehearsalName, String timeStart, String timeEnd, String date ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.rehearsalsEntity.removeRehearsal(this.rehearsalsEntity.getRehearsal(rehearsalName, timeStart, timeEnd,
			   date));
		
	}
	
	@SuppressWarnings("unused")
	public void removeCast ( String castName ) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	
		this.castsRegistry.removeCast(castName);
		
		this.performancesRegistry.removeCast(castName);
		
	}
	
	@SuppressWarnings("unused")
	public RehearsalEntity getRehearsal ( String name, String timeStart, String timeEnd, String date ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return this.rehearsalsEntity.getRehearsal(name, timeStart, timeEnd, date);
		
	}
	
	@SuppressWarnings("unused")
	public CastEntity getCast ( String castName ) throws
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return this.castsRegistry.getCast(castName);
		
	}
	
	@SuppressWarnings("unused")
	public PerformanceEntity getPerformance ( String performanceName ) throws IllegalClassFormatException,
		   IOException, InvocationTargetException, NoSuchMethodException, InstantiationException,
		   IllegalAccessException {
		
		return this.performancesRegistry.getPerformance(performanceName);
		
	}
	
	@SuppressWarnings("unused")
	public void changeRoleName ( String oldRole, String newRole ) throws IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.rolesEntity.changeRole(oldRole, newRole);
		
		this.castsRegistry.changeRoleName(oldRole, newRole);
		
		this.performancesRegistry.changeRole(oldRole, newRole);
		
		this.rehearsalsEntity.changeRole(oldRole, newRole);
		
	}
	
	@SuppressWarnings("unused")
	public void removeRole ( String roleName ) throws IllegalClassFormatException, IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.rolesEntity.removeRole(roleName);
		
		this.castsRegistry.removeRole(roleName);
		
		this.performancesRegistry.removeRole(roleName);
		
		this.rehearsalsEntity.removeRole(roleName);
		
	}
	
}
