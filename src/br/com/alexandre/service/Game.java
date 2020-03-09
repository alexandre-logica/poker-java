package br.com.alexandre.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Deck;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.Person;
import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.TablePlayer;
import br.com.alexandre.domain.aux.GenderEnum;
import br.com.alexandre.domain.aux.RankEnum;
import br.com.alexandre.domain.aux.ScoreHandEnum;
import br.com.alexandre.domain.aux.SuitsEnum;
import br.com.alexandre.domain.aux.TypeCardEnum;
import br.com.alexandre.util.Util;

public class Game {
	public void playGame(Table table, List<Player> players) {
		Long count = 0L;
		table.setTablePlayers(setTablePlayers(table, players));
		while(!table.getGameOver()) {
			Deck deck = new Deck();
			deck.shuffle();
			Hand hand = new Hand(++count, deck);
			setCards(hand, table.getTablePlayers());
			hand.setRounds(runRounds(hand));
			table.getHands().add(hand);
			//showCards(hand);
			//table.setGameOver(true);
		}
	}
	
	public List<TablePlayer> setTablePlayers(Table table, List<Player> players){
		List<TablePlayer> tablePlayers = new ArrayList<TablePlayer>();
		for(int i = 0; i < players.size(); i++) {
			tablePlayers.add(new TablePlayer(new Long(i), table, players.get(i), i+1, false));
		}
		tablePlayers.get(new Random().nextInt(tablePlayers.size())).setDealer(true);
		return tablePlayers;
	}
	
	public void setCards(Hand hand, List<TablePlayer> tablePlayers) {
		HandCard handcard;
		HandPlayer handPlayer;
		for(int x = 0; x < tablePlayers.size(); x++){
			handPlayer = new HandPlayer(new Long(x), hand, tablePlayers.get(x));
			for(int i = 0; i < 2; i++ ){
				handPlayer.getCards().add(hand.getDeck().getFULLDECK().get(0));
				hand.getDeck().getFULLDECK().remove(0);
			}
			hand.getHandPlayers().add(handPlayer);
		}
		for(int z = 0; z < 5; z++ ) {
			handcard = new HandCard();
			handcard.setCard(hand.getDeck().getFULLDECK().get(0));
			hand.getDeck().getFULLDECK().remove(0);
			if(z < 3)
				handcard.setType(TypeCardEnum.FLOP.getValue());
			else if(z < 4)
				handcard.setType(TypeCardEnum.TURN.getValue());
			else
				handcard.setType(TypeCardEnum.RIVER.getValue());
			hand.getHandCards().add(handcard);
		}
	}
	
	public void showDeck(Deck deck) {
		System.out.println();
		System.out.println("*************** Cards ***************");
		System.out.println();
		for(Card card : deck.getFULLDECK()) {
			System.out.println(card.getCharacter()+"-"+card.getSymbol());
		}
	}
	
	public void showHandCards(Hand hand) {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		for(HandCard card : hand.getHandCards()) {
			if(card.getType().equals(TypeCardEnum.FLOP.getValue())) {
				System.out.println(card.getCard().getCharacter()+"-"+card.getCard().getSymbol());
			}
		}
		for(HandCard card : hand.getHandCards()) {
			if(card.getType().equals(TypeCardEnum.TURN.getValue())) {
				System.out.println("##### Turn #####");
				System.out.println(card.getCard().getCharacter()+"-"+card.getCard().getSymbol());
			} else if(card.getType().equals(TypeCardEnum.RIVER.getValue())) {
				System.out.println("##### River #####");
				System.out.println(card.getCard().getCharacter()+"-"+card.getCard().getSymbol());
			}
		}
	}
	
	public void showPlayerCards(Hand hand) {
		System.out.println();
		System.out.println("************** Players Cards **************");
		System.out.println();
		for(HandPlayer player : hand.getHandPlayers()) {
			System.out.println("##### Player: "+player.getTablePlayer().getPlayer().getNickname()+" #####");
			System.out.println(player.getCards().get(0).getCharacter()+"-"+player.getCards().get(0).getSymbol());
			System.out.println(player.getCards().get(1).getCharacter()+"-"+player.getCards().get(1).getSymbol());
		}
	}
	
	public void showCards(Hand hand) {
		showHandCards(hand);
		showPlayerCards(hand);
	}
	
	public List<Round> runRounds(Hand hand) {
		int count = 0;
		List<Round> rounds = new ArrayList<Round>();
		Boolean winner = false;
		List<Card> handCards = new ArrayList<>();
		for(HandCard handCard : hand.getHandCards()) {
			handCards.add(handCard.getCard());
		}
		for(HandPlayer player : hand.getHandPlayers()) {
			List<Card> playerHand = player.getCards();
			playerHand.addAll(handCards);
			applyRuleToWinners(playerHand);
		}

		return new ArrayList<Round>();
	}
	
	private void countRanksAndSuits(List<Integer> ranks, Map<String, Long> suitCount, Map<String, Long> rankCount, List<Card> playerHand) {
		List<String> suits = new ArrayList<>();
		for(Card card : playerHand) {
			ranks.add(card.getRank());
			suits.add(card.getSuit());
		}
		Collections.sort(ranks, Collections.reverseOrder());
		for(SuitsEnum suit : SuitsEnum.values()) {
			suitCount.put(suit.name(), suits.stream().filter(item -> item == suit.getValue()).count());
		}
		suitCount = Util.sortByValue(suitCount);
		for(RankEnum rank : RankEnum.values()) {
			rankCount.put(rank.name(), ranks.stream().filter(item -> item == rank.getValue()).count());
		}
		rankCount = Util.sortByValue(rankCount);
	}
	
	private Boolean checkStraights(List<Integer> ranks, List<Integer> straightSequence) {
		Set<Integer> straightNumbers = new LinkedHashSet<Integer>();
		if(ranks.contains(1))
			straightNumbers.add(14);
		straightNumbers.addAll(ranks);
		straightSequence = Util.orderedWithNoGap(straightNumbers);
		if(straightSequence.contains(14)) {
			straightSequence.remove(0);
			straightSequence.add(0, 1);
		}
		if(straightSequence.size() == 5)
			return true;
		else
			return false;
	}
	
	private HandRanking checkTypeOfStraight(List<Integer> straightSequence, List<Card> playerHand) {
		List<Card> straightCards = new ArrayList<Card>();
		Set<String> straightFlush = new HashSet<String>();
		HandRanking handRanking = new HandRanking();

		for(Integer straightItem : straightSequence) {
			for(Card card : playerHand) {
				if(card.getRank().equals(straightItem)) {
					straightCards.add(card);
					straightFlush.add(card.getSuit());
				}
			}
		}
		if(straightFlush.size() == 1) {
			if(straightCards.get(0).getRank().equals(1)) {
				handRanking.setType(ScoreHandEnum.ROYAL_FLUSH.name());
				handRanking.setValue(ScoreHandEnum.ROYAL_FLUSH.getValue());
				handRanking.setHandCards(straightCards);
			}else {
				handRanking.setType(ScoreHandEnum.STRAIGHT_FLUSH.name());
				handRanking.setValue(ScoreHandEnum.STRAIGHT_FLUSH.getValue()+new Double("0."+straightSequence.get(1)));
				handRanking.setHandCards(straightCards);
			}
		}else {
			handRanking.setType(ScoreHandEnum.STRAIGHT.name());
			handRanking.setValue(ScoreHandEnum.STRAIGHT.getValue()+new Double("0."+straightSequence.get(1)));
			handRanking.setHandCards(straightCards);
		}
		return handRanking;
	}
	
	private List<Card> getCardsByRank(Integer rank, List<Card> playerHand){
		List<Card> amontCards = new ArrayList<Card>();
		for(Card card : playerHand) {
			if(card.getRank().equals(rank)) {
				amontCards.add(card);
			}
		}
		return amontCards;
	}
	
	private HandRanking checkAmontOfKind(List<Card> playerHand, Map<String, Long> rankCount, List<Integer> ranks) {
		HandRanking handRanking = new HandRanking();
		for (Map.Entry<String, Long> entry : rankCount.entrySet()) {
			if(entry.getValue().equals(4L)) {
				handRanking.setType(ScoreHandEnum.FOUR_OF_KIND.name());
				handRanking.setValue(ScoreHandEnum.FOUR_OF_KIND.getValue());
				handRanking.setHandCards(getCardsByRank(new Integer(entry.getKey()), playerHand));
				return handRanking;
			}else if(entry.getValue().equals(3L)) {
				
			}

		}
		return new HandRanking();
	}
	
	public List<HandRanking> applyRuleToWinners(List<Card> playerHand){
		List<HandRanking> ranking = new ArrayList<>();
		List<Integer> ranks = new ArrayList<Integer>();
		Map<String, Long> suitCount = new LinkedHashMap<String, Long>();
		Map<String, Long> rankCount = new LinkedHashMap<String, Long>();
		List<Integer> straightSequence = new ArrayList<Integer>();
		/* 
		 * Apply the rules to count ranks and suits with the same value 
		 */
		countRanksAndSuits(ranks, suitCount, rankCount, playerHand);
		/*
		 * Apply the rules to check straight, straightFlush and Royal Flush
		 */
		if(checkStraights(ranks, straightSequence)) {
			ranking.add(checkTypeOfStraight(straightSequence, playerHand));
			if(!ranking.get(0).getType().equals(ScoreHandEnum.STRAIGHT.name())) {
				return ranking;
			}
		}

		return ranking;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Person p1 = new Person(1, "Alexandre", GenderEnum.MASCULINO.getValue());
		Person p2 = new Person(2, "Thales", GenderEnum.MASCULINO.getValue());
		Person p3 = new Person(3, "Julia", GenderEnum.FEMININO.getValue());
		Person p4 = new Person(4, "Italo", GenderEnum.MASCULINO.getValue());
		
		Player pl1 = new Player(1, "Xandão", p1);
		Player pl2 = new Player(2, "Thalão", p2);
		Player pl3 = new Player(3, "Ju", p3);
		Player pl4 = new Player(4, "Bonititalo", p4);
		
		List<Player> players = new ArrayList<>();
		players.add(pl1);
		players.add(pl2);
		players.add(pl3);
		players.add(pl4);
		
		Table table = new Table(1L, 20.0, LocalDateTime.now());
		
		Game game = new Game();
		game.playGame(table, players);
		
	}
}
