package br.com.alexandre.domain.aux;

public enum RankEnum {

	ACE(1, "A"),
	TWO(2, "2"),
	THREE(3, "3"),
	FOUR(4, "4"),
	FIVE(5, "5"),
	SIX(6, "6"),
	SEVEN(7, "7"),
	EIGHT(8, "8"),
	NINE(9, "9"),
	TEN(10, "10"),
	JACK(11, "J"),
	QUEEN(12, "Q"),
	KING(13, "K");
	
	private Integer value;
	private String character;
	
	RankEnum(Integer value, String character){
		this.value = value;
		this.character = character;
	}

	public Integer getValue() {
		return value;
	}
	
	public String getCharacter() {
		return character;
	}
	
}
