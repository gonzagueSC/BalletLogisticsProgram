package Core.Utilities;

public class Action {
	
	private final String action;
	private final String subAction;
	
	public Action(String action, String subAction) {
		
		this.action = action;
		this.subAction = subAction;
		
	}
	
	public String getAction() {
		
		return this.action;
		
	}
	
	public String getSubAction() {
		
		return this.subAction;
		
	}
	
}
