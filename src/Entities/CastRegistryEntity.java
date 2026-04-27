package Entities;

import Core.RegistryEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Core.DataConstants.*;

public class CastRegistryEntity extends RegistryEntity {

    public static final String FileType = JSON_TYPE;
    public static final String RegistryName = "Casts";
    public static final String format = "CAST-%04d";

    public CastRegistryEntity(File Production) throws IllegalClassFormatException, IOException {

        File Registry = new File(Production, RegistryName);

        super(Registry, CastEntity.class);

    }

    public CastEntity getCast(String castName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (!this.entityData.containsKey(castName)) {
            throw new IllegalArgumentException("Cast not found");
        }

        return this.getEntity(castName, true);

    }
    
    public String getNextCastID () {
        
        return this.getNextID(format);
        
    }
    
    @SuppressWarnings("unused")
    public void addCast(String castName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        this.getEntity(castName, true);
        
        this.entityData.put(castName, getNextCastID());

    }

    public void removeCast(String castName) throws IOException {

        this.removeEntity(castName);

    }
    
    @SuppressWarnings("unused")
    public boolean castExists(String castName) {

        return this.entityData.containsKey(castName);

    }

    public List<String> getAllCasts() {
	    
	    return new ArrayList<>(this.entityData.keySet());

    }
    
    public void changeRoleName(String oldRole, String newRole) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    
        List<String> casts = this.getAllCasts();
        
        for (String cast : casts) {
            CastEntity castEntity = this.getEntity(cast, true);
            castEntity.changeRoleName(oldRole, newRole);
        }
    
    }
    
    public void removeRole(String role) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        
        List<String> casts = this.getAllCasts();
        
        for (String cast : casts) {
            CastEntity castEntity = this.getEntity(cast, true);
            castEntity.removeRole(role);
        }
        
    }

    @Override
    protected void initializeComponents() throws IllegalClassFormatException, IOException {

        setDataType(FileType);

    }

}
