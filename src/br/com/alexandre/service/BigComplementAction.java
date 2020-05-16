package br.com.alexandre.service;

import br.com.alexandre.domain.ActionPlayer;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.RoundPlayer;

public class BigComplementAction extends ActionPlayer{

	public BigComplementAction(Hand hand, RoundPlayer roundPlayer) {
		super(hand, roundPlayer);
	}

	@Override
	public void action() {
		init();
		System.out.println("Type:");
		if(roundPlayer.getRound().getCurrentBet() <= hand.getCurrentBigBlind()) {
			canCheck = true;
			msg = "c for check | b for bet | f for fold";
		}
		System.out.println(msg);
		getPlayerAction();
	}
	
	@Override
	public void checkAction() {
	}

}
