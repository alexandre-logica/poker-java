package br.com.alexandre.domain;

public class Card {

	private int rank;
	private String suit;
	private String symbol;
	private String character;
	
	public Card() {
		
	}

	public Card(String suit, int rank, String symbol, String character) {
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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
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
