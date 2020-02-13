package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {

	private long id;
	private int number;
	private int pot;
	private int totalPlayers;
	private Hand hand;
	private List<ActionPlayer> actionPlayers = new ArrayList<ActionPlayer>();
	
	public Round(long id, int number, int pot, Hand hand, List<ActionPlayer> actionPlayers, int totalPlayers) {
		this.id = id;
		this.number = number;
		this.pot = pot;
		this.hand = hand;
		this.actionPlayers = actionPlayers;
		this.totalPlayers = totalPlayers;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public List<ActionPlayer> getActionPlayers() {
		return actionPlayers;
	}

	public void setActionPlayers(List<ActionPlayer> actionPlayers) {
		this.actionPlayers = actionPlayers;
	}

	public int getTotalPlayers() {
		return totalPlayers;
	}

	public void setTotalPlayers(int totalPlayers) {
		this.totalPlayers = totalPlayers;
	}
	
}
