package br.com.alexandre.enuns;

public enum StatusEnum {
	IN("I"),
	OUT("O"),
	CARD_WINNER("CW"),
	BETING_WINNER("BW");
	
	private String value;
	
	StatusEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
