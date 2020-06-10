package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Deck;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.TablePlayer;
import br.com.alexandre.enuns.BlindEnum;
import br.com.alexandre.enuns.TypeCardEnum;

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
		table.setTablePlayers(createTablePlayers());
		while (!table.getGameOver()) {
			table.getHands().add(runHand(++count));
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
	
	private List<TablePlayer> createTablePlayers() {
		List<TablePlayer> tablePlayers = new ArrayList<TablePlayer>();
		Collections.shuffle(players);
		for (int i = 0; i < players.size(); i++) {
			tablePlayers.add(new TablePlayer(table, players.get(i), i+1, i+1, table.getInitialChipsPlayers(), false));
		}
		return tablePlayers;
	}
	
	private void goForwardTablePlayerPosition() {
		for (TablePlayer player : table.getTablePlayers()) {
			if(player.getDealerPosition().equals(1))
				player.setDealerPosition(table.getTablePlayers().size());
			else
				player.setDealerPosition(player.getDealerPosition()-1);
		}
	}
	
	private List<HandPlayer> createHandPlayers(Hand hand) {
		goForwardTablePlayerPosition();
		Collections.sort(table.getTablePlayers());
		List<HandPlayer> handPlayers = new ArrayList<HandPlayer>();
		for (TablePlayer tablePlayer : table.getTablePlayers()) {
			HandPlayer handPlayer = new HandPlayer(new Long(tablePlayer.getDealerPosition()), hand, tablePlayer);
			switch (tablePlayer.getDealerPosition()) {
				case 1:
					handPlayer.setBlind(BlindEnum.SMALL);;
					break;
				case 2:
					handPlayer.setBlind(BlindEnum.BIG);
					break;
				default:
					break;
			}
			if(tablePlayer.getDealerPosition().equals(table.getTablePlayers().size()))
				handPlayer.setBlind(BlindEnum.DEALER);;
			handPlayers.add(handPlayer);
		}
		return handPlayers;
	}
	
	private Boolean checkGameOver() {
		table.getTablePlayers().removeIf(p -> (p.getChips().equals(0.0)));
		for (int i = 0; i < table.getTablePlayers().size(); i++) {
			table.getTablePlayers().get(i).setDealerPosition(i+1);
		}
		if(table.getTablePlayers().size() == 1)
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
