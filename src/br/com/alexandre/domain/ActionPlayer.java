package br.com.alexandre.domain;

import java.util.Scanner;

import br.com.alexandre.enuns.ActionEnum;
import br.com.alexandre.enuns.StatusEnum;

public abstract class ActionPlayer {

	protected ActionEnum actionEnum;
	protected Double bet;
	protected Hand hand;
	protected RoundPlayer roundPlayer;
	protected Boolean canCheck = false;
	protected String msg = "b for bet | f for fold";
	
	public ActionPlayer() {
		
	}
	
	public ActionPlayer(Hand hand, RoundPlayer roundPlayer) {
		this.hand = hand;
		this.roundPlayer = roundPlayer;
	}
	
	public ActionPlayer(ActionEnum actionEnum, Double bet, Hand hand) {
		this.actionEnum = actionEnum;
		this.bet = bet;
		this.hand = hand;
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

	public abstract void action();
	
	public abstract Boolean checkAction();
	
	protected void init() {
		System.out.println();
		System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
	}
	
	protected void getPlayerAction() {
		Boolean corretAction = false;
		Boolean corretBet = false;
		while(!corretAction) {
			try {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				System.out.println("Action: ");
				String action = sc.nextLine();
				if(action.equals(ActionEnum.BET.getValue())) {
					corretAction = true;
					while(!corretBet) {
						System.out.println("Pot: "+roundPlayer.getRound().getPot());
						System.out.println("Total chips: "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
						System.out.println("Value already betted: "+roundPlayer.getHandPlayer().getTotalBet());
						System.out.println("Value already betted in this round: "+roundPlayer.getTotalBet());
						System.out.println("Type your bet: ");
						Double bet = sc.nextDouble();
						Double minimumBet = 0.0;
//						if(roundPlayer.getRound().getCurrentBet() > hand.getCurrentBigBlind()) {
//							minimumBet = roundPlayer.getRound().getCurrentBet();
//						}else {
//							minimumBet = hand.getCurrentBigBlind();
//							if(blindEnum.equals(BlindEnum.SMALL_COMPLEMENT))
//								minimumBet = hand.getCurrentBigBlind() / 2;
//						}
//						
//						if((roundPlayer.getHandPlayer().getTablePlayer().getChips() + roundPlayer.getTotalBet()) < minimumBet) {
//							if(!bet.equals(roundPlayer.getHandPlayer().getTablePlayer().getChips())) {
//								System.out.println("Minimum bet (all in): "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
//							}else{
//								if(bet > roundPlayer.getRound().getCurrentBet())
//									roundPlayer.getRound().setCurrentBet(bet);
//								corretAction = true;
//							}
//						}
//						else if((roundPlayer.getTotalBet() + bet) < minimumBet)
//							System.out.println("Minimum bet: "+minimumBet);
//						else if(bet > roundPlayer.getHandPlayer().getTablePlayer().getChips())
//							System.out.println("Maximum bet (all in) : "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
//						else if(blindEnum.equals(BlindEnum.BIG_COMPLEMENT) && bet < minimumBet)
//							System.out.println("Minimum bet: "+minimumBet);
//						else if(blindEnum.equals(BlindEnum.SMALL_COMPLEMENT) && bet > minimumBet && (bet - minimumBet) < hand.getCurrentBigBlind())
//							System.out.println("To increase the bet, the minimum is: "+minimumBet+"(Complement) + "+hand.getCurrentBigBlind()+"(big blind)"+
//											   "Total: "+minimumBet+hand.getCurrentBigBlind());
//						else {
//							corretBet = true;
//						}
						if(corretBet) {
							if(roundPlayer.getHandPlayer().getTablePlayer().getChips().equals(bet)) {
								roundPlayer.setAllIn(true);
								roundPlayer.getRound().setAllInValue(bet);
								System.out.println("All in by "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
								System.out.println("Total: "+ (roundPlayer.getTotalBet() + bet) + "chips");
							}
							actionEnum = ActionEnum.BET;
							this.bet = (bet);
							roundPlayer.setAction(this);
							roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-bet);
							roundPlayer.setTotalBet(roundPlayer.getTotalBet()+bet);
							if(roundPlayer.getTotalBet()+bet > roundPlayer.getRound().getCurrentBet() && roundPlayer.getTotalBet()+bet > minimumBet)
								roundPlayer.getRound().setPlayerIncreasedBet(roundPlayer);
							roundPlayer.getRound().setCurrentBet(roundPlayer.getTotalBet());
						}
					}
				}else if(action.equals(ActionEnum.FOLD.getValue())){
					this.actionEnum = ActionEnum.FOLD;
					this.bet = (0.0);
					roundPlayer.setAction(this);
					roundPlayer.getHandPlayer().setStatus(StatusEnum.OUT);
					corretAction = true;
				}else if(action.equals(ActionEnum.CHECK.getValue())) {
					if(canCheck) {
						corretAction = true;
					}
				}else 
					System.out.println("Wrong value!");
			} catch (Exception e) {
				corretAction = false;
				System.out.println("PokerGameAutomator.getPlayerAction()");
				System.err.println(e);
				System.out.println(e.getMessage());
				System.out.println(e.getCause());
			} 
		}
	}
	
}
