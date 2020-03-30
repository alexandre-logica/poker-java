package br.com.alexandre.enuns;

public enum ActionEnum {

	CHECK("C"),
	FOLD("F"),
	BET("B");
	
	private String value;
	
	ActionEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
