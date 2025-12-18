package databaseConstants;


public class StudentLogFormats {
	
	public static final String PROFILEDATA = "PROFILEDATA";
	public static final String ATTENDANCE = "ATTENDANCE";
	public static final String LEVELCHANGE = "LEVELCHANGE";
	public static final String CASTING = "CASTING";
	public static final String FINANCIAL = "FINANCIAL";
	public static final String ADMINNOTE = "ADMINNOTE";

	public static final String ChangeMedicalConditionsFormat = PROFILEDATA + " %s: Medical Conditions Changed to %s";
	public static final String ChangeFirstNameFormat = PROFILEDATA + " %s: First Name Modified to %s";
	public static final String ChangeLastNameFormat = PROFILEDATA + " %s: Last Name Modified to %s";
	public static final String ChangeEmailFormat = PROFILEDATA + " %s: Email Changed to %s";
	public static final String ChangeDateOfBirthFormat = PROFILEDATA + " %s: Date of Birth Modified to %s";
	public static final String ChangePhoneNumberFormat = PROFILEDATA + " %s: Phone number changed to %s";
	public static final String ChangeGenderFormat = PROFILEDATA + " %s: Gender Modified to %s";
	public static final String ChangeAddressFormat = PROFILEDATA + " %s: Address Changed to %s";
	public static final String ChangeCostumeMeasurementFormat = PROFILEDATA + " %s: %s Updated to %s";
	public static final String ChangeDateJoinedFormat = PROFILEDATA + " %s: Date Joined Modified to %s";
	public static final String ChangeActivityStatusFormat = PROFILEDATA + " %s: Status Changed to %s";
	public static final String ChangeTuitionPlanFormat = FINANCIAL + " %s: Tuition Plan Changed to %s";
	public static final String ChangeAccountBalanceFormat = FINANCIAL + " %s: Account Balance Changed to %s";
	public static final String ChangeLevelFormat = LEVELCHANGE + " %s: Moved to %s";
	public static final String CastingFormat = CASTING + " %s: Casted as %s in casts: %s";
	public static final String AttendanceEnteredFormat = ATTENDANCE + " %s: %s";
	public static final String AttendanceExitFormat = ATTENDANCE + " %s: %s %s";
	public static final String TeachersNoteFormat = ADMINNOTE + " %s: %s - %s";

}
