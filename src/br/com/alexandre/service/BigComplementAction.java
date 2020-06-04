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
		if(!roundPlayer.getRound().getCurrentBet().equals(0.0)) {
			init();
			System.out.println("Type:");
			if(roundPlayer.getRound().getCurrentBet().equals(hand.getCurrentBigBlind())) {
				canCheck = true;
				canFold = false;
				msg = "c for check | b for bet";
			}
			System.out.println(msg);
			getPlayerAction();
		}
	}
	
	@Override
	public void checkAction() {
	}

}
