package common.util;

public enum JPA_AUTO {

	CREATE("create")
	, CREATE_DROP("create-drop")
	, UPDATE("update")
	, NONE("none")
	, VALIDATE("validate");
	
	final private String autoConfig;

	private JPA_AUTO(String autoConfig) {
		this.autoConfig = autoConfig;
	}
	
	public String getAutuConfig() {
		return this.autoConfig; 
	}
}
