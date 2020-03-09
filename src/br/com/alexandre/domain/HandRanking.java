package br.com.alexandre.domain;

import java.util.List;

public class HandRanking {

	private String type;
	private Double value;
	private List<Card> handCards;
	
	public HandRanking() {
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public List<Card> getHandCards() {
		return handCards;
	}

	public void setHandCards(List<Card> handCards) {
		this.handCards = handCards;
	}
	
}
