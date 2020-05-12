package br.com.alexandre.service;

import br.com.alexandre.domain.ActionPlayer;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.enuns.ActionEnum;

public class BigAction extends ActionPlayer{
	
	public BigAction(Hand hand, RoundPlayer roundPlayer) {
		super(hand, roundPlayer);
	}

	@Override
	public void action() {
		init();
		System.out.println("Big blind mandatory");
		System.out.println("Value: "+hand.getCurrentBigBlind());
		actionEnum = ActionEnum.BET;
		bet = hand.getCurrentBigBlind();
		roundPlayer.setAction(this);
		roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-hand.getCurrentBigBlind());
		roundPlayer.setTotalBet(roundPlayer.getTotalBet()+hand.getCurrentBigBlind());
	}
	
	@Override
	public Boolean checkAction() {
		return null;
	}

}
