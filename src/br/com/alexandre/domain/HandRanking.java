package br.com.alexandre.domain;

import java.util.List;

import br.com.alexandre.domain.enums.ScoreHandEnum;

public class HandRanking implements Comparable<HandRanking>{

	private ScoreHandEnum type;
	private Double value;
	private List<Card> handCards;
	private List<Card> kickers;
	private HandPlayer handPlayer;
	
	public HandRanking() {
		
	}

	public ScoreHandEnum getType() {
		return type;
	}

	public void setType(ScoreHandEnum type) {
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
	
	public List<Card> getKickers() {
		return kickers;
	}

	public void setKickers(List<Card> kickers) {
		this.kickers = kickers;
	}
	
	public HandPlayer getHandPlayer() {
		return handPlayer;
	}

	public void setHandPlayer(HandPlayer handPlayer) {
		this.handPlayer = handPlayer;
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
