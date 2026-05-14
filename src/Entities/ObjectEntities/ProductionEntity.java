package Entities.ObjectEntities;

import Core.Databases.JSONObjectEntity;
import Core.Utilities.AppError;
import Core.Utilities.AppWarning;
import Core.Utilities.Chronos;
import Core.Utilities.EntityField;
import util.Globals;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.time.LocalDate;
import java.util.*;
import static Core.Utilities.DataConstants.*;

public class ProductionEntity extends JSONObjectEntity {
	
	private final CastRegistry castsRegistry;
	private final PerformanceRegistry performancesRegistry;
	private final RolesEntity rolesEntity;
	private final RehearsalsRegistry rehearsalsEntity;
	private final ProductionCostumesRegistry costumesRegistry;
	
	@SuppressWarnings("All")
	private final File productionsFolder;
	
	public ProductionEntity ( String Name, File folder ) throws IOException, IllegalClassFormatException, AppWarning {
		
		super(Name, folder);
		
		ENTITY_FIELDS = new EntityField[]{ NAME, START_DATE, END_DATE };
		
		productionsFolder = folder;
		
		castsRegistry = new CastRegistry(productionsFolder);
		rolesEntity = new RolesEntity(productionsFolder);
		performancesRegistry = new PerformanceRegistry(productionsFolder);
		rehearsalsEntity = new RehearsalsRegistry(productionsFolder);
		costumesRegistry = new ProductionCostumesRegistry(productionsFolder);
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllCasts () {
		
		return this.castsRegistry.getAllCastNames();
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllPerformances () {
		
		return this.performancesRegistry.getAllPerformanceNames();
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllRoles () throws IOException, AppWarning {
		
		return rolesEntity.getAllRoles();
		
	}
	
	@SuppressWarnings("unused")
	public Map<String, Map<String, String>> getAllRehearsals () {
		
		return this.rehearsalsEntity.getRehearsals();
		
	}
	
	@SuppressWarnings("unused")
	public CastRegistry getCastsRegistry () {
		
		return this.castsRegistry;
		
	}
	
	@SuppressWarnings("unused")
	public PerformanceRegistry getPerformancesRegistry () {
		
		return this.performancesRegistry;
		
	}
	
	@SuppressWarnings("unused")
	public RehearsalsRegistry getRehearsalsRegistry () {
		
		return this.rehearsalsEntity;
	}
	
	@SuppressWarnings("unused")
	public void addPerformance ( String performanceName ) throws IOException, AppWarning {
		
		String ID = this.performancesRegistry.addPerformance(performanceName);
		this.performancesRegistry.loadRoles(ID, this.rolesEntity.getAllRoles());
		
	}
	
	@SuppressWarnings("unused")
	public void addRole ( String roleName ) throws IllegalClassFormatException, IOException, AppWarning {
		
		this.rolesEntity.addRole(roleName);
		this.performancesRegistry.addRoles(roleName);
		castsRegistry.addRole(roleName);
		this.costumesRegistry.addRole(roleName);
		
	}
	
	@SuppressWarnings("unused")
	public void addCast ( String castName ) throws IOException, AppWarning {
		
		castsRegistry.addCast(castName);
		
	}
	
	@SuppressWarnings("unused")
	public void addRehearsal ( String rehearsalName, String timeStart, String timeEnd, String date ) throws IOException, AppWarning {
		
		this.rehearsalsEntity.setUpRehearsal(rehearsalName, timeStart, timeEnd, date);
		
	}
	
	@SuppressWarnings("unused")
	public void addPerformance(String performanceName, String date) throws Exception {
	
		String ID = this.performancesRegistry.addPerformance(performanceName, date);
		
		this.performancesRegistry.loadRoles(ID, this.rolesEntity.getAllRoles());
		
		this.checkForChange(date, true);
	
	}
	
	@SuppressWarnings("unused")
	public void removePerformance ( String performanceName ) throws Exception {
		
		LocalDate performanceDate = this.performancesRegistry.getFieldValue(this.performancesRegistry.getPerformanceID(performanceName), DATE);
		
		this.performancesRegistry.removePerformance(performanceName);
		
		this.checkForChange(performanceDate.toString(), false);
		
	}
	
	@SuppressWarnings("unused")
	public void removeRehearsal ( String rehearsalName, String timeStart, String timeEnd, String date ) throws AppWarning {
		
		this.rehearsalsEntity.removeRehearsal(this.rehearsalsEntity.getRehearsal(rehearsalName, timeStart, timeEnd,
			   date));
		
	}
	
	@SuppressWarnings("unused")
	public void removeCast ( String castName ) throws IOException, AppWarning {
		
		this.castsRegistry.removeCast(castName);
		
		this.performancesRegistry.removeCasts(castName);
		
	}
	
	@SuppressWarnings("unused")
	public Map<EntityField, String> getRehearsal ( String name, String timeStart, String timeEnd, String date ) throws AppWarning {
		
		return this.rehearsalsEntity.getRehearsal(name, timeStart, timeEnd, date);
		
	}
	
	@SuppressWarnings("unused")
	public Map<String, String> getCast ( String castName ) throws AppWarning {
		
		return this.castsRegistry.getCast(castName);
		
	}
	
	@SuppressWarnings("unused")
	public String getPerformanceID ( String performanceName ) throws AppWarning {
		
		return this.performancesRegistry.getPerformanceID(performanceName);
		
	}
	
	@SuppressWarnings("unused")
	public void changeRoleName ( String oldRole, String newRole ) throws AppError, AppWarning, IOException {
		
		this.rolesEntity.changeRole(oldRole, newRole);
		
		this.castsRegistry.editRoleName(oldRole, newRole);
		
		this.performancesRegistry.editRole(oldRole, newRole);
		
		this.rehearsalsEntity.changeRole(oldRole, newRole);
		
		this.costumesRegistry.editRole(oldRole, newRole);
		
	}
	
	@SuppressWarnings("unused")
	public void removeRole ( String roleName ) throws IllegalClassFormatException, IOException, AppError, AppWarning {
		
		this.rolesEntity.removeRole(roleName);
		
		this.castsRegistry.removeRole(roleName);
		
		this.performancesRegistry.removeRole(roleName);
		
		this.rehearsalsEntity.removeRole(roleName);
		
		this.costumesRegistry.deleteRole(roleName);
		
	}
	
	public void checkForChange(String date, boolean adding) throws Exception {
		
		LocalDate startDate = this.getFieldValue(START_DATE);
		LocalDate endDate = this.getFieldValue(END_DATE);
		LocalDate providedDate = Chronos.getStandardizedDate(date);
		
		boolean dateIsBefore = !providedDate.isAfter(startDate);
		boolean dateIsAfter = !providedDate.isBefore(endDate);
		
		boolean requiresChange = (dateIsAfter || dateIsBefore);
		
		if (requiresChange) {
			
			if (adding) {
				
				if (dateIsBefore) {
					
					this.saveField(START_DATE, date, false);
					return;
					
				} else {
					
					this.saveField(END_DATE, date, false);
					return;
					
				}
				
			} else {
			
				List<String> performanceDates = this.performancesRegistry.getAllOfField(DATE);
				
				LocalDate target = Chronos.getStandardizedDate(this.getFieldValue((dateIsBefore)?END_DATE:START_DATE));
				
				int currentDistance = Chronos.unitsBetween(target.toString(), providedDate.toString(), Globals.MINUTES);
				
				for (String performanceDate: performanceDates) {
				
					int newDistance = Chronos.unitsBetween(performanceDate, providedDate.toString(), Globals.MINUTES);
					
					if (currentDistance > newDistance) {
						
						currentDistance = newDistance;
						
						target = Chronos.getStandardizedDate(performanceDate);
						
					}
				
				}
				
				this.saveField((dateIsBefore)?START_DATE:END_DATE, Chronos.standardizeDate(target), false);
			
			}
			
		}
		
	}
	
	
}
