package br.com.alexandre.domain;

public class Card {

	private int rank;
	private String suit;
	
	public Card() {
		
	}

	public Card(String suit, int rank) {
		super();
		this.suit = suit;
		this.rank = rank;
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

	@Override
	public String toString() {
		return "Card [rank=" + rank + ", suit=" + suit + "]";
	}

}
