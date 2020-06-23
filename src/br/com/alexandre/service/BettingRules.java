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
import br.com.alexandre.domain.enums.ActionEnum;
import br.com.alexandre.domain.enums.BlindEnum;
import br.com.alexandre.domain.enums.StatusEnum;
import br.com.alexandre.util.ShowResults;

public class BettingRules {

	private Hand hand;
	private ShowResults showResults;
	
	public BettingRules(Hand hand) {
		this.hand = hand;
	}
	
	public List<HandPlayer> runRounds() {
		Round round;
		Integer count = 0;
		Boolean handWinner = false;
		showResults = new ShowResults(hand);
		do {
			round = new Round(++count, hand);
			showResults.showTableCards(count);
			round = runBets(round);
			handWinner = checkHandWinner(round);
		} while(!handWinner);
		return round.getHand().getWinners();
	}
	
	private Boolean checkHandWinner(Round round) {
		HandRankingRules handRankingHules;
		round.getRoundPlayers().removeIf(p -> (p.getStatus().equals(StatusEnum.OUT)));
		if(round.getRoundPlayers().size() == 1) {
			round.sethandWinner(true);
			round.getRoundPlayers().get(0).increaseChips(round.getHand().getPot());
			round.getRoundPlayers().get(0).setStatus(StatusEnum.BETING_WINNER);
			hand.getWinners().add((HandPlayer)round.getRoundPlayers().get(0));
		}else if(round.getNumber().equals(4) || round.getAllIn()) {
			round.sethandWinner(true);
			List<HandRanking> handRankings = new ArrayList<>();
			for(RoundPlayer player : round.getRoundPlayers()) {
				handRankingHules = new HandRankingRules();
				HandRanking handRanking = handRankingHules.setPlayerScore(player.getPlayerHandCards());
				handRanking.setHandPlayer((HandPlayer) player);
				handRankings.add(handRanking);
			}
			round.getHand().setWinners(checkMultipleWinners(handRankings));
		}
		return round.gethandWinner();
	}
	
	public List<HandPlayer> checkMultipleWinners(List<HandRanking> handRankings){
		List<HandPlayer> winners = new ArrayList<>();
		Collections.sort(handRankings, Collections.reverseOrder());
		if(handRankings.get(0).getValue().equals(handRankings.get(1).getValue())) {
			Double value = handRankings.get(0).getValue();
			for(int i = 0; i < handRankings.size(); i++) {
				if(handRankings.get(i).getValue().equals(value)) {
					handRankings.get(i).getHandPlayer().setHandRanking(handRankings.get(i));
					handRankings.get(i).getHandPlayer().setStatus(StatusEnum.CARD_WINNER);
					winners.add(handRankings.get(i).getHandPlayer());
				}
			}
			for(HandPlayer player : winners) {
				player.increaseChips(hand.getPot()/winners.size());
			}
		}else {
			handRankings.get(0).getHandPlayer().setHandRanking(handRankings.get(0));
			handRankings.get(0).getHandPlayer().setStatus(StatusEnum.CARD_WINNER);
			handRankings.get(0).getHandPlayer().increaseChips(hand.getPot());
			winners.add(handRankings.get(0).getHandPlayer());
		}
		return winners;
	}
	
	private List<RoundPlayer> createRoundPlayers(Round round) {
		List<RoundPlayer> roundPlayers = new ArrayList<RoundPlayer>();
		Integer roundPosition = 0;
		for (HandPlayer handPlayer : round.getHand().getHandPlayers()) {
			if(handPlayer.getStatus().equals(StatusEnum.IN)) {
				RoundPlayer roundPlayer = (RoundPlayer) handPlayer;
				roundPlayer.cleanUp(round);
				if(round.getNumber().equals(1)) {
					switch (handPlayer.getBlind()) {
					case SMALL:
						roundPlayer.setRoundPosition(hand.getHandPlayers().size() - 1);
						roundPlayer.initSmall();
						break;
					case BIG:
						roundPlayer.setRoundPosition(hand.getHandPlayers().size());
						roundPlayer.initBig();
						break;
					default:
						if(handPlayer.getDealer())
							roundPlayer.setRoundPosition(hand.getHandPlayers().size() - 2);
						else
							roundPlayer.setRoundPosition(++roundPosition);
						break;
					}
				}else {
					roundPlayer.setRoundPosition(++roundPosition);
					roundPlayer.setBlind(BlindEnum.MIDDLE);
				}
				roundPlayers.add(roundPlayer);
			}
		}
		Collections.sort(roundPlayers);
		return roundPlayers;
	}
	
	private void checkPlayerBet(Map<Integer, Double> playerMap, RoundPlayer roundPlayer) {
		if(roundPlayer.getActionEnum().equals(ActionEnum.FOLD) || roundPlayer.getSmallerAllIn())
			playerMap.remove(roundPlayer.getId());
		else
			playerMap.put(roundPlayer.getId(), roundPlayer.getTotalBet());
	}
	
	private Boolean checkSameBet(Map<Integer, Double> playerMap) {
		Set<Double> bets = new HashSet<Double>();
		for(Map.Entry<Integer, Double> entry : playerMap.entrySet()) {
			bets.add(entry.getValue());
		}
		if(bets.size() <= 1) {
			return true;
		}else {
			return false;
		}
	}
	
	private Round runBets(Round round) {
		List<RoundPlayer> roundPlayers = createRoundPlayers(round);
		Boolean sameBet = false;
		Map<Integer, Double> playerMap = new HashMap<Integer, Double>();
		while(!sameBet) {
			for(RoundPlayer roundPlayer : roundPlayers) {
				if(roundPlayer.getStatus().equals(StatusEnum.IN)) {
					roundPlayer.action();
					checkPlayerBet(playerMap, roundPlayer);
				}
			}
			sameBet = checkSameBet(playerMap);
		}
		round.setRoundPlayers(roundPlayers);
		return round;
	}

}
