package Entities.ObjectEntities;

import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;
import Core.Databases.SimpleRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.Map;

import static Core.Utilities.DataConstants.*;

public class CostumesRegistry extends SimpleRegistry {
	
	protected static final String format = "COSTUME-%04d";
	
	public static final EntityField[] ENTITY_FIELDS = {
		   NAME, HEIGHT, GIRTH, WAIST, HIPS, BUST_CHEST
		   , INSEAM, SLEEVE_LENGTH, NECK, BACK_LENGTH, SHOE_SIZE, TYPE
	};
	
	private static final File COSTUMES_REGISTRY = FilePaths.Costumes;
	
	public CostumesRegistry() throws AppWarning, IllegalClassFormatException, IOException {
	
		super(COSTUMES_REGISTRY, ENTITY_FIELDS, format);
	
	}
	
	public void addCostume( Map<EntityField, String> presetFields) throws AppWarning, IOException {
		
		this.createNewEntry(presetFields);
		
	}

}
