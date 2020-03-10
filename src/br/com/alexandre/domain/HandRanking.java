package br.com.alexandre.domain;

import java.util.List;

public class HandRanking implements Comparable<HandRanking>{

	private String type;
	private Double value;
	private List<Card> handCards;
	private List<Integer> kickers;
	
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
	
	public List<Integer> getKickers() {
		return kickers;
	}

	public void setKickers(List<Integer> kickers) {
		this.kickers = kickers;
	}

	@Override
	public String toString() {
		return "HandRanking [type=" + type + ", value=" + value + "]";
	}
	
	@Override
	public int compareTo(HandRanking o) {
		return this.getValue().compareTo(o.getValue());
	}
}
