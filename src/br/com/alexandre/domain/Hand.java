package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private Integer number;
	private List<HandPlayer> handPlayers;
	private List<Round> rounds;
	private List<HandCard> handCards;
	private List<HandPlayer> winners;
	private Deck deck;
	private Double currentBigBlind;
	private Double pot;
	
	public Hand(Integer number, List<HandPlayer> handPlayers, List<Round> rounds, List<HandCard> handCards,
			    List<HandPlayer> winners, Deck deck, Double currentBigBlind, Double pot) {
		super();
		this.number = number;
		this.handPlayers = handPlayers;
		this.rounds = rounds;
		this.handCards = handCards;
		this.winners = winners;
		this.deck = deck;
		this.currentBigBlind = currentBigBlind;
		this.handPlayers = handPlayers;
	}
	
	public Hand(Integer number, Deck deck, Double currentBigBlind) {
		this(number, new ArrayList<HandPlayer>(), new ArrayList<Round>(), new ArrayList<HandCard>(),
			 new ArrayList<HandPlayer>(), deck, currentBigBlind, 0.0);
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<HandPlayer> getHandPlayers() {
		return handPlayers;
	}

	public void setHandPlayers(List<HandPlayer> handPlayers) {
		this.handPlayers = handPlayers;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public List<HandCard> getHandCards() {
		return handCards;
	}

	public void setHandCards(List<HandCard> handCards) {
		this.handCards = handCards;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public Double getCurrentBigBlind() {
		return currentBigBlind;
	}

	public void setCurrentBigBlind(Double currentBigBlind) {
		this.currentBigBlind = currentBigBlind;
	}

	public List<HandPlayer> getWinners() {
		return winners;
	}

	public void setWinners(List<HandPlayer> winners) {
		this.winners = winners;
	}

	public Double getPot() {
		return pot;
	}

	public void setPot(Double pot) {
		this.pot = pot;
	}
	
}
