package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private Long id;
	private List<HandPlayer> handPlayers = new ArrayList<>();
	private List<Round> rounds = new ArrayList<>();
	private List<HandPlayer> winners;
	private List<HandCard> handCards = new ArrayList<>();
	private Deck deck;
	
	public Hand(Long id, List<HandPlayer> handPlayers, List<Round> rounds, List<HandPlayer> winners,
			List<HandCard> handCards, Deck deck) {
		super();
		this.id = id;
		this.handPlayers = handPlayers;
		this.rounds = rounds;
		this.winners = winners;
		this.handCards = handCards;
		this.deck = deck;
	}
	
	public Hand(Long id, Deck deck) {
		this(id, null, null, null, null, deck);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<HandPlayer> getWinners() {
		return winners;
	}

	public void setWinners(List<HandPlayer> winners) {
		this.winners = winners;
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
	
}
