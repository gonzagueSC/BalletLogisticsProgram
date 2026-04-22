package Entities;

import Core.DB;
import Core.FilePaths;
import Core.RegistryEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.List;

import static Core.DataConstants.*;

public class ProductionsRegistryEntity extends RegistryEntity {
	
	public static final String dataType = JSON_TYPE;
	
	private static final Class<ProductionEntity> ObjectType = ProductionEntity.class;
	
	private static final File productions = FilePaths.ProductionsFolder;
	
	public ProductionsRegistryEntity() throws IOException, IllegalClassFormatException {
		
		DB.fileStartup(productions, dataType);
		
		super(productions, ObjectType);
		
	}
	
	public boolean productionExists(String productionName) {
		
		return this.entityData.containsKey(productionName);
		
	}
	
	public List<String> getAllProductions() {
	
		return new ArrayList<String>(this.entityData.keySet());
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(dataType);
	
	}
	
}
