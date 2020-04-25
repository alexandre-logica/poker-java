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
import br.com.alexandre.enuns.TypeCardEnum;

public class PokerGameAutomator {
	
	private Table table;
	private List<Player> players;
	private Deck deck;
	private List<Card> handCards;
	private BetingRules betingRules;
	private ShowCards showCards;
	
	public PokerGameAutomator(Table table, List<Player> players) {
		this.table = table;
		this.players = players;
	}
	
	public void playGame() {
		Integer count = 0;
		table.setTablePlayers(createTablePlayers());
		while (!table.getGameOver()) {
			table.getHands().add(runHand(++count));
			table.setGameOver(true);
		}
	}
	
	private Hand runHand(Integer count) {
		deck = new Deck();
		deck.shuffle();
		Hand hand = new Hand(count, deck, table.getCurrentBigBlind());
		showCards = new ShowCards(hand);
		showCards.showDeck();
		giveOutCards(hand);
		showCards.showPlayerCards();
		betingRules = new BetingRules(hand);
		hand.setWinners(betingRules.runRounds());
		showCards = new ShowCards(hand);
		showCards.showWinners();
		return hand;
	}
	
	private List<TablePlayer> createTablePlayers() {
		List<TablePlayer> tablePlayers = new ArrayList<TablePlayer>();
		Collections.shuffle(players);
		for (int i = 0; i < players.size(); i++) {
			tablePlayers.add(new TablePlayer(table, players.get(i), i, false, table.getInitialChipsPlayers()));
		}
		tablePlayers.get(tablePlayers.size()-1).setDealer(true);
		return tablePlayers;
	}
	
	private void giveOutCards(Hand hand) {
		HandCard handcard;
		HandPlayer handPlayer;
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
		for (int x = 0; x < table.getTablePlayers().size(); x++) {
			handPlayer = new HandPlayer(new Long(x), hand, table.getTablePlayers().get(x));
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
