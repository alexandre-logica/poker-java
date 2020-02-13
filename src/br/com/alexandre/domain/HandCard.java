package br.com.alexandre.domain;

public class HandCard {
	
	private Hand hand;
	private Card card;
	private String type;
	
	public HandCard() {

	}
	
	public HandCard(Hand hand, Card card, String type) {
		super();
		this.hand = hand;
		this.card = card;
		this.type = type;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
