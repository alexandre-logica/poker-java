package br.com.alexandre.enuns;

public enum SuitsEnum {
	CLUBS("C", "\u2663"),
	DIAMONDS("D", "\u2666"),
	HEARTS("H", "\u2665"),
	SPADES("S", "\u2660");
	
	private String value;
	private String symbol;
	
	SuitsEnum(String value, String symbol) {
		this.value = value;
		this.symbol = symbol;
	}

	public String getValue() {
		return value;
	}

	public String getSymbol() {
		return symbol;
	}

}
