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
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.TablePlayer;
import br.com.alexandre.enuns.TypeCardEnum;

public class PokerGameAutomator {
	
	private Table table;
	private List<Player> players;
	private Hand hand;
	private Deck deck;
	List<Card> handCards;
	HandRankingRules handRankingHules;
	ShowCards showCards;
	
	public PokerGameAutomator(Table table, List<Player> players) {
		this.table = table;
		this.players = players;
	}
	
	public void playGame() {
		Integer count = 0;
		table.setTablePlayers(createTablePlayers());
		while (!table.getGameOver()) {
			table.getHands().add(hand);
			runHand(++count);
			// showCards(hand);
			table.setGameOver(true);
		}
	}
	
	public void runHand(Integer count) {
		deck = new Deck();
		deck.shuffle();
		hand = new Hand(count, deck);
		giveOutCards();
		showCards = new ShowCards(hand);
		showCards.showPlayerCards();
		runRounds();
		//hand.setRounds(runBets());
	}
	
	public List<Round> runRounds() {
		List<Round> rounds = new ArrayList<>();
		Round round;
		Integer count = 0;
		Boolean winner = false;
		while(!winner) {
			round = runBets(++count);
			winner = round.getWinner();
			rounds.add(round);
		}
		return rounds;
	}
	
	public Round runBets(Integer count) {
		Round round = new Round(count, hand);

		
		return new round;
	}

	public List<TablePlayer> createTablePlayers() {
		List<TablePlayer> tablePlayers = new ArrayList<TablePlayer>();
		Collections.shuffle(players);
		for (int i = 0; i < players.size(); i++) {
			tablePlayers.add(new TablePlayer(table, players.get(i), i, false, table.getInitialChipsPlayers()));
		}
		tablePlayers.get(tablePlayers.size()-1).setDealer(true);
		return tablePlayers;
	}
	
	public List<TablePlayer> createRoundPlayers(Round round) {
		List<RoundPlayer> roundPlayers = new ArrayList<RoundPlayer>();
		RoundPlayer roundPlayer;
		for (int i = 0; i < players.size(); i++) {
			roundPlayer = new RoundPlayer();
			roundPlayer.setRound(round);
			roundPlayers.add(roundPlayer);
		}
		tablePlayers.get(tablePlayers.size()-1).setDealer(true);
		return tablePlayers;
	}

	public void giveOutCards() {
		HandCard handcard;
		HandPlayer handPlayer;
		handCards = new ArrayList<Card>();
		for (int x = 0; x < table.getTablePlayers().size(); x++) {
			handPlayer = new HandPlayer(new Long(x), hand, table.getTablePlayers().get(x));
			for (int i = 0; i < 2; i++) {
				handPlayer.getCards().add(hand.getDeck().getFULLDECK().get(0));
				hand.getDeck().getFULLDECK().remove(0);
			}
			hand.getHandPlayers().add(handPlayer);
		}
		for (int z = 0; z < 5; z++) {
			handcard = new HandCard();
			handcard.setCard(hand.getDeck().getFULLDECK().get(0));
			handCards.add(handcard.getCard());
			hand.getDeck().getFULLDECK().remove(0);
			if (z < 3)
				handcard.setType(TypeCardEnum.FLOP.getValue());
			else if (z < 4)
				handcard.setType(TypeCardEnum.TURN.getValue());
			else
				handcard.setType(TypeCardEnum.RIVER.getValue());
			hand.getHandCards().add(handcard);
		}
	}


	public void checkWinner() {
		int count = 0;
		List<Round> rounds = new ArrayList<Round>();
		Boolean winner = false;
		
		for (HandCard handCard : hand.getHandCards()) {
			handCards.add(handCard.getCard());
		}
		for (HandPlayer player : hand.getHandPlayers()) {
			List<Card> playerHand = player.getCards();
			playerHand.addAll(handCards);
			handRankingHules = new HandRankingRules(playerHand);
			hand.getHandRankings().add(handRankingHules.setPlayerScore());
		}
	}

	

}
