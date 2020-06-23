package br.com.alexandre.domain.enums;

public enum ActionEnum {

	CHECK("c"),
	FOLD("f"),
	BET("b");
	
	private String value;
	
	ActionEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static ActionEnum getActionEnumFromValue(String value) {
		switch (value) {
		case "c":
			return ActionEnum.CHECK;
		case "b":
			return ActionEnum.BET;
		case "f":
			return ActionEnum.FOLD;
		default:
			return null;
		}
	}

}
