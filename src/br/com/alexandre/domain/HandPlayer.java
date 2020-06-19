package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandre.enuns.BlindEnum;
import br.com.alexandre.enuns.StatusEnum;

public class HandPlayer {

	private Long id;
	private Hand hand;
	private TablePlayer tablePlayer;
	private List<Card> cards;
	private StatusEnum status;
	private Boolean winner;
	private HandRanking handRanking;
	private Double totalBet;
	private List<Card> playerHandCards;
	private BlindEnum blind;
	private Boolean dealer;
	
	public HandPlayer() {
		
	}
	
	public HandPlayer(Long id, Hand hand, TablePlayer tablePlayer, List<Card> cards, StatusEnum status, Boolean winner,
					  HandRanking handRanking, Double totalBet, List<Card> playerHandCards, BlindEnum blind, Boolean dealer) {
		super();
		this.id = id;
		this.hand = hand;
		this.tablePlayer = tablePlayer;
		this.cards = cards;
		this.status = status;
		this.winner = winner;
		this.handRanking = handRanking;
		this.totalBet = totalBet;
		this.playerHandCards = playerHandCards;
		this.blind = blind;
		this.dealer = dealer;
	}
	
	public HandPlayer(Long id, Hand hand, TablePlayer tablePlayer) {
		this(id, hand, tablePlayer, new ArrayList<Card>(), StatusEnum.IN, false, new HandRanking(),
			 0.0, new ArrayList<Card>(), BlindEnum.MIDDLE, false);
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

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public HandRanking getHandRanking() {
		return handRanking;
	}

	public void setHandRanking(HandRanking handRanking) {
		this.handRanking = handRanking;
	}

	public Double getTotalBet() {
		return totalBet;
	}

	public void setTotalBet(Double totalBet) {
		this.totalBet += totalBet;
	}

	public List<Card> getPlayerHandCards() {
		return playerHandCards;
	}

	public void setPlayerHandCards(List<Card> playerHandCards) {
		this.playerHandCards = playerHandCards;
	}

	public BlindEnum getBlind() {
		return blind;
	}

	public void setBlind(BlindEnum blind) {
		this.blind = blind;
	}

	public Boolean getDealer() {
		return dealer;
	}

	public void setDealer(Boolean dealer) {
		this.dealer = dealer;
	}

	@Override
	public String toString() {
		return "HandPlayer [name=" + tablePlayer.getPlayer().getNickname() + "]";
	}
	
}
