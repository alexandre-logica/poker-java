package br.com.alexandre.enuns;

public enum ScoreHandEnum {

	ROYAL_FLUSH(10000000.0),
	STRAIGHT_FLUSH(9000000.0),
	FOUR_OF_KIND(8000000.0),
	FULL_HOUSE(7000000.0),
	FLUSH(6000000.0),
	STRAIGHT(5000000.0),
	THREE_OF_KIND(4000000.0),
	TWO_PAIR(3000000.0),
	ONE_PAIR(2000000.0),
	HIGH_CARD(1000000.0);
	
	private Double value;
	
	ScoreHandEnum(Double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}
}
