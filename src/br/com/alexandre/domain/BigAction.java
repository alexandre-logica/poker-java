package br.com.alexandre.domain;

import br.com.alexandre.enuns.ActionEnum;

public class BigAction extends RoundPlayer{
	
	public BigAction(Long id, Round round, HandPlayer handPlayer, Integer roundPosition) {
		super(id,round, handPlayer, roundPosition);
		init();
	}
	
	@Override
	protected void init() {
		super.init();
		System.out.println("Big blind mandatory");
		System.out.println("Value: "+handPlayer.getHand().getCurrentBigBlind());
		actionEnum = ActionEnum.BET;
		bet = handPlayer.getHand().getCurrentBigBlind();
		handPlayer.getTablePlayer().setChips(handPlayer.getTablePlayer().getChips()-handPlayer.getHand().getCurrentBigBlind());
		setTotalBet(totalBet+handPlayer.getHand().getCurrentBigBlind());
		handPlayer.getHand().setPot(bet);
	}
	
	@Override
	public void action() {
		if(round.getCurrentBet() > 0.0) {
			super.init();
			System.out.println("Type:");
			if(round.getCurrentBet().equals(handPlayer.getHand().getCurrentBigBlind())) {
				canCheck = true;
				canFold = false;
				msg = "c for check | b for bet";
			}
			System.out.println(msg);
			getPlayerAction();
		}
	}
	
}
