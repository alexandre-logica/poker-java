package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.enuns.ActionEnum;
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
			showCards.showTableCards(count);
			round = runBets(round);
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
		if(bets.size() == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * private void sortRoundPlayers() { for(int i = 0; i < round) }
	 */
	
	private Round runBets(Round round) {
		List<RoundPlayer> roundPlayers = createRoundPlayers(round);
		Boolean sameBet = false;
		Map<Long, Double> playerMap = new HashMap<Long, Double>();
		Integer roundIteration = 0;
		while(!sameBet) {
			++roundIteration;
			for(RoundPlayer roundPlayer : roundPlayers) {
				if(roundIteration.equals(1) && round.getNumber().equals(1)) {
					if(roundPlayers.indexOf(roundPlayer) == 0) {
						// Small
						new SmallAction(hand, roundPlayer).action();
						hand.setPot(round.getPot()+roundPlayer.getAction().getBet());
					}else if(roundPlayers.indexOf(roundPlayer) == 1){
						// Big
						new BigAction(hand, roundPlayer).action();
						hand.setPot(hand.getPot()+roundPlayer.getAction().getBet());
					}else if(roundPlayers.indexOf(roundPlayer) == roundPlayers.size()-1){
						// Last of First Round
						new FirstRoundAction(hand, roundPlayer).action();
						hand.setPot(hand.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
						// Small Complement
						new SmallComplementAction(hand, roundPlayers.get(0)).action();
						hand.setPot(hand.getPot()+roundPlayers.get(0).getAction().getBet());
						checkPlayerBet(playerMap, roundPlayers.get(0));
						// Big Complement
						new BigComplementAction(hand, roundPlayers.get(1)).action();
						hand.setPot(hand.getPot()+roundPlayers.get(1).getAction().getBet());
						checkPlayerBet(playerMap, roundPlayers.get(1));
					}else {
						// First Round
						new FirstRoundAction(hand, roundPlayer).action();
						hand.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
					}
				}else {
					if(roundIteration > 1) {
						if(roundPlayer.getRound().getPlayerIncreasedBet() != roundPlayer && roundPlayer.getRound().getPlayerIncreasedBet().getTotalBet() > roundPlayer.getTotalBet()) {
							// Other Rounds
							new RoundsAction(hand, roundPlayer).action();
							hand.setPot(round.getPot()+roundPlayer.getAction().getBet());
							checkPlayerBet(playerMap, roundPlayer);
						}
					}else {
						// Other Rounds
						new RoundsAction(hand, roundPlayer).action();
						hand.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
					}
				}
			}
			sameBet = checkSameBet(playerMap);
		}
		round.setRoundPlayers(roundPlayers);
		return round;
	}

}
