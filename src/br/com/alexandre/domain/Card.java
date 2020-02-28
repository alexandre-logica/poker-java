package br.com.alexandre.domain;

public class Card {

	private int rank;
	private String suit;
	private String symbol;
	
	public Card() {
		
	}

	public Card(String suit, int rank, String symbol) {
		super();
		this.suit = suit;
		this.rank = rank;
		this.symbol = symbol;
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

	@Override
	public String toString() {
		return "Card [rank=" + rank + ", suit=" + suit + ", symbol=" +symbol + "]";
	}

}
