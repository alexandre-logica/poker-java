package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Deck;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.enums.BlindEnum;
import br.com.alexandre.domain.enums.TypeCardEnum;
import br.com.alexandre.util.ShowResults;

public class PokerGameAutomator {
	
	private Table table;
	private List<Player> players;
	private Deck deck;
	private List<Card> handCards;
	private BettingRules bettingRules;
	private ShowResults showResults;
	
	public PokerGameAutomator(Table table, List<Player> players) {
		this.table = table;
		this.players = players;
	}
	
	public void playGame() {
		Integer count = 0;
		table.setPlayers(setTablePlayers());
		while (!table.getGameOver()) {
			table.getHands().add(runHand(++count));
			//table.getHands().add(runHandTestHandRanking(++count));
			table.setGameOver(checkGameOver());
		}
		showResults = new ShowResults(table.getHands().get(table.getHands().size()-1));
		showResults.showWinner(table);
	}
	
	private Hand runHand(Integer count) {
		deck = new Deck();
		deck.shuffle();
		Hand hand = new Hand(count, deck, table.getCurrentBigBlind());
		showResults = new ShowResults(hand);
		giveOutCards(hand);
		showResults.showPlayerCards();
		bettingRules = new BettingRules(hand);
		hand.setWinners(bettingRules.runRounds());
		showResults = new ShowResults(hand);
		showResults.showHandWinners();
		return hand;
	}
	
	@SuppressWarnings("unused")
	private Hand runHandTestHandRanking(Integer count) {
		deck = new Deck();
		deck.shuffle();
		Hand hand = new Hand(count, deck, table.getCurrentBigBlind());
		showResults = new ShowResults(hand);
		giveOutCards(hand);
		showResults.showPlayerCards();
		showResults.showHandCards();
		bettingRules = new BettingRules(hand);
		//hand.setWinners(bettingRules.runRounds());
		
		List<HandRanking> handRankings = new ArrayList<>();
		for(HandPlayer player : hand.getHandPlayers()) {
			//System.out.println("player: " + player.getTablePlayer().getPlayer().getNickname());
			HandRankingRules handRankingHules = new HandRankingRules();
			HandRanking handRanking = handRankingHules.setPlayerScore(player.getPlayerHandCards());
			handRanking.setHandPlayer(player);
			handRankings.add(handRanking);
		}
		hand.setWinners(bettingRules.checkMultipleWinners(handRankings));
		showResults = new ShowResults(hand);
		showResults.showHandWinners();
		System.out.println("############### Hand Amount: "+count);
		return hand;
	}
	
	private List<Player> setTablePlayers() {
		ArrayList<Player> tablePlayers = new ArrayList<Player>();
		Collections.shuffle(players);
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setTable(table);
			players.get(i).setTablePosition(i+1);
			players.get(i).setDealerPosition(i+1);
			players.get(i).setChips(table.getInitialChipsPlayers());
			players.get(i).setWinner(false);
			tablePlayers.add(players.get(i));
		}
		return tablePlayers;
	}
	
	private void goForwardTablePlayerPosition() {
		for (Player player : table.getPlayers()) {
			if(player.getDealerPosition().equals(1))
				player.setDealerPosition(table.getPlayers().size());
			else
				player.setDealerPosition(player.getDealerPosition()-1);
		}
	}
	
	private List<HandPlayer> createHandPlayers(Hand hand) {
		goForwardTablePlayerPosition();
		Collections.sort(table.getPlayers(), Player.dealerPositionCompare);
		List<HandPlayer> handPlayers = new ArrayList<HandPlayer>();
		for (Player tablePlayer : table.getPlayers()) {
			HandPlayer handPlayer = (HandPlayer) tablePlayer;
			handPlayer.cleanUp(hand);
			switch (tablePlayer.getDealerPosition()) {
				case 1:
					handPlayer.setBlind(BlindEnum.SMALL);;
					break;
				case 2:
					handPlayer.setBlind(BlindEnum.BIG);
					break;
				case 3:
					handPlayer.setBlind(BlindEnum.UNDER_THE_GUN);
					break;
				default:
					handPlayer.setBlind(BlindEnum.MIDDLE);
					break;
			}
			if(tablePlayer.getDealerPosition().equals(table.getPlayers().size()))
				handPlayer.setDealer(true);
			handPlayers.add(handPlayer);
		}
		return handPlayers;
	}
	
	private Boolean checkGameOver() {
		table.getPlayers().removeIf(p -> (p.getChips().equals(0.0)));
		for (int i = 0; i < table.getPlayers().size(); i++) {
			table.getPlayers().get(i).setDealerPosition(i+1);
		}
		if(table.getPlayers().size() == 1)
			return true;
		else
			return false;
	}
	
	private void giveOutCards(Hand hand) {
		HandCard handcard;
		List<HandPlayer> handPlayers = createHandPlayers(hand);
		handCards = new ArrayList<Card>();
		for (int z = 0; z < 5; z++) {
			handcard = new HandCard();
			handcard.setCard(hand.getDeck().getFULLDECK().get(0));
			hand.getDeck().getFULLDECK().remove(0);
			if (z < 3)
				handcard.setType(TypeCardEnum.FLOP.getValue());
			else if (z < 4)
				handcard.setType(TypeCardEnum.TURN.getValue());
			else
				handcard.setType(TypeCardEnum.RIVER.getValue());
			handCards.add(handcard.getCard());
			hand.getHandCards().add(handcard);
		}
		for (HandPlayer handPlayer : handPlayers) {
			for (int i = 0; i < 2; i++) {
				handPlayer.getCards().add(hand.getDeck().getFULLDECK().get(0));
				hand.getDeck().getFULLDECK().remove(0);
			}
			handPlayer.getPlayerHandCards().addAll(handPlayer.getCards());
			handPlayer.getPlayerHandCards().addAll(handCards);
			hand.getHandPlayers().add(handPlayer);
		}
	}

}
