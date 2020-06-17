package br.com.alexandre.domain;

import java.util.Scanner;

import br.com.alexandre.enuns.ActionEnum;
import br.com.alexandre.enuns.BlindEnum;
import br.com.alexandre.enuns.StatusEnum;

public class RoundPlayer implements Comparable<RoundPlayer>{

	protected Long id;
	protected Round round;
	protected HandPlayer handPlayer;
	protected Integer roundPosition; 
	protected BlindEnum blind;
	protected ActionEnum actionEnum;
	protected Boolean allIn = false;
	protected Boolean smallerAllIn = false;
	protected Double totalBet = 0.0;
	protected Double bet = 0.0;
	protected Boolean canCheck = false;
	protected Boolean canFold = true;
	protected String msg = "b for bet | f for fold";
	protected Double minimumBet = 0.0;
	
	public RoundPlayer() {
		super();
	}

	public RoundPlayer(Long id, Round round, HandPlayer handPlayer, Integer roundPosition) {
		super();
		this.id = id;
		this.round = round;
		this.handPlayer = handPlayer;
		this.roundPosition = roundPosition;
	}
	
	public RoundPlayer(Long id, Round round, HandPlayer handPlayer) {
		super();
		this.id = id;
		this.round = round;
		this.handPlayer = handPlayer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public HandPlayer getHandPlayer() {
		return handPlayer;
	}
	
	public Integer getRoundPosition() {
		return roundPosition;
	}

	public void setRoundPosition(Integer roundPosition) {
		this.roundPosition = roundPosition;
	}

	public void setHandPlayer(HandPlayer handPlayer) {
		this.handPlayer = handPlayer;
	}
	
	public BlindEnum getBlind() {
		return blind;
	}

	public void setBlind(BlindEnum blind) {
		this.blind = blind;
	}

	public Boolean getAllIn() {
		return allIn;
	}

	public void setAllIn(Boolean allIn) {
		this.allIn = allIn;
		round.setAllIn(allIn);
	}
	
	public Boolean getSmallerAllIn() {
		return smallerAllIn;
	}

	public void setSmallerAllIn(Boolean smallerAllIn) {
		this.smallerAllIn = smallerAllIn;
	}

	public Double getTotalBet() {
		return totalBet;
	}

	public void setTotalBet(Double totalBet) {
		this.totalBet += totalBet;
		handPlayer.setTotalBet(totalBet);
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

	public void action() {
		init();
		System.out.println("Type:");
		if(round.getCurrentBet().equals(0.0) && round.getNumber() > 1) {
			canCheck = true;
			canFold = false;
			msg = "c for check | b for bet";
		}
		System.out.println(msg);
		getPlayerAction();
	}
	
	protected void init() {
		System.out.println();
		System.out.println("Action by: "+handPlayer.getTablePlayer().getPlayer().getNickname());
	}
	
	protected void setMinimumBet() {
		if(round.getCurrentBet() > handPlayer.getHand().getCurrentBigBlind())
			minimumBet = round.getCurrentBet();
		else 
			minimumBet = handPlayer.getHand().getCurrentBigBlind();
		
		if((handPlayer.getTablePlayer().getChips() + totalBet) < minimumBet)
			minimumBet = handPlayer.getTablePlayer().getChips() + totalBet;
	}
	
	private Boolean checkBet(Scanner sc) {
		Boolean corretBet = false;
		System.out.println("Pot: "+handPlayer.getHand().getPot());
		System.out.println("Total chips: "+handPlayer.getTablePlayer().getChips());
		System.out.println("Value already betted in this hand: "+handPlayer.getTotalBet());
		System.out.println("Value already betted in this round: "+totalBet);
		System.out.println("Minimum bet: "+(minimumBet - totalBet));
		System.out.println("Type your bet: ");
		bet = sc.nextDouble();
		if((totalBet + bet) < minimumBet) {
			System.out.println("Current bet: "+round.getCurrentBet());
			if((handPlayer.getTablePlayer().getChips() + totalBet) < round.getCurrentBet())
				System.out.println("Minimum bet (all in): "+(minimumBet - totalBet));
			else
				System.out.println("Minimum bet: "+(minimumBet - totalBet));
		}else if(bet > handPlayer.getTablePlayer().getChips()) {
			System.out.println("Maximum bet (all in) : "+handPlayer.getTablePlayer().getChips());
		}else if((totalBet + bet) > minimumBet && (totalBet + bet) < (minimumBet * 2)) {
			if((handPlayer.getTablePlayer().getChips() + totalBet) >= (minimumBet * 2)) {
				System.out.println("To increase the bet, the minimum is: "+((minimumBet * 2) - totalBet));
			} else if(bet < handPlayer.getTablePlayer().getChips()) {
				System.out.println("To increase the bet, the minimum is (All in): "+ handPlayer.getTablePlayer().getChips());
			}else {
				corretBet = true;
			}
		}else {
			corretBet = true;
		}
		return corretBet;
	}
	
	protected void getPlayerAction() {
		Boolean corretAction = false;
		Boolean corretBet = false;
		while(!corretAction) {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Action: ");
				String action = sc.nextLine();
				if(action.equals(ActionEnum.BET.getValue())) {
					corretAction = true;
					setMinimumBet();
					while(!corretBet) {
						corretBet = checkBet(sc);
						if(corretBet) {
							if(handPlayer.getTablePlayer().getChips().equals(bet)) {
								setAllIn(true);
								if(bet < round.getCurrentBet())
									setSmallerAllIn(true);
								round.setAllInValue(bet);
								System.out.println("All in by "+handPlayer.getTablePlayer().getPlayer().getNickname());
								System.out.println("Total: "+ (totalBet + bet) + "chips");
							}
							actionEnum = ActionEnum.BET;
							handPlayer.getTablePlayer().decreaseChips(bet);;
							setTotalBet(bet);
							if(totalBet > round.getCurrentBet())
								round.setCurrentBet(totalBet);
							handPlayer.getHand().setPot(bet);
						}
					}
				}else if(action.equals(ActionEnum.FOLD.getValue())){
					if(canFold) {
						corretAction = true;
						this.actionEnum = ActionEnum.FOLD;
						this.bet = (0.0);
						handPlayer.setStatus(StatusEnum.OUT);
					}
				}else if(action.equals(ActionEnum.CHECK.getValue())) {
					if(canCheck) {
						corretAction = true;
						this.actionEnum = ActionEnum.CHECK;
						this.bet = (0.0);
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
	
	@Override
	public String toString() {
		return "RoundPlayer [handPlayer=" + handPlayer.getTablePlayer().getPlayer().getNickname() + ",  blind=" + handPlayer.getBlind().name()+ "]";
	}

	@Override
	public int compareTo(RoundPlayer o) {
		return this.getRoundPosition().compareTo(o.getRoundPosition());
	}
}
