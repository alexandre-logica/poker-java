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
		if(bet < minimumBet) {
			System.out.println("Minimum bet: "+minimumBet);
			corretBet = false;
		}else if(bet > minimumBet && (bet - minimumBet) < hand.getCurrentBigBlind()) {
			System.out.println("To increase the bet, the minimum is: "+minimumBet+"(Complement) + "+hand.getCurrentBigBlind()+"(big blind)"+
					   "Total: "+(minimumBet+hand.getCurrentBigBlind()));
			corretBet = false;
		}
	}
	
	@Override
	public void setMinimumBet() {
		minimumBet = hand.getCurrentBigBlind() / 2;
	}

}
