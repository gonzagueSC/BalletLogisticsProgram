package Entities;

import Core.*;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.Map;

import static Core.DataConstants.*;

public class RehearsalEntity extends JSONObjectEntity {

    public static String dataType = JSON_TYPE;
    File productionFolder;

    public static final EntityField rehearsalName = new EntityField("rehearsal_name", true, String.class);
    public static final EntityField rehearsalDate = new EntityField("rehearsal_date", true, String.class);
    public static final EntityField rehearsalStartTime = new EntityField("rehearsal_start_time", true, String.class);
    public static final EntityField rehearsalEndTime = new EntityField("rehearsal_end_time", true, String.class);
    public static final EntityField rehearsalLocation = new EntityField("rehearsal_location", true, String.class);
    public static final EntityField rehearsalNotes = new EntityField("rehearsal_notes", false, String.class);
    public static final EntityField rehearsalCast = new EntityField("rehearsal_cast", true, String.class);

    public RehearsalEntity ( String rehearsalID, File folder) throws IOException, IllegalClassFormatException {

        super(rehearsalID, folder);
        this.productionFolder = folder;

    }

    public String getFullRehearsalName() {

        return this.getFieldValue(rehearsalName) + ", " + this.getFieldValue(rehearsalStartTime) + " - " + this.getFieldValue(rehearsalEndTime) + ", " + this.getFieldValue(rehearsalDate);

    }

    public void setUpRehearsal(String rehearsalNameString, String rehearsalDateString, String rehearsalStartTimeString, String rehearsalEndTimeString) throws IOException, IllegalClassFormatException {
        Map<EntityField, String> fields = new HashMap<>();

        fields.put(rehearsalName, rehearsalNameString);
        fields.put(rehearsalDate, rehearsalDateString);
        fields.put(rehearsalStartTime, rehearsalStartTimeString);
        fields.put(rehearsalEndTime, rehearsalEndTimeString);

        this.saveFields(fields);

    }

    public void setRehearsalName(String rehearsalNameString) throws IOException {
        this.saveField(rehearsalName, rehearsalNameString);
    }

    public void setRehearsalDate(String rehearsalDateString) throws IOException {
        this.saveField(rehearsalDate, rehearsalDateString);
    }

    public void setRehearsalStartTime(String rehearsalStartTimeString) throws IOException {
        this.saveField(rehearsalStartTime, rehearsalStartTimeString);
    }

    public void setRehearsalEndTime(String rehearsalEndTimeString) throws IOException {
        this.saveField(rehearsalEndTime, rehearsalEndTimeString);
    }
    
    @SuppressWarnings("unused")
    public void setRehearsalLocation(String rehearsalLocationString) throws IOException {
        this.saveField(rehearsalLocation, rehearsalLocationString);
    }
    
    @SuppressWarnings("unused")
    public void setRehearsalNotes(String rehearsalNotesString) throws IOException {
        this.saveField(rehearsalNotes, rehearsalNotesString);
    }

    public void addRehearsalCast(String castToAdd) throws IOException {

        String currentRoles = this.getFieldValue(rehearsalCast);

        if (currentRoles == null) {

            currentRoles = "";

        }

        if (currentRoles.contains(castToAdd))
            throw new AppError(new AppWarning("Tried to add the same role twice"));

        currentRoles += ((!currentRoles.isBlank()) ? ", " : "") + castToAdd;

        this.saveField(rehearsalCast, currentRoles);

    }

    public void removeRehearsalCast(String castToRemove) throws IOException {

        String currentRoles = this.getFieldValue(rehearsalCast);

        if (!currentRoles.contains(castToRemove))
            throw new AppError(new AppWarning("Tried to un-cast a non-casted student"));

        String regex = "(, " + castToRemove + "|" + castToRemove + ", |" + castToRemove + ")";

        currentRoles = currentRoles.replaceFirst(regex, "");

        this.saveField(rehearsalCast, (currentRoles.isBlank()) ? null : currentRoles);

    }

    @Override
    protected void initializeComponents() throws IllegalClassFormatException, IOException {

        setDataType(dataType);

    }
    
    public void removeRole(String roleToRemove) throws IOException {
        
        removeRehearsalCast(roleToRemove);
        
    }
    
    public void editRole(String oldRole, String newRole) throws IOException {
        
        removeRehearsalCast(oldRole);
        addRehearsalCast(newRole);
        
    }

}
