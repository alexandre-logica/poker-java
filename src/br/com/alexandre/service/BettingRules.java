package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.alexandre.domain.BigAction;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.domain.SmallAction;
import br.com.alexandre.enuns.ActionEnum;
import br.com.alexandre.enuns.StatusEnum;

public class BettingRules {

	private Hand hand;
	private ShowResults showResults;
	private HandRankingRules handRankingHules;
	
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
		round.getRoundPlayers().removeIf(p -> (p.getHandPlayer().getStatus().equals(StatusEnum.OUT)));
		if(round.getRoundPlayers().size() == 1) {
			round.sethandWinner(true);
			round.getRoundPlayers().get(0).getHandPlayer().getTablePlayer().increaseChips(round.getHand().getPot());
			round.getRoundPlayers().get(0).getHandPlayer().setStatus(StatusEnum.BETING_WINNER);
			round.getHand().getWinners().add(round.getRoundPlayers().get(0).getHandPlayer());
		}else if(round.getNumber().equals(4) || round.getAllIn()) {
			round.sethandWinner(true);
			handRankingHules = new HandRankingRules();
			List<HandRanking> handRankings = new ArrayList<>();
			for(RoundPlayer player : round.getRoundPlayers()) {
				HandRanking handRanking = handRankingHules.setPlayerScore(player.getHandPlayer().getPlayerHandCards());
				handRanking.setHandPlayer(player.getHandPlayer());
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
				player.getTablePlayer().increaseChips(hand.getPot()/winners.size());
			}
		}else {
			handRankings.get(0).getHandPlayer().setHandRanking(handRankings.get(0));
			handRankings.get(0).getHandPlayer().setStatus(StatusEnum.CARD_WINNER);
			handRankings.get(0).getHandPlayer().getTablePlayer().increaseChips(hand.getPot());
			winners.add(handRankings.get(0).getHandPlayer());
		}
		return winners;
	}
	
	private List<RoundPlayer> createRoundPlayers(Round round) {
		List<RoundPlayer> roundPlayers = new ArrayList<RoundPlayer>();
		RoundPlayer roundPlayer;
		Long id = 0L;
		Integer roundPosition = 0;
		for (HandPlayer handPlayer : round.getHand().getHandPlayers()) {
			if(handPlayer.getStatus().equals(StatusEnum.IN)) {
				if(round.getNumber().equals(1)) {
					switch (handPlayer.getBlind()) {
					case SMALL:
						roundPlayer = new SmallAction(++id, round, handPlayer, round.getHand().getHandPlayers().size() - 1);
						break;
					case BIG:
						roundPlayer = new BigAction(++id, round, handPlayer, round.getHand().getHandPlayers().size());
						break;
					default:
						roundPlayer = new RoundPlayer(++id, round, handPlayer);
						if(handPlayer.getDealer())
							roundPlayer.setRoundPosition(round.getHand().getHandPlayers().size() - 2);
						else
							roundPlayer.setRoundPosition(++roundPosition);
						break;
					}
				}else {
					roundPlayer = new RoundPlayer(++id, round, handPlayer, ++roundPosition);
				}
				roundPlayers.add(roundPlayer);
			}
		}
		Collections.sort(roundPlayers);
		return roundPlayers;
	}
	
	private void checkPlayerBet(Map<Long, Double> playerMap, RoundPlayer roundPlayer) {
		if(roundPlayer.getActionEnum().equals(ActionEnum.FOLD) || roundPlayer.getSmallerAllIn())
			playerMap.remove(roundPlayer.getId());
		else
			playerMap.put(roundPlayer.getId(), roundPlayer.getTotalBet());
	}
	
	private Boolean checkSameBet(Map<Long, Double> playerMap) {
		Set<Double> bets = new HashSet<Double>();
		for(Map.Entry<Long, Double> entry : playerMap.entrySet()) {
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
		Map<Long, Double> playerMap = new HashMap<Long, Double>();
		while(!sameBet) {
			for(RoundPlayer roundPlayer : roundPlayers) {
				if(roundPlayer.getHandPlayer().getStatus().equals(StatusEnum.IN)) {
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
