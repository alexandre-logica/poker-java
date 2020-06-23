package br.com.alexandre.domain.enums;

public enum BlindEnum {

	DEALER("DL"),
	SMALL("SM"),
	BIG("BG"),
	UNDER_THE_GUN("UG"),
	MIDDLE("MD"),
	CUT_OFF("CT"),
	ROUNDS("RS");
	
	private String value;
	
	BlindEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
