package br.com.alexandre.domain.enums;

public enum ScoreHandEnum {

	ROYAL_FLUSH(10000000.0, "Royal Flush"),
	STRAIGHT_FLUSH(9000000.0, "Straight Flush"),
	FOUR_OF_KIND(8000000.0, "Four of Kind"),
	FULL_HOUSE(7000000.0, "Full House"),
	FLUSH(6000000.0, "Flush"),
	STRAIGHT(5000000.0, "Straight"),
	THREE_OF_KIND(4000000.0, "Three of Kind"),
	TWO_PAIR(3000000.0, "Two Pair"),
	ONE_PAIR(2000000.0, "One Pair"),
	HIGH_CARD(1000000.0, "High Card");
	
	private Double value;
	private String name;
	
	ScoreHandEnum(Double value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public Double getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
}
