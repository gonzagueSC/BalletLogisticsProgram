package Entities.TopLevelEntities;

import Core.Utilities.AppWarning;
import Core.Databases.DB;
import Core.Utilities.FilePaths;
import Core.Databases.RegistryEntity;
import Entities.ObjectEntities.ProductionEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static Core.Utilities.DataConstants.*;

public class ProductionsRegistryEntity extends RegistryEntity {
	
	public static final String dataType = JSON_TYPE;
	
	private static final Class<ProductionEntity> ObjectType = ProductionEntity.class;
	
	private static final File productions = FilePaths.ProductionsFolder;
	public static final String format = "PRODUCTION-%04d";
	
	public ProductionsRegistryEntity() throws IOException, IllegalClassFormatException, AppWarning {
		
		DB.fileStartup(productions, dataType);
		
		super(productions, ObjectType);
		
	}
	
	public boolean productionExists(String productionName) {
		
		return this.entityData.containsKey(productionName);
		
	}
	
	@SuppressWarnings("unused")
	public ProductionEntity getProduction(String productionName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if (!productionExists(productionName))
			throw new IllegalArgumentException("Production does not exist");
		
		return this.getEntity(this.entityData.get(productionName));
		
	}
	
	@SuppressWarnings("unused")
	public void createProduction(String productionName) throws IllegalClassFormatException, IOException, AppWarning {
	
		this.addToFile(productionName, this.getNextID(format));
		
		this.save();
	
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllProductions() {
	
		return new ArrayList<>(this.entityData.keySet());
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning {
		
		setDataType(dataType);
	
	}
	
}
