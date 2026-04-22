package Entities;

import Core.EntityField;
import Core.JSONObjectEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

public class ProductionEntity extends JSONObjectEntity {
	
	private final CastRegistryEntity castsRegistry;
	private final PerformancesRegistryEntity performancesRegistry;
	private final RolesEntity rolesEntity;
	private final RehearsalsEntity rehearsalsEntity;
	
	private File productionsFolder;
	
	public static final EntityField productionName = new EntityField("production_name", true, String.class);
	public static final EntityField startDate = new EntityField("first_performance", true, String.class);
	public static final EntityField endDate = new EntityField("last_performance", true, String.class);
	
	public ProductionEntity ( String Name, File folder ) throws IOException, IllegalClassFormatException {
		
		super(Name, folder);
		
		ENTITY_FIELDS = new EntityField[]{productionName, startDate, endDate};
		
		productionsFolder = folder;
		
		castsRegistry = new CastRegistryEntity(productionsFolder);
		rolesEntity = new RolesEntity(productionsFolder);
		performancesRegistry = new PerformancesRegistryEntity(productionsFolder);
		rehearsalsEntity = new RehearsalsEntity(productionsFolder);
		
	}
	
	public List<String> getAllCasts() {
	
		return this.castsRegistry.getAllCasts();
	
	}
	
	public List<String> getAllPerformances() {
	
		return this.performancesRegistry.getAllPerformances();
	
	}
	
	public List<String> getAllRoles() throws IOException {
		
		return rolesEntity.getAllRoles();
		
	}
	
	public List<String> getAllRehearsals() throws IOException {
	
		return this.rehearsalsEntity.getAllRehearsals();
	
	}
	
	public CastRegistryEntity getCastsRegistry() {
		
		return this.castsRegistry;
		
	}
	
	public PerformancesRegistryEntity getPerformancesRegistry() {
		
		return this.performancesRegistry;
		
	}
	
	public void addPerformance(String performanceName) {
	
	
	
	}
	
	public void addRole(String roleName) {
	
	
	
	}
	
	public void addCast(String castName) {
	
	
	
	}
	
	public void addRehearsal(List<String> roles, String timeStart, String timeEnd) {
	
	
	
	}
	
	public void removePerformance(String performanceName) {
	
	
	
	}
	
	public void removeRole(String roleName) {
	
	
	
	}
	
	public void removeRehearsal(List<String> roles, String timeStart, String timeEnd) {
	
	
	
	}
	
	public void removeCast(String castName) {
	
	
	
	}
	
	public void editCastName(String oldCastName, String newCastName) {
	
	
	
	}
	
	public void editRoleName(String oldRoleName, String newRoleName) {
	
	
	
	}
	
	public void editPerformance(String performanceName)
	
}
