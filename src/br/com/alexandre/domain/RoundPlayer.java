package br.com.alexandre.domain;

import br.com.alexandre.enuns.BlindEnum;

public class RoundPlayer {

	private Long id;
	private Round round;
	private HandPlayer handPlayer;
	private BlindEnum blind;
	private ActionPlayer action;
	private Boolean allIn;
	private Double totalBet;
	
	public RoundPlayer() {
		this.totalBet = 0.0;
	}

	public RoundPlayer(Long id, Round round, HandPlayer handPlayer, BlindEnum blind, ActionPlayer action, Boolean allIn, Double totalBet) {
		super();
		this.id = id;
		this.round = round;
		this.handPlayer = handPlayer;
		this.blind = blind;
		this.action = action;
		this.allIn = allIn;
		this.totalBet = totalBet;
	}
	
	public RoundPlayer(Long id, Round round, HandPlayer handPlayer, Double totalBet) {
		this(id, round, handPlayer, BlindEnum.FIRST_ROUND, null, false, totalBet);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public HandPlayer getHandPlayer() {
		return handPlayer;
	}

	public void setHandPlayer(HandPlayer handPlayer) {
		this.handPlayer = handPlayer;
	}
	
	public BlindEnum getBlind() {
		return blind;
	}

	public void setBlind(BlindEnum blind) {
		this.blind = blind;
	}

	public ActionPlayer getAction() {
		return action;
	}

	public void setAction(ActionPlayer action) {
		this.action = action;
	}

	public Boolean getAllIn() {
		return allIn;
	}

	public void setAllIn(Boolean allIn) {
		this.allIn = allIn;
		round.setAllIn(allIn);
	}

	public Double getTotalBet() {
		return totalBet;
	}

	public void setTotalBet(Double totalBet) {
		this.totalBet += totalBet;
		handPlayer.setTotalBet(totalBet);
	}
	
}
