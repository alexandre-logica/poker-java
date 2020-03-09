package br.com.alexandre.domain.aux;

public enum ScoreHandEnum {

	ROYAL_FLUSH(10.0),
	STRAIGHT_FLUSH(9.0),
	FOUR_OF_KIND(8.0),
	FULL_HOUSE(7.0),
	FLUSH(6.0),
	STRAIGHT(5.0),
	THREE_OF_KIND(4.0),
	TWO_PAIR(3.0),
	ONE_PAIR(2.0),
	HIGH_CARD(1.0);
	
	private Double value;
	
	ScoreHandEnum(Double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}
}
