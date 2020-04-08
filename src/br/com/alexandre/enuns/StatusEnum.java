package br.com.alexandre.enuns;

public enum StatusEnum {
	IN("I"),
	OUT("O");
	
	private String value;
	
	StatusEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
