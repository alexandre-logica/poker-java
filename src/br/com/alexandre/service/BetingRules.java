package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import br.com.alexandre.domain.ActionPlayer;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.enuns.ActionEnum;
import br.com.alexandre.enuns.BlindEnum;
import br.com.alexandre.enuns.StatusEnum;

public class BetingRules {

	private Hand hand;
	private ShowCards showCards;
	private HandRankingRules handRankingHules;
	
	public BetingRules(Hand hand) {
		this.hand = hand;
	}
	
	public List<HandPlayer> runRounds() {
		Round round;
		Integer count = 0;
		Boolean winner = false;
		showCards = new ShowCards(hand);
		do {
			round = new Round(++count, hand);
			round = runBets(round);
			showCards.showTableCards(count);
			winner = checkWinner(round);
		} while(!winner);
		
		return round.getHand().getWinners();
	}
	
	private Boolean checkWinner(Round round) {
		if(round.getRoundPlayers().size() == 1) {
			round.setWinner(true);
			round.getHand().getWinners().add(round.getRoundPlayers().get(0).getHandPlayer());
		}else if(round.getNumber().equals(6) || round.getAllIn()) {
			round.setWinner(true);
			handRankingHules = new HandRankingRules();
			List<HandRanking> handRankings = new ArrayList<>();
			for(RoundPlayer player : round.getRoundPlayers()) {
				HandRanking handRanking = handRankingHules.setPlayerScore(player.getHandPlayer().getPlayerHandCards());
				handRanking.setHandPlayer(player.getHandPlayer());
				handRankings.add(handRanking);
			}
			round.getHand().setWinners(checkMultipleWinners(hand, handRankings));
		}
		return round.getWinner();
	}
	
	private List<HandPlayer> checkMultipleWinners(Hand hand, List<HandRanking> handRankings){
		List<HandPlayer> winners = new ArrayList<>();
		Collections.sort(handRankings, Collections.reverseOrder());
		if(handRankings.get(0).getValue().equals(handRankings.get(1).getValue())) {
			Double value = handRankings.get(0).getValue();
			for(int i = 0; i < handRankings.size(); i++) {
				if(handRankings.get(i).getValue().equals(value)) {
					handRankings.get(i).getHandPlayer().setHandRanking(handRankings.get(i));
					winners.add(handRankings.get(i).getHandPlayer());
				}
			}
		}else {
			handRankings.get(0).getHandPlayer().setHandRanking(handRankings.get(0));
			winners.add(handRankings.get(0).getHandPlayer());
		}
		return winners;
	}
	
	private List<RoundPlayer> createRoundPlayers(Round round) {
		List<RoundPlayer> roundPlayers = new ArrayList<RoundPlayer>();
		RoundPlayer roundPlayer;
		Long id = 0L;
		for (HandPlayer handPlayer : round.getHand().getHandPlayers()) {
			if(handPlayer.getStatus().equals(StatusEnum.IN)) {
				roundPlayer = new RoundPlayer();
				roundPlayer.setId(++id);
				roundPlayer.setRound(round);
				roundPlayer.setHandPlayer(handPlayer);
				roundPlayer.setTotalBet(0.0);
				roundPlayers.add(roundPlayer);
			}
		}
		return roundPlayers;
	}
	
	private void checkPlayerBet(Map<Long, Double> playerMap, RoundPlayer roundPlayer) {
		if(roundPlayer.getAction().getActionEnum().equals(ActionEnum.FOLD))
			playerMap.remove(roundPlayer.getId());
		else if(roundPlayer.getAction().getActionEnum().equals(ActionEnum.BET))
			playerMap.put(roundPlayer.getId(), roundPlayer.getTotalBet());
	}
	
	private Boolean checkSameBet(Map<Long, Double> playerMap) {
		Set<Double> bets = new HashSet<Double>();
		for(Map.Entry<Long, Double> entry : playerMap.entrySet()) {
			bets.add(entry.getValue());
		}
		if(bets.size() == 1)
			return true;
		else
			return false;
	}
	
	private Round runBets(Round round) {
		List<RoundPlayer> roundPlayers = createRoundPlayers(round);
		Boolean sameBet = false;
		Map<Long, Double> playerMap = new HashMap<Long, Double>();
		Integer roundIteration = 0;
		while(!sameBet) {
			++roundIteration;
			for(RoundPlayer roundPlayer : roundPlayers) {
				if(roundIteration.equals(1)) {
					if(roundPlayers.indexOf(roundPlayer) == 0) {
						getBlindAction(BlindEnum.SMALL, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
					}else if(roundPlayers.indexOf(roundPlayer) == 1){
						getBlindAction(BlindEnum.BIG, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
					}else if(roundPlayers.indexOf(roundPlayer) == roundPlayers.size()-1){
						getBlindAction(BlindEnum.FIRST_ROUND, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
						// Small Complement
						getBlindAction(BlindEnum.SMALL_COMPLEMENT, roundPlayers.get(0));
						round.setPot(round.getPot()+roundPlayers.get(0).getAction().getBet());
						checkPlayerBet(playerMap, roundPlayers.get(0));
						// Big Complement
						getBlindAction(BlindEnum.BIG_COMPLEMENT, roundPlayers.get(1));
						round.setPot(round.getPot()+roundPlayers.get(1).getAction().getBet());
						checkPlayerBet(playerMap, roundPlayers.get(1));
					}else {
						getBlindAction(BlindEnum.FIRST_ROUND, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
					}
				}else {
					if(roundPlayer.getRound().getPlayerIncreasedBet() != roundPlayer) {
						getBlindAction(BlindEnum.ROUNDS, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
					}
				}
			}
			sameBet = checkSameBet(playerMap);
		}
		round.setRoundPlayers(roundPlayers);
		return round;
	}
	
	private void getBlindAction(BlindEnum blindEnum, RoundPlayer roundPlayer) {
		ActionPlayer actionPlayer = null;
		Boolean canCheck = false;
		String msg = "b for bet | f for fold";
		switch (blindEnum) {
		case SMALL:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Small blind mandatory");
			System.out.println("Value: "+hand.getCurrentBigBlind()/2);
			actionPlayer = new ActionPlayer(ActionEnum.BET, hand.getCurrentBigBlind()/2);
			roundPlayer.setAction(actionPlayer);
			roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-hand.getCurrentBigBlind()/2);
			roundPlayer.setTotalBet(roundPlayer.getTotalBet()+hand.getCurrentBigBlind()/2);
			break;
		case SMALL_COMPLEMENT:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			System.out.println(msg);
			getPlayerAction(blindEnum, roundPlayer, false);
			break;	
		case BIG:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Big blind mandatory");
			System.out.println("Value: "+hand.getCurrentBigBlind());
			actionPlayer = new ActionPlayer(ActionEnum.BET, hand.getCurrentBigBlind());
			roundPlayer.setAction(actionPlayer);
			roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-hand.getCurrentBigBlind());
			roundPlayer.setTotalBet(roundPlayer.getTotalBet()+hand.getCurrentBigBlind());
			break;
		case BIG_COMPLEMENT:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			if(roundPlayer.getRound().getCurrentBet() <= hand.getCurrentBigBlind()) {
				canCheck = true;
				msg = "c for check | b for bet | f for fold";
			}
			System.out.println(msg);
			getPlayerAction(blindEnum, roundPlayer, canCheck);
			break;
		case FIRST_ROUND:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			System.out.println(msg);
			getPlayerAction(blindEnum, roundPlayer, false);
			break;
		case ROUNDS:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			if(roundPlayer.getRound().getCurrentBet().equals(0.0)) {
				canCheck = true;
				msg = "c for check | b for bet | f for fold";
			}
			System.out.println(msg);
			getPlayerAction(blindEnum, roundPlayer, canCheck);
			break;
		default:
			break;
		}
	}
	
	private void getPlayerAction(BlindEnum blindEnum, RoundPlayer roundPlayer, Boolean canCheck) {
		Boolean corretAction = false;
		Boolean corretBet = false;
		ActionPlayer actionPlayer = null;
		while(!corretAction) {
			try {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				System.out.println("Action: ");
				String action = sc.nextLine();
				if(action.equals(ActionEnum.BET.getValue())) {
					while(!corretBet) {
						System.out.println("Pot: "+roundPlayer.getRound().getPot());
						System.out.println("Total chips: "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
						System.out.println("Value already betted: "+roundPlayer.getHandPlayer().getTotalBet());
						System.out.println("Value already betted in this round: "+roundPlayer.getTotalBet());
						System.out.println("Type your bet: ");
						Double bet = sc.nextDouble();
						Double minimumBet = 0.0;
						if(roundPlayer.getRound().getCurrentBet() > hand.getCurrentBigBlind()) {
							minimumBet = roundPlayer.getRound().getCurrentBet();
						}else {
							minimumBet = hand.getCurrentBigBlind();
							if(blindEnum.equals(BlindEnum.SMALL_COMPLEMENT))
								minimumBet = hand.getCurrentBigBlind() / 2;
						}
						
						if((roundPlayer.getHandPlayer().getTablePlayer().getChips() + roundPlayer.getTotalBet()) < minimumBet) {
							if(!bet.equals(roundPlayer.getHandPlayer().getTablePlayer().getChips())) {
								System.out.println("Minimum bet (all in): "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
							}else{
								if(bet > roundPlayer.getRound().getCurrentBet())
									roundPlayer.getRound().setCurrentBet(bet);
								corretAction = true;
							}
						}
						else if((roundPlayer.getTotalBet() + bet) < minimumBet)
							System.out.println("Minimum bet: "+minimumBet);
						else if(bet > roundPlayer.getHandPlayer().getTablePlayer().getChips())
							System.out.println("Maximum bet (all in) : "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
						else if(blindEnum.equals(BlindEnum.BIG_COMPLEMENT) && bet < minimumBet)
							System.out.println("Minimum bet: "+minimumBet);
						else if(blindEnum.equals(BlindEnum.SMALL_COMPLEMENT) && bet > minimumBet && (bet - minimumBet) < hand.getCurrentBigBlind())
							System.out.println("To increase the bet, the minimum is: "+minimumBet+"(Complement) + "+hand.getCurrentBigBlind()+"(big blind)"+
											   "Total: "+minimumBet+hand.getCurrentBigBlind());
						else {
							corretAction = true;
							corretBet = true;
						}
						if(corretBet) {
							if(roundPlayer.getHandPlayer().getTablePlayer().getChips().equals(bet)) {
								roundPlayer.setAllIn(true);
								roundPlayer.getRound().setAllInValue(bet);
								System.out.println("All in by "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
								System.out.println("Total: "+ (roundPlayer.getTotalBet() + bet) + "chips");
							}
							actionPlayer = new ActionPlayer(ActionEnum.BET, bet);
							roundPlayer.setAction(actionPlayer);
							roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-bet);
							roundPlayer.setTotalBet(roundPlayer.getTotalBet()+bet);
							if(roundPlayer.getTotalBet()+bet > roundPlayer.getRound().getCurrentBet() && roundPlayer.getTotalBet()+bet > minimumBet)
								roundPlayer.getRound().setPlayerIncreasedBet(roundPlayer);
							roundPlayer.getRound().setCurrentBet(roundPlayer.getTotalBet());
						}
					}
				}else if(action.equals(ActionEnum.FOLD.getValue())){
					actionPlayer = new ActionPlayer(ActionEnum.FOLD, 0.0);
					roundPlayer.setAction(actionPlayer);
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
