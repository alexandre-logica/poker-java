package br.com.alexandre.service;

import br.com.alexandre.domain.ActionPlayer;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.enuns.ActionEnum;

public class SmallAction extends ActionPlayer{

	public SmallAction(Hand hand, RoundPlayer roundPlayer) {
		super(hand, roundPlayer);
	}

	@Override
	public void action() {
		init();
		System.out.println("Small blind mandatory");
		System.out.println("Value: "+hand.getCurrentBigBlind()/2);
		actionEnum = ActionEnum.BET;
		bet = hand.getCurrentBigBlind()/2;
		roundPlayer.setAction(this);
		roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-hand.getCurrentBigBlind()/2);
		roundPlayer.setTotalBet(roundPlayer.getTotalBet()+hand.getCurrentBigBlind()/2);
		hand.setPot(bet);
	}
	
	@Override
	public void checkAction() {
	}

}
