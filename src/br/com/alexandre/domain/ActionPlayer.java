package br.com.alexandre.domain;

import java.util.List;

public class ActionPlayer {

	private Long id;
	private Round round;
	private Player player;
	private int position;
	private Boolean smallBlind;
	private Boolean bigBlind;
	private String action;
	private List<Integer> bets;
	
	public ActionPlayer(Round round, Player player, int position, Boolean smallBlind, Boolean bigBlind, String action,
			List<Integer> bets) {
		super();
		this.round = round;
		this.player = player;
		this.position = position;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.action = action;
		this.bets = bets;
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Boolean getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(Boolean smallBlind) {
		this.smallBlind = smallBlind;
	}

	public Boolean getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(Boolean bigBlind) {
		this.bigBlind = bigBlind;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Integer> getBets() {
		return bets;
	}

	public void setBets(List<Integer> bets) {
		this.bets = bets;
	}
	
	
}
