package databaseAccess;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

import util.DataPoint;
import util.Globals;

import java.util.*;

import control.Main;
import control.PromptsService;
import databaseConstants.*;
import swingConstants.ViewConstants;

public class StudentsModule {

    /**
     * Adds a student to the system with as 8 data values: First Name, Last Name,
     * Level, Email, Phone Number, Medical Conditions, Date Of Birth, Gender
     *
     * @param strings
     */

    public static void addStudent(String... strings) {

        String studentName = strings[0] + " " + strings[1];
        String birthDate = strings[6];

        String info = "";

        for (int i = 0; i < databaseConstants.DatabaseUtilities.StudentInfo.length; i++) {

            if (i < 8) {

                info += strings[i] + "\n";

            } else if (i == 8) {

                info += LocalDate.now().toString() + "\n";

            } else if (i == 10) {

                info += "Active\n";

            } else if (i == 12) {

                info += "0\n";

            } else {

                info += "-1\n";

            }

        }

        String ID = Long.toString(System.currentTimeMillis());

        if (StudentsModule.checkForDuplicateStudent(databaseConstants.Databases.RegisteredStudents, studentName,
                birthDate)) {

            PromptsService.FailurePrompt("Student is already in the database");

        } else {

            try {

                // WRITING HERE WRITING HERE WRITING HERE WRITING HERE WRITING HERE WRITING HERE
                // WRITING HERE

                DatabaseCore.writeToDatabase(databaseConstants.Databases.RegisteredStudents,
                        studentName + " " + birthDate + " " + ID);
                File newFile = DatabaseCore.CreateFile(DatabaseFolders.StudentsFolder, ID);
                int completion = DatabaseCore.writeToDatabase(newFile, info);
                Main.Router.showView(ViewConstants.ADMIN_VIEW);
                PromptsService.SuccessPrompt("Student Added to Repertory");

                // WRITING HERE WRITING HERE WRITING HERE WRITING HERE WRITING HERE WRITING HERE
                // WRITING HERE

                return;

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        }

    }

    public static String[] getStudentInfo(String name, String birthDate) {

        // RETURN THE ARRAY OF STUDENT DETAILS

        return getStudentInfo(StudentsModule.getStudentID(name, birthDate));

    }

    public static String[] getStudentInfo(String ID) {

        String[] studentDetails = new String[DatabaseUtilities.StudentInfo.length];

        // CREATE A FILE OBJECT FOR THE STUDENT'S INFO FILE

        File readFile = new File(DatabaseFolders.StudentsFolder, ID);

        // READ LINES 0 TO 22 (TOTAL 23 LINES) FROM THE STUDENT'S FILE

        try {

            studentDetails = DatabaseCore.returnFileExcerpt(readFile, 0, studentDetails.length);

        } catch (Exception e) {

            e.printStackTrace();

        }

        // RETURN THE ARRAY OF STUDENT DETAILS

        return studentDetails;

    }

    public static String getStudentDetail(String name, String birthDate, String detail) {

        int index = switch (detail) {

            case DatabaseUtilities.FIRST_NAME -> 0;
            case DatabaseUtilities.LAST_NAME -> 1;
            case DatabaseUtilities.LEVEL -> 2;
            case DatabaseUtilities.EMAIL -> 3;
            case DatabaseUtilities.PHONE_NUMBER -> 4;
            case DatabaseUtilities.MEDICAL_CONDITIONS -> 5;
            case DatabaseUtilities.DATE_OF_BIRTH -> 6;
            case DatabaseUtilities.GENDER -> 7;
            case DatabaseUtilities.DATE_JOINED -> 8;
            case DatabaseUtilities.ADDRESS -> 9;
            case DatabaseUtilities.ACTIVITY_STATUS -> 10;
            case DatabaseUtilities.TUITION_PLAN -> 11;
            case DatabaseUtilities.ACCOUNT_BALANCE -> 12;
            case DatabaseUtilities.HEIGHT -> 13;
            case DatabaseUtilities.GIRTH -> 14;
            case DatabaseUtilities.WAIST -> 15;
            case DatabaseUtilities.HIPS -> 16;
            case DatabaseUtilities.BUSTCHEST -> 17;
            case DatabaseUtilities.INSEAM -> 18;
            case DatabaseUtilities.SLEEVE_LENGTH -> 19;
            case DatabaseUtilities.NECK -> 20;
            case DatabaseUtilities.BACK_LENGTH -> 21;
            case DatabaseUtilities.SHOE_SIZE -> 22;
            default -> throw new IllegalArgumentException("Unexpected value: " + detail);

        };

        return getStudentInfo(name, birthDate)[index];

    }

    public static String getStudentDetail(String ID, String detail) {

        int index = switch (detail) {

            case DatabaseUtilities.FIRST_NAME -> 0;
            case DatabaseUtilities.LAST_NAME -> 1;
            case DatabaseUtilities.LEVEL -> 2;
            case DatabaseUtilities.EMAIL -> 3;
            case DatabaseUtilities.PHONE_NUMBER -> 4;
            case DatabaseUtilities.MEDICAL_CONDITIONS -> 5;
            case DatabaseUtilities.DATE_OF_BIRTH -> 6;
            case DatabaseUtilities.GENDER -> 7;
            case DatabaseUtilities.DATE_JOINED -> 8;
            case DatabaseUtilities.ADDRESS -> 9;
            case DatabaseUtilities.ACTIVITY_STATUS -> 10;
            case DatabaseUtilities.TUITION_PLAN -> 11;
            case DatabaseUtilities.ACCOUNT_BALANCE -> 12;
            case DatabaseUtilities.HEIGHT -> 13;
            case DatabaseUtilities.GIRTH -> 14;
            case DatabaseUtilities.WAIST -> 15;
            case DatabaseUtilities.HIPS -> 16;
            case DatabaseUtilities.BUSTCHEST -> 17;
            case DatabaseUtilities.INSEAM -> 18;
            case DatabaseUtilities.SLEEVE_LENGTH -> 19;
            case DatabaseUtilities.NECK -> 20;
            case DatabaseUtilities.BACK_LENGTH -> 21;
            case DatabaseUtilities.SHOE_SIZE -> 22;
            default -> throw new IllegalArgumentException("Unexpected value: " + detail);

        };

        return getStudentInfo(ID)[index];

    }

    public static String[] getAllStudentNames(String[] filters) {

        // ARRAYLIST TO STORE MATCHING NAMES

        ArrayList<String> names = new ArrayList<String>();

        try {

            // OPEN THE REGISTERED STUDENTS FILE

            BufferedReader reader = new BufferedReader(new FileReader(Databases.RegisteredStudents));
            String nextLine;

            // ITERATE THROUGH ALL REGISTERED STUDENTS

            while ((nextLine = reader.readLine()) != null) {

                // EXTRACT THE STUDENT ID FILENAME

                File individualFile = new File(DatabaseFolders.StudentsFolder, nextLine.split(" ")[3]);

                // CHECK IF THE INDIVIDUAL STUDENT FILE MATCHES THE FILTERS

                if (Organizer.runByFilters(individualFile, filters))

                    // IF IT MATCHES, ADD THE FULL LINE (NAME AND ID INFO) TO THE NAMES LIST

                    names.add(nextLine.strip());

            }

            // IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        // CONVERT THE ARRAYLIST TO AN ARRAY

        String[] result = names.toArray(new String[0]);

        // SORT THE RESULT ARRAY BASED ON THE GIVEN FILTERS

        return Organizer.sortByFilter(result, filters);

    }

    public static String[] getAllStudentNames(String start) {

        // ARRAYLIST TO STORE MATCHING NAMES

        ArrayList<String> names = new ArrayList<String>();

        try {

            // OPEN THE REGISTERED STUDENTS FILE

            BufferedReader reader = new BufferedReader(new FileReader(Databases.RegisteredStudents));
            String nextLine;

            // ITERATE THROUGH ALL REGISTERED STUDENTS

            while ((nextLine = reader.readLine()) != null) {

                // EXTRACT THE STUDENT ID FILENAME

                File individualFile = new File(DatabaseFolders.StudentsFolder, nextLine.split(" ")[3]);

                // CHECK IF THE STUDENT FILE MATCHES THE 'start' FILTER

                boolean check = Organizer.runByFilters(individualFile, start);

                // IF IT MATCHES, ADD THE FULL LINE (NAME AND ID INFO) TO THE NAMES LIST

                if (check)
                    names.add(nextLine.strip());

            }

            // IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        // CONVERT THE ARRAYLIST TO AN ARRAY AND RETURN IT

        String[] result = names.toArray(new String[0]);

        return result;

    }

    public static String getStudentID(String name, String birthDate) {

        try {

            // OPEN THE REGISTERED STUDENTS FILE

            BufferedReader reader = new BufferedReader(new FileReader(Databases.RegisteredStudents));
            String nextLine;

            // ITERATE THROUGH ALL REGISTERED STUDENTS

            while ((nextLine = reader.readLine()) != null) {

                // CHECK IF THE NAME AND BIRTHDATE IN THE LINE MATCH THE GIVEN ARGUMENTS

                if ((nextLine.split(" ")[0] + " " + nextLine.split(" ")[1]).equals(name)
                        && birthDate.equals(nextLine.split(" ")[2])) {

                    // IF MATCH FOUND, CLOSE READER AND RETURN THE STUDENT ID (INDEX 3)

                    reader.close();
                    return nextLine.split(" ")[3];

                }

            }

            // CLOSE READER IF LOOP FINISHES WITHOUT A MATCH

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        // RETURN "-1" IF STUDENT WAS NOT FOUND

        return "-1";

    }

    public static String[] getAllStudentsByRole(String productionName, String role) {

        // GET OR CREATE THE PRODUCTION FOLDER

        File ProductionFolder = DatabaseCore.CreateFolder(DatabaseFolders.ProductionsFolder, productionName);

        // GET ALL CASTS FOR THE PRODUCTION

        String[] allCasts = ProductionsModule.getAllCastsByProduction(productionName);

        // HASHSET TO STORE UNIQUE STUDENT IDs

        HashSet<String> students = new HashSet<String>();

        // ITERATE THROUGH EACH CAST

        for (int i = 0; i < allCasts.length; i++) {

            // GET THE CAST FOLDER AND THE SPECIFIC ROLE FILE WITHIN IT

            File CastFolder = DatabaseCore.CreateFolder(ProductionFolder, allCasts[i]);
            File RoleFile = new File(CastFolder, role);

            // CHECK IF THE ROLE FILE EXISTS FOR THIS CAST

            if (RoleFile.exists()) {

                try {

                    // OPEN THE ROLE FILE

                    BufferedReader rolesReader = new BufferedReader(new FileReader(RoleFile));

                    String nextLine;

                    // READ ALL STUDENT IDs FROM THE ROLE FILE

                    while ((nextLine = rolesReader.readLine()) != null) {

                        students.add(nextLine);

                    }

                    // IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK

                    rolesReader.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        // RETURN THE UNIQUE STUDENT IDs AS AN ARRAY

        return students.toArray(new String[0]);

    }

    public static String getStudentDisplayByID(String ID) {

        try {

            // OPEN THE REGISTERED STUDENTS FILE

            BufferedReader reader = new BufferedReader(new FileReader(Databases.RegisteredStudents));
            String nextLine;

            // ITERATE THROUGH ALL REGISTERED STUDENTS

            while ((nextLine = reader.readLine()) != null) {

                String[] line = nextLine.split(" ");

                // CHECK IF THE ID (INDEX 3) MATCHES THE GIVEN ID

                if (line[3].equals(ID)) {

                    // IF MATCH FOUND, CLOSE READER AND RETURN THE FIRST AND LAST NAME

                    reader.close();
                    return line[0] + " " + line[1];

                }

            }

            // IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK IF NO MATCH FOUND

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        // RETURN "404: Not Found" IF STUDENT ID WAS NOT FOUND

        return "404: Not Found";

    }

    public static String[] getCastsByStudentRole(String productionName, String studentID, String role) {

        // GET THE PRODUCTION FOLDER

        File ProductionFolder = DatabaseCore.CreateFolder(DatabaseFolders.ProductionsFolder, productionName);

        // GET ALL CASTS FOR THE PRODUCTION

        String[] allCasts = ProductionsModule.getAllCastsByProduction(productionName);

        // HASHSET TO STORE MATCHING CAST NAMES

        HashSet<String> casts = new HashSet<String>();

        // ITERATE THROUGH EACH CAST

        for (int i = 0; i < allCasts.length; i++) {

            // GET THE CAST FOLDER AND THE SPECIFIC ROLE FILE WITHIN IT

            File CastFolder = new File(ProductionFolder, allCasts[i]);
            File RoleFile = new File(CastFolder, role);

            // CHECK IF THE STUDENT ID EXISTS IN THE ROLE FILE FOR THIS CAST

            if (DatabaseCore.checkForSameLine(RoleFile, studentID)) {

                // IF FOUND, ADD THE CAST NAME

                casts.add(allCasts[i]);

            }

        }

        // RETURN THE MATCHING CAST NAMES AS AN ARRAY

        return casts.toArray(new String[0]);

    }

    public static void UpdateStudentRoles(String studentID, String productionName, String role, String[] casts) {

        // IT IS EASIER, GIVEN THE DATABASE LOGIC TO DELETE THEM FROM THEIR CASTS

        StudentsModule.removeStudentFromRole(studentID, productionName, role, casts);

        // THEN ADD THEM BACK TO WHATEVER CASTS THEY ARE ASSIGNED TO

        StudentsModule.addStudentToRole(studentID, productionName, role, casts);

        try {

            File studentFile = new File(DatabaseFolders.StudentsFolder, studentID);
            BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

            studentFixer.write(String.format(StudentLogFormats.CastingFormat, LocalDate.now().toString(), role,
                    Arrays.toString(casts)) + "\n");
            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentFixer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void fixStudentName(String name, String birthDate, String ID, String newVal) {

        // CATCH ANY EXCEPTION

        try {

            // GET THE FILE WHERE ALL THE STUDENTS ARE STORED (MAKE A SCANNER OF IT AS WELL)

            File studentsFile = Databases.RegisteredStudents;
            Scanner studentsReader = new Scanner(studentsFile);

            // WRITE THE ENTIRE FILE TO A TEMPORARY FILE

            String fullNamesFile = "";

            while (studentsReader.hasNext()) {

                fullNamesFile += studentsReader.nextLine() + "\n";

            }

            studentsReader.close();

            // WITH THE TEMPORARY FILE, RE-WRITE THE FILE

            studentsReader = new Scanner(fullNamesFile);
            BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(studentsFile));

            String nextLine;

            while (studentsReader.hasNext()) {

                nextLine = studentsReader.nextLine();

                // IF WE ARE AT THE ENTRY OF THE STUDENT WE ARE TRYING TO CHANGE, CHANGE,
                // OTHERWISE LEAVE THE LINE AS IS.

                if (nextLine.strip().equals(name + " " + birthDate + " " + ID)) {

                    studentsFixer.write(newVal + " " + ID + "\n");

                } else {

                    studentsFixer.write(nextLine + "\n");

                }

            }

            // CLOSE READERS TO AVOID RESOURCE LEAK WHICH CAN SLOW THE PROGRAM

            studentsFixer.close();
            studentsReader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void writeToFile(String name, String birthDate, String[] info) {

        try {

            // log all of the changes to the student historical log

            changeStudentFirstName(name, birthDate, info[0]);
            changeStudentLastName(name, birthDate, info[1]);
            changeStudentLevel(name, birthDate, info[2]);
            changeStudentEmail(name, birthDate, info[3]);
            changeStudentPhoneNumber(name, birthDate, info[4]);
            changeStudentMedical(name, birthDate, info[5]);
            changeStudentDateOfBirth(name, birthDate, info[6]);
            changeStudentGender(name, birthDate, info[7]);
            changeStudentDateJoined(name, birthDate, info[8]);
            changeStudentAddress(name, birthDate, info[9]);
            changeStudentStatus(name, birthDate, info[10]);
            changeStudentTuitionPlan(name, birthDate, info[11]);
            changeStudentAccountBalance(name, birthDate, info[12]);
            changeStudentMeasurement(name, birthDate, "Height", info[13]);
            changeStudentMeasurement(name, birthDate, "Girth", info[14]);
            changeStudentMeasurement(name, birthDate, "Waist", info[15]);
            changeStudentMeasurement(name, birthDate, "Hips", info[16]);
            changeStudentMeasurement(name, birthDate, "Bust/Chest", info[17]);
            changeStudentMeasurement(name, birthDate, "Inseam", info[18]);
            changeStudentMeasurement(name, birthDate, "Sleeve Length", info[19]);
            changeStudentMeasurement(name, birthDate, "Neck", info[20]);
            changeStudentMeasurement(name, birthDate, "Back Length", info[21]);
            changeStudentMeasurement(name, birthDate, "Shoe Size", info[22]);

            File studentFile = new File(DatabaseFolders.StudentsFolder, StudentsModule.getStudentID(name, birthDate));

            Scanner studentsReader = new Scanner(studentFile);

            String fullNamesFile = "";

            while (studentsReader.hasNext()) {

                fullNamesFile += studentsReader.nextLine() + "\n";

            }

            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentsReader.close();

            studentsReader = new Scanner(fullNamesFile);

            String[] studentInfo = StudentsModule.getStudentInfo(name, birthDate);
            BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile));

            for (int i = 0; i < info.length; i++) {

                studentsReader.nextLine();

                // if first name changes, update the registered students file entry

                if (i == 0) {

                    if (!info[i].equals(studentInfo[0])) {

                        StudentsModule.fixStudentName(name, birthDate, StudentsModule.getStudentID(name, birthDate),
                                info[0] + " " + info[1] + " " + birthDate);

                    }

                    // if last name changes, update the registered students file entry

                } else if (i == 1) {

                    if (!info[i].equals(studentInfo[1])) {

                        StudentsModule.fixStudentName(name, birthDate, StudentsModule.getStudentID(name, birthDate),
                                info[0] + " " + info[1] + " " + birthDate);

                    }

                    // if birth date changes, update the registered students file entry

                } else if (i == 6) {

                    if (!info[i].equals(studentInfo[6])) {

                        StudentsModule.fixStudentName(name, birthDate, StudentsModule.getStudentID(name, birthDate),
                                info[0] + " " + info[1] + " " + info[6]);

                    }

                }

                studentFixer.write(info[i] + "\n");

            }

            while (studentsReader.hasNextLine()) {

                studentFixer.write(studentsReader.nextLine() + "\n");

            }

            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentsReader.close();

            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentFixer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's medical information in their file if the value
     * has changed.
     *
     * @param name       The student's full name.
     * @param birthDate  The student's birth date.
     * @param newMedical The new medical information value.
     */
    public static void changeStudentMedical(String name, String birthDate, String newMedical) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 5, newMedical)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(
                        String.format(StudentLogFormats.ChangeMedicalConditionsFormat, LocalDate.now(), newMedical)
                                + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's first name in their file if the value has
     * changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newName   The new first name value.
     */
    public static void changeStudentFirstName(String name, String birthDate, String newName) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 0, newName)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeFirstNameFormat, LocalDate.now(), newName) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's last name in their file if the value has
     * changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newName   The new last name value.
     */
    public static void changeStudentLastName(String name, String birthDate, String newName) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 1, newName)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeLastNameFormat, LocalDate.now(), newName) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's email in their file if the value has changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newEmail  The new email value.
     */
    public static void changeStudentEmail(String name, String birthDate, String newEmail) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 3, newEmail)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeEmailFormat, LocalDate.now(), newEmail) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's phone number in their file if the value has
     * changed.
     *
     * @param name           The student's full name.
     * @param birthDate      The student's birth date.
     * @param newPhoneNumber The new phone number value.
     */
    public static void changeStudentPhoneNumber(String name, String birthDate, String newPhoneNumber) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 4, newPhoneNumber)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(
                        String.format(StudentLogFormats.ChangeEmailFormat, LocalDate.now(), newPhoneNumber) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's date of birth in their file if the value has
     * changed.
     *
     * @param name           The student's full name.
     * @param birthDate      The student's birth date.
     * @param newDateOfBirth The new date of birth value.
     */
    public static void changeStudentDateOfBirth(String name, String birthDate, String newDateOfBirth) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 6, newDateOfBirth)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeDateOfBirthFormat, LocalDate.now(), newDateOfBirth)
                                + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's gender in their file if the value has changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newGender The new gender value.
     */
    public static void changeStudentGender(String name, String birthDate, String newGender) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 7, newGender)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeGenderFormat, LocalDate.now(), newGender) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's address in their file if the value has changed.
     *
     * @param name       The student's full name.
     * @param birthDate  The student's birth date.
     * @param newAddress The new address value.
     */
    public static void changeStudentAddress(String name, String birthDate, String newAddress) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 9, newAddress)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(
                        String.format(StudentLogFormats.ChangeAddressFormat, LocalDate.now(), newAddress) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's costume measurement in their file if the value
     * has changed.
     *
     * @param name            The student's full name.
     * @param birthDate       The student's birth date.
     * @param measurementType The specific measurement (e.g., "Height", "Waist").
     * @param newMeasurement  The new measurement value.
     */
    public static void changeStudentMeasurement(String name, String birthDate, String measurementType,
                                                String newMeasurement) {

        try {

            // determine the field index based on measurement type

            int change = switch (measurementType) {

                case "Height" -> 13;
                case "Girth" -> 14;
                case "Waist" -> 15;
                case "Hips" -> 16;
                case "Bust/Chest" -> 17;
                case "Inseam" -> 18;
                case "Sleeve Length" -> 19;
                case "Neck" -> 20;
                case "Back Length" -> 21;
                case "Shoe Size" -> 22;
                default -> throw new IllegalArgumentException("Unexpected value: " + measurementType);

            };

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, change, newMeasurement)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(String.format(StudentLogFormats.ChangeCostumeMeasurementFormat, LocalDate.now(),
                        measurementType, newMeasurement) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's join date in their file if the value has
     * changed.
     *
     * @param name          The student's full name.
     * @param birthDate     The student's birth date.
     * @param newDateJoined The new date joined value.
     */
    public static void changeStudentDateJoined(String name, String birthDate, String newDateJoined) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 8, newDateJoined)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(
                        String.format(StudentLogFormats.ChangeAddressFormat, LocalDate.now(), newDateJoined) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's activity status in their file if the value has
     * changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newStatus The new status value.
     */
    public static void changeStudentStatus(String name, String birthDate, String newStatus) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 10, newStatus)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(
                        String.format(StudentLogFormats.ChangeActivityStatusFormat, LocalDate.now(), newStatus) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's tuition plan in their file if the value has
     * changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newPlan   The new tuition plan value.
     */
    public static void changeStudentTuitionPlan(String name, String birthDate, String newPlan) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 11, newPlan)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer.write(
                        String.format(StudentLogFormats.ChangeTuitionPlanFormat, LocalDate.now(), newPlan) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's account balance in their file if the value has
     * changed.
     *
     * @param name       The student's full name.
     * @param birthDate  The student's birth date.
     * @param newBalance The new account balance value.
     */
    public static void changeStudentAccountBalance(String name, String birthDate, String newBalance) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 12, newBalance)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeAccountBalanceFormat, LocalDate.now(), newBalance)
                                + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Logs a change to a student's level in their file if the value has changed.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param newLevel  The new level value.
     */
    public static void changeStudentLevel(String name, String birthDate, String newLevel) {

        try {

            // check if the value has actually changed

            if (StudentsModule.changed(name, birthDate, 2, newLevel)) {

                File studentFile = new File(DatabaseFolders.StudentsFolder,
                        StudentsModule.getStudentID(name.trim(), birthDate));
                BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

                studentFixer
                        .write(String.format(StudentLogFormats.ChangeLevelFormat, LocalDate.now(), newLevel) + "\n");

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentFixer.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Appends a custom log entry to a student's historical data file.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param logData   The custom string content to add to the log.
     */
    public static void addToStudentLog(String name, String birthDate, String logData) {

        try {

            File studentFile = new File(DatabaseFolders.StudentsFolder,
                    StudentsModule.getStudentID(name.trim(), birthDate));
            BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

            studentFixer.write(logData + "\n");

            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentFixer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Adds a performance casting detail to a student's historical log.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param role      The role the student was cast in.
     * @param Casting   The name of the production/casting event.
     */
    public static void addStudentCasting(String name, String birthDate, String role, String Casting) {

        try {

            File studentFile = new File(DatabaseFolders.StudentsFolder,
                    StudentsModule.getStudentID(name.trim(), birthDate));
            BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

            studentFixer.write(String.format(StudentLogFormats.CastingFormat, LocalDate.now(), role, Casting) + "\n");

            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentFixer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Adds a teacher's note to a student's historical log.
     *
     * @param name      The student's full name.
     * @param birthDate The student's birth date.
     * @param Teacher   The name of the teacher submitting the note.
     * @param Note      The content of the note.
     */
    public static void addTeachersNote(String name, String birthDate, String Teacher, String Note) {

        try {

            File studentFile = new File(DatabaseFolders.StudentsFolder,
                    StudentsModule.getStudentID(name.trim(), birthDate));
            BufferedWriter studentFixer = new BufferedWriter(new FileWriter(studentFile, true));

            studentFixer.write(String.format(StudentLogFormats.TeachersNoteFormat, LocalDate.now(), Note, Teacher) + "\n");

            // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

            studentFixer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Adds a student's ID to the specified role file for one or more casts within a
     * production.
     *
     * @param studentID      The ID of the student to add.
     * @param productionName The name of the production.
     * @param role           The specific role to assign the student to.
     * @param casts          An array of cast names (e.g., "Cast A", "Cast B").
     */
    public static void addStudentToRole(String studentID, String productionName, String role, String[] casts) {

        File ProductionFolder = DatabaseCore.CreateFolder(DatabaseFolders.ProductionsFolder, productionName);

        for (int i = 0; i < casts.length; i++) {

            File CastFolder = DatabaseCore.CreateFolder(ProductionFolder, casts[i]);
            File RoleFile = DatabaseCore.CreateFile(CastFolder, role);

            File CostumesFile = DatabaseCore.CreateFile(ProductionFolder, "Costumes");

            DatabaseCore.writeToDatabase(RoleFile, studentID);

            DatabaseCore.writeToDatabase(CostumesFile, studentID + ", Not assigned");

        }

    }

    /**
     * Removes a student's ID from the specified role file for one or more casts
     * within a production.
     *
     * @param studentID      The ID of the student to remove.
     * @param productionName The name of the production.
     * @param role           The specific role to remove the student from.
     * @param casts          An array of cast names (e.g., "Cast A", "Cast B").
     */
    public static void removeStudentFromRole(String studentID, String productionName, String role, String[] casts) {

        File ProductionFolder = DatabaseCore.CreateFolder(DatabaseFolders.ProductionsFolder, productionName);

        for (int i = 0; i < casts.length; i++) {

            File CastFolder = new File(ProductionFolder, casts[i]);
            File RoleFile = new File(CastFolder, role);

            try {

                // read the entire current file content
                Scanner RoleReader = new Scanner(RoleFile);

                String fullNamesFile = "";

                while (RoleReader.hasNext()) {

                    fullNamesFile += RoleReader.nextLine() + "\n";

                }

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                RoleReader.close();

                // prepare to re-write the file
                RoleReader = new Scanner(fullNamesFile);
                BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(RoleFile));

                String nextLine;

                // re-write all lines except the one matching the student ID

                while (RoleReader.hasNext()) {

                    nextLine = RoleReader.nextLine();

                    if (!nextLine.strip().equals(studentID)) {

                        studentsFixer.write(nextLine + "\n");

                    }

                }

                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK

                studentsFixer.close();
                // IMPORTANT LINE: AVOIDS A RESOURCE LEAK
                RoleReader.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }

    public static boolean checkForDuplicateStudent(File database, String studentName, String studentBirthDate) {

        String regex = " ";

        // split the full name into first and last name
        String studentFirstName = studentName.split(regex)[0];
        String studentLastName = studentName.split(regex)[1];

        try {

            // use DatabaseReader to check for a line matching First Name, Last Name, and
            // Birth Date
            return DatabaseCore.checkForLine(database, regex, studentFirstName, studentLastName, studentBirthDate);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    public static boolean changed(String name, String birthDate, int info, String newVal) {

        // get the current student information
        String[] studentInfo = StudentsModule.getStudentInfo(name, birthDate);

        // return false (no change) if:
        // 1. the stored value already equals the new value, OR
        // 2. the new value is blank AND the stored value is the default placeholder
        // ("-1")
        if (studentInfo[info].equals(newVal) || newVal.isBlank() && studentInfo[info].equals("-1"))
            return false;
        return true;

    }

    public static String[] getUpcomingClassesToday(String ID) {

        String studentLevel = StudentsModule.getStudentDetail(ID, DatabaseUtilities.LEVEL);

        return SchedulesModule.getAllUpcomingClassesBelowLevelToday(studentLevel);

    }

    public static File getStudentFile(String ID) {

        return new File(DatabaseFolders.StudentsFolder, ID);

    }

    public static String[] getAllRecords(String studentID, String DataType) throws Exception {

        File database = StudentsModule.getStudentFile(studentID);
        String[] allFile = DatabaseCore.returnAllFile(database);

        ArrayList<String> fullFile = new ArrayList<String>();

        for (String record : allFile) {

            if (record.matches(DataType + ".*")) {

                fullFile.add(record);

            }

        }

        return fullFile.toArray(new String[0]);

    }

    public static String[] getAllRecords(String studentID) throws Exception {

        File database = StudentsModule.getStudentFile(studentID);
        String[] allFile = DatabaseCore.returnFileExcerpt(database, DatabaseUtilities.StudentInfo.length);

        ArrayList<String> fullFile = new ArrayList<String>();

        for (String record : allFile) {

            fullFile.add(record);

        }

        return fullFile.toArray(new String[0]);

    }

    public static String[] returnAllProfile(String studentID) throws Exception {

        String[] ProfileData = databaseAccess.StudentsModule.getAllRecords(studentID, Globals.PROFILEDATA);
        String[] AdminNotes = databaseAccess.StudentsModule.getAllRecords(studentID, Globals.ADMINNOTE);

        List<String> returnArray = new ArrayList<>(Arrays.asList(ProfileData));
        returnArray.addAll(Arrays.asList(AdminNotes));

        return returnArray.toArray(new String[0]);

    }

    public static String[] returnAllProduction(String studentID) throws Exception {

        return databaseAccess.StudentsModule.getAllRecords(studentID, Globals.CASTING);

    }

    public static String[] returnAllAdmin(String studentID) throws Exception {

        String[] LevelChanges = databaseAccess.StudentsModule.getAllRecords(studentID, Globals.LEVELCHANGE);
        String[] FinancialData = databaseAccess.StudentsModule.getAllRecords(studentID, Globals.FINANCIAL);

        List<String> returnArray = new ArrayList<>(Arrays.asList(LevelChanges));
        returnArray.addAll(Arrays.asList(FinancialData));

        return returnArray.toArray(new String[0]);

    }

}
