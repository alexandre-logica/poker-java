package br.com.alexandre.domain;

public class Card {

	private Integer rank;
	private String suit;
	private String symbol;
	private String character;
	
	public Card() {
		
	}

	public Card(String suit, Integer rank, String symbol, String character) {
		super();
		this.suit = suit;
		this.rank = rank;
		this.symbol = symbol;
		this.character = character;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	@Override
	public String toString() {
		return "Card [rank=" + rank + ", suit=" + suit + ", symbol=" +symbol + "]";
	}

}
