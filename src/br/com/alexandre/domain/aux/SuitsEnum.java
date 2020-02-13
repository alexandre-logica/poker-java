package br.com.alexandre.domain.aux;

public enum SuitsEnum {
	CLUBS("C"),
	DIAMONDS("D"),
	HEARTS("H"),
	SPADES("S");
	
	private String value;
	
	SuitsEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
