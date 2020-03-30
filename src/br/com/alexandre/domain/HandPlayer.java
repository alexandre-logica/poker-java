package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandre.enuns.StatusEnum;

public class HandPlayer {

	private Long id;
	private Hand hand;
	private TablePlayer tablePlayer;
	private List<Card> cards;
	private String status;
	private Boolean winner;
	
	public HandPlayer() {
		
	}
	
	public HandPlayer(Long id, Hand hand, TablePlayer tablePlayer, List<Card> cards, String status, Boolean winner) {
		super();
		this.id = id;
		this.hand = hand;
		this.tablePlayer = tablePlayer;
		this.cards = cards;
		this.status = status;
		this.winner = winner;
	}
	
	public HandPlayer(Long id, Hand hand, TablePlayer tablePlayer) {
		this(id, hand, tablePlayer, new ArrayList<Card>(), StatusEnum.IN.toString(), false);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public TablePlayer getTablePlayer() {
		return tablePlayer;
	}

	public void setTablePlayer(TablePlayer tablePlayer) {
		this.tablePlayer = tablePlayer;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

}
