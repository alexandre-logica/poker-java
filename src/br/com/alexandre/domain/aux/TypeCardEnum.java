package br.com.alexandre.domain.aux;

public enum TypeCardEnum {

	FLOP("F"),
	TURN("T"),
	RIVER("R"),
	PLAYER("P");
	
	private String value;
	
	TypeCardEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
