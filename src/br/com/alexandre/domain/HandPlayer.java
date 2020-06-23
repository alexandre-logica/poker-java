package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandre.domain.enums.BlindEnum;
import br.com.alexandre.domain.enums.StatusEnum;

public class HandPlayer extends Player{
	
	protected Hand hand;
	protected List<Card> cards;
	protected StatusEnum status;
	protected Boolean winner;
	protected HandRanking handRanking;
	protected Double totalBet;
	protected List<Card> playerHandCards;
	protected BlindEnum blind;
	protected Boolean dealer;
	
	public HandPlayer(Integer id, String nickname) {
		super(id, nickname);
	}
	
	public HandPlayer() {
	}
	
	public HandPlayer(Hand hand, List<Card> cards, StatusEnum status, Boolean winner,
					  HandRanking handRanking, Double totalBet, List<Card> playerHandCards, BlindEnum blind, Boolean dealer) {
		this.hand = hand;
		this.cards = cards;
		this.status = status;
		this.winner = winner;
		this.handRanking = handRanking;
		this.totalBet = totalBet;
		this.playerHandCards = playerHandCards;
		this.blind = blind;
		this.dealer = dealer;
	}
	
	public HandPlayer(Hand hand) {
		this(hand, new ArrayList<Card>(), StatusEnum.IN, false, new HandRanking(),
			 0.0, new ArrayList<Card>(), BlindEnum.MIDDLE, false);
	}
	
	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
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
		this.totalBet = totalBet;
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
	
	public void increaseTotalBet(Double totalBet) {
		this.totalBet += totalBet;
	}

	public void cleanUp(Hand hand) {
		this.hand = hand;
		this.cards = new ArrayList<Card>();
		this.status = StatusEnum.IN;
		this.winner = false;
		this.handRanking = new HandRanking();
		this.totalBet = 0.0;
		this.playerHandCards = new ArrayList<Card>();
		this.blind = null;
		this.dealer = false;
	}
	
	@Override
	public String toString() {
		return "HandPlayer [name=" + getNickname() + "]";
	}
	
}
