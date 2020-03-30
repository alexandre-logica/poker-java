package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {

	private Integer number;
	private Double pot;
	private Integer totalPlayers;
	private Hand hand;
	private List<RoundPlayer> roundPlayers = new ArrayList<RoundPlayer>();
	private Boolean winner;
	
	public Round(Integer number, Double pot, Integer totalPlayers, Hand hand, List<RoundPlayer> roundPlayers, Boolean winner) {
		super();
		this.number = number;
		this.pot = pot;
		this.totalPlayers = totalPlayers;
		this.hand = hand;
		this.roundPlayers = roundPlayers;
		this.winner = winner;
	}
	
	public Round(Integer number, Hand hand) {
		this(number, 0.0, 0, hand, new ArrayList<RoundPlayer>(), false);
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

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	
}
