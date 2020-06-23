package br.com.alexandre.domain;

import java.util.Scanner;

import br.com.alexandre.domain.enums.ActionEnum;
import br.com.alexandre.domain.enums.StatusEnum;

public class RoundPlayer extends HandPlayer implements Comparable<RoundPlayer>{
	
	protected Round round;
	protected Integer roundPosition; 
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

	}

	public RoundPlayer(Integer id, String nickname) {
		super(id, nickname);
	}
	
	public RoundPlayer(Round round, Integer roundPosition) {
		this.round = round;
		this.roundPosition = roundPosition;
	}
	
	public RoundPlayer(Round round) {
		this.round = round;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Integer getRoundPosition() {
		return roundPosition;
	}

	public void setRoundPosition(Integer roundPosition) {
		this.roundPosition = roundPosition;
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
		this.totalBet = totalBet;
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
	
	public Boolean getCanCheck() {
		return canCheck;
	}

	public void setCanCheck(Boolean canCheck) {
		this.canCheck = canCheck;
	}

	public Boolean getCanFold() {
		return canFold;
	}

	public void setCanFold(Boolean canFold) {
		this.canFold = canFold;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Double getMinimumBet() {
		return minimumBet;
	}

	public void setMinimumBet(Double minimumBet) {
		this.minimumBet = minimumBet;
	}

	@Override
	public void increaseTotalBet(Double totalBet) {
		this.totalBet += totalBet;
		super.increaseTotalBet(totalBet);
	}
	
	private void bet(Scanner sc) {
		Boolean corretBet = false;
		setMinimumBet();
		while(!corretBet) {
			corretBet = checkBet(sc);
			if(corretBet) {
				if(getChips().equals(bet)) {
					setAllIn(true);
					if(bet < round.getCurrentBet())
						setSmallerAllIn(true);
					round.setAllInValue(bet);
					System.out.println("All in by "+getNickname());
					System.out.println("Total: "+ (this.totalBet + bet) + "chips");
				}
				actionEnum = ActionEnum.BET;
				decreaseChips(bet);;
				this.increaseTotalBet(bet);
				if(this.totalBet > round.getCurrentBet())
					round.setCurrentBet(this.totalBet);
				hand.setPot(bet);
			}
		}
	}

	private void fold() {
			this.actionEnum = ActionEnum.FOLD;
			this.bet = (0.0);
			super.setStatus(StatusEnum.OUT);
	}
	
	private void check() {
		this.actionEnum = ActionEnum.CHECK;
		this.bet = (0.0);
	}

	public void action() {
		init();
		System.out.println("Type:");
		switch (blind) {
		case SMALL:
			System.out.println(msg);
			getPlayerAction();
			break;
		case BIG:
			if(round.getCurrentBet() > 0.0) {
				if(round.getCurrentBet().equals(hand.getCurrentBigBlind())) {
					canCheck = true;
					canFold = false;
					msg = "c for check | b for bet";
				}
				System.out.println(msg);
				getPlayerAction();
			}
			break;
		default:
			if(round.getCurrentBet().equals(0.0) && round.getNumber() > 1) {
				canCheck = true;
				canFold = false;
				msg = "c for check | b for bet";
			}
			System.out.println(msg);
			getPlayerAction();
			break;
		}
		
	}
	
	protected void init() {
		System.out.println();
		System.out.println("Action by: " + getNickname());
	}
	
	public void initSmall() {
		init();
		System.out.println("Small blind mandatory");
		System.out.println("Value: "+hand.getCurrentBigBlind()/2);
		actionEnum = ActionEnum.BET;
		bet = hand.getCurrentBigBlind()/2;
		setChips(getChips()-hand.getCurrentBigBlind()/2);
		this.increaseTotalBet(hand.getCurrentBigBlind()/2);
		hand.setPot(bet);
	}
	
	public void initBig() {
		init();
		System.out.println("Big blind mandatory");
		System.out.println("Value: "+hand.getCurrentBigBlind());
		actionEnum = ActionEnum.BET;
		bet = hand.getCurrentBigBlind();
		setChips(getChips()-hand.getCurrentBigBlind());
		this.increaseTotalBet(hand.getCurrentBigBlind());
		hand.setPot(bet);
	}
		
	protected void setMinimumBet() {
		if(round.getCurrentBet() > hand.getCurrentBigBlind())
			minimumBet = round.getCurrentBet();
		else 
			minimumBet = hand.getCurrentBigBlind();
		
		if((getChips() + totalBet) < minimumBet)
			minimumBet = getChips() + totalBet;
	}
	
	private Boolean checkBet(Scanner sc) {
		Boolean corretBet = false;
		System.out.println("Pot: "+hand.getPot());
		System.out.println("Total chips: "+getChips());
		System.out.println("Value already betted in this hand: "+super.totalBet);
		System.out.println("Value already betted in this round: "+this.totalBet);
		System.out.println("Minimum bet: "+(minimumBet - totalBet));
		System.out.println("Type your bet: ");
		bet = sc.nextDouble();
		if((totalBet + bet) < minimumBet) {
			System.out.println("Current bet: "+round.getCurrentBet());
			if((getChips() + totalBet) < round.getCurrentBet())
				System.out.println("Minimum bet (all in): "+(minimumBet - totalBet));
			else
				System.out.println("Minimum bet: "+(minimumBet - totalBet));
		}else if(bet > getChips()) {
			System.out.println("Maximum bet (all in) : "+getChips());
		}else if((totalBet + bet) > minimumBet && (totalBet + bet) < (minimumBet * 2)) {
			if((getChips() + totalBet) >= (minimumBet * 2)) {
				System.out.println("To increase the bet, the minimum is: "+((minimumBet * 2) - totalBet));
			} else if(bet < getChips()) {
				System.out.println("To increase the bet, the minimum is (All in): "+ getChips());
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
		while(!corretAction) {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Action: ");
				String action = sc.nextLine();
				switch (action) {
				case "b":
					corretAction = true;
					bet(sc);
					break;
				case "f":
					if(canFold) {
						corretAction = true;
						fold();
					}
					break;
				case "c":
					if(canCheck) {
						corretAction = true;
						check();
					}
					break;
				default:
					System.out.println("Wrong value!");
					break;
				}
			} catch (Exception e) {
				corretAction = false;
				System.out.println("PokerGameAutomator.getPlayerAction()");
				System.err.println(e);
				System.out.println(e.getMessage());
				System.out.println(e.getCause());
			} 
		}
	}
	
	public void cleanUp(Round round) {
		this.round = round;
		this.roundPosition = null;
		this.actionEnum = null;
		this.allIn = false;
		this.smallerAllIn = false;
		this.totalBet = 0.0;
		this.bet = 0.0;
		this.canCheck = false;
		this.canFold = true;
		this.msg = "b for bet | f for fold";
		this.minimumBet = 0.0;
	}
	
	@Override
	public String toString() {
		return "RoundPlayer [handPlayer=" + getNickname() + ",  blind=" + blind.name()+ "]";
	}

	@Override
	public int compareTo(RoundPlayer o) {
		return this.getRoundPosition().compareTo(o.getRoundPosition());
	}
}
