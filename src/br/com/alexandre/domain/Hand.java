package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private Integer number;
	private List<HandPlayer> handPlayers;
	private List<Round> rounds;
	private List<HandCard> handCards;
	private List<HandRanking> handRankings;
	private Deck deck;
	
	public Hand(Integer number, List<HandPlayer> handPlayers, List<Round> rounds, List<HandCard> handCards, List<HandRanking> handRankings, Deck deck) {
		super();
		this.number = number;
		this.handPlayers = handPlayers;
		this.rounds = rounds;
		this.handCards = handCards;
		this.handRankings = handRankings;
		this.deck = deck;
	}
	
	public Hand(Integer number, Deck deck) {
		this(number, new ArrayList<HandPlayer>(), new ArrayList<Round>(), new ArrayList<HandCard>(), new ArrayList<HandRanking>(), deck);
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

	public List<HandRanking> getHandRankings() {
		return handRankings;
	}

	public void setHandRankings(List<HandRanking> handRankings) {
		this.handRankings = handRankings;
	}
	
}
