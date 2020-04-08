package br.com.alexandre.domain;

import br.com.alexandre.enuns.ActionEnum;

public class ActionPlayer {

	private ActionEnum actionEnum;
	private Double bet;
	
	public ActionPlayer() {
		
	}
	
	public ActionPlayer(ActionEnum actionEnum, Double bet) {
		this.actionEnum = actionEnum;
		this.bet = bet;
	}

	public ActionEnum getActionEnum() {
		return actionEnum;
	}

	public void setActionEnum(ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}

	public Double getBet() {
		return bet;
	}

	public void setBet(Double bet) {
		this.bet = bet;
	}
	
}
