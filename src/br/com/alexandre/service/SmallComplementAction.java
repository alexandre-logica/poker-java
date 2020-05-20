package br.com.alexandre.service;

import br.com.alexandre.domain.ActionPlayer;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.RoundPlayer;

public class SmallComplementAction extends ActionPlayer{
	
	public SmallComplementAction(Hand hand, RoundPlayer roundPlayer) {
		super(hand, roundPlayer);
	}

	@Override
	public void action() {
		init();
		System.out.println("Type:");
		System.out.println(msg);
		getPlayerAction();
	}
	
	@Override
	public void checkAction() {
		if((roundPlayer.getTotalBet() + bet) < minimumBet) {
			System.out.println("Minimum bet: "+minimumBet);
			corretBet = false;
		}
	}
	
	@Override
	public void setMinimumBet() {
		if(roundPlayer.getRound().getCurrentBet() > hand.getCurrentBigBlind() / 2)
			minimumBet = roundPlayer.getRound().getCurrentBet();
		else 
			minimumBet = hand.getCurrentBigBlind() / 2;
	}

}
