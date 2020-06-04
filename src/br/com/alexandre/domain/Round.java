package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {

	private Integer number;
	private Double pot;
	private Integer totalPlayers;
	private Hand hand;
	private List<RoundPlayer> roundPlayers = new ArrayList<RoundPlayer>();
	private Boolean handWinner;
	private Boolean allIn;
	private Double allInValue;
	private Double currentBet;
	private RoundPlayer playerIncreasedBet;
	
	public Round(Integer number, Double pot, Integer totalPlayers, Hand hand, List<RoundPlayer> roundPlayers,
			Boolean handWinner, Boolean allIn, Double allInValue, Double currentBet, RoundPlayer playerIncreasedBet) {
		super();
		this.number = number;
		this.pot = pot;
		this.totalPlayers = totalPlayers;
		this.hand = hand;
		this.roundPlayers = roundPlayers;
		this.handWinner = handWinner;
		this.allIn = allIn;
		this.allInValue = allInValue;
		this.currentBet = currentBet;
		this.playerIncreasedBet = playerIncreasedBet;
	}

	public Round(Integer number, Hand hand) {
		this(number, 0.0, 0, hand, new ArrayList<RoundPlayer>(), false, false, 0.0, 0.0, null);
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getPot() {
		return pot;
	}

	public void setPot(Double pot) {
		this.pot = pot;
		hand.setPot(pot);
	}

	public Integer getTotalPlayers() {
		return totalPlayers;
	}

	public void setTotalPlayers(Integer totalPlayers) {
		this.totalPlayers = totalPlayers;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public List<RoundPlayer> getRoundPlayers() {
		return roundPlayers;
	}

	public void setRoundPlayers(List<RoundPlayer> roundPlayers) {
		this.roundPlayers = roundPlayers;
	}

	public Boolean gethandWinner() {
		return handWinner;
	}

	public void sethandWinner(Boolean handWinner) {
		this.handWinner = handWinner;
	}

	public Boolean getAllIn() {
		return allIn;
	}

	public void setAllIn(Boolean allIn) {
		this.allIn = allIn;
	}

	public Double getAllInValue() {
		return allInValue;
	}

	public void setAllInValue(Double allInValue) {
		this.allInValue = allInValue;
	}

	public Double getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(Double currentBet) {
		this.currentBet = currentBet;
	}

	public RoundPlayer getPlayerIncreasedBet() {
		return playerIncreasedBet;
	}

	public void setPlayerIncreasedBet(RoundPlayer playerIncreasedBet) {
		this.playerIncreasedBet = playerIncreasedBet;
	}
	
}
