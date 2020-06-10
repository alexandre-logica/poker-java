package br.com.alexandre.enuns;

public enum BlindEnum {

	SMALL("SM"),
	SMALL_COMPLEMENT("SC"),
	BIG("BG"),
	BIG_COMPLEMENT("BC"),
	FIRST_ROUND("FR"),
	ROUNDS("RS"),
	DEALER("DL");
	
	private String value;
	
	BlindEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
