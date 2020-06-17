package br.com.alexandre.domain;

import br.com.alexandre.enuns.ActionEnum;

public class SmallAction extends RoundPlayer{

	public SmallAction(Long id, Round round, HandPlayer handPlayer, Integer roundPosition) {
		super(id,round, handPlayer, roundPosition);
		init();
	}
	
	public SmallAction(Long id, Round round, HandPlayer handPlayer) {
		super(id,round, handPlayer);
		init();
	}
	
	@Override
	protected void init() {
		super.init();
		System.out.println("Small blind mandatory");
		System.out.println("Value: "+handPlayer.getHand().getCurrentBigBlind()/2);
		actionEnum = ActionEnum.BET;
		bet = handPlayer.getHand().getCurrentBigBlind()/2;
		handPlayer.getTablePlayer().setChips(handPlayer.getTablePlayer().getChips()-handPlayer.getHand().getCurrentBigBlind()/2);
		setTotalBet(totalBet+handPlayer.getHand().getCurrentBigBlind()/2);
		handPlayer.getHand().setPot(bet);
	}
	
	@Override
	public void action() {
		super.init();
		System.out.println("Type:");
		System.out.println(msg);
		getPlayerAction();
	}
	
}
