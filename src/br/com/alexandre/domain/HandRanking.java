package br.com.alexandre.domain;

public class HandRanking {

	private Boolean royalFlush;
	private Boolean straightFlush;
	private Boolean fourOfKing;
	private Boolean fullHouse;
	private Boolean flush;
	private Boolean straight;
	private Boolean threeOfKind;
	private Boolean twoPair;
	private Boolean onePair;
	private Boolean highCard;
	
	public HandRanking() {
		
	}

	public Boolean getRoyalFlush() {
		return royalFlush;
	}

	public void setRoyalFlush(Boolean royalFlush) {
		this.royalFlush = royalFlush;
	}

	public Boolean getStraightFlush() {
		return straightFlush;
	}

	public void setStraightFlush(Boolean straightFlush) {
		this.straightFlush = straightFlush;
	}

	public Boolean getFourOfKing() {
		return fourOfKing;
	}

	public void setFourOfKing(Boolean fourOfKing) {
		this.fourOfKing = fourOfKing;
	}

	public Boolean getFullHouse() {
		return fullHouse;
	}

	public void setFullHouse(Boolean fullHouse) {
		this.fullHouse = fullHouse;
	}

	public Boolean getFlush() {
		return flush;
	}

	public void setFlush(Boolean flush) {
		this.flush = flush;
	}

	public Boolean getStraight() {
		return straight;
	}

	public void setStraight(Boolean straight) {
		this.straight = straight;
	}

	public Boolean getThreeOfKind() {
		return threeOfKind;
	}

	public void setThreeOfKind(Boolean threeOfKind) {
		this.threeOfKind = threeOfKind;
	}

	public Boolean getTwoPair() {
		return twoPair;
	}

	public void setTwoPair(Boolean twoPair) {
		this.twoPair = twoPair;
	}

	public Boolean getOnePair() {
		return onePair;
	}

	public void setOnePair(Boolean onePair) {
		this.onePair = onePair;
	}

	public Boolean getHighCard() {
		return highCard;
	}

	public void setHighCard(Boolean highCard) {
		this.highCard = highCard;
	}
	
}
