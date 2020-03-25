package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Deck;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.TablePlayer;
import br.com.alexandre.domain.aux.LevelValueEnum;
import br.com.alexandre.domain.aux.ScoreHandEnum;
import br.com.alexandre.domain.aux.SuitsEnum;
import br.com.alexandre.domain.aux.TypeCardEnum;
import br.com.alexandre.util.Util;

public class PokerGameAutomator {
	
	private Table table;
	private List<Player> players;
	private Hand hand;
	private Deck deck;
	
	public PokerGameAutomator(Table table, List<Player> players) {
		this.table = table;
		this.players = players;
	}
	
	public void playGame() {
		Long count = 0L;
		table.setTablePlayers(createTablePlayers());
		while (!table.getGameOver()) {
			deck = new Deck();
			deck.shuffle();
			hand = new Hand(++count, deck);
			giveOutCards();
			hand.setRounds(runRounds());
			table.getHands().add(hand);
			// showCards(hand);
			// table.setGameOver(true);
		}
	}

	public List<TablePlayer> createTablePlayers() {
		List<TablePlayer> tablePlayers = new ArrayList<TablePlayer>();
		for (int i = 0; i < players.size(); i++) {
			tablePlayers.add(new TablePlayer(new Long(i), table, players.get(i), i + 1, false));
		}
		tablePlayers.get(new Random().nextInt(tablePlayers.size())).setDealer(true);
		return tablePlayers;
	}

	public void giveOutCards() {
		HandCard handcard;
		HandPlayer handPlayer;
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

	public void showDeck() {
		System.out.println();
		System.out.println("*************** Cards ***************");
		System.out.println();
		for (Card card : deck.getFULLDECK()) {
			System.out.println(card.getCharacter() + "-" + card.getSymbol());
		}
	}

	public void showHandCards() {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.FLOP.getValue())) {
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
		}
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.TURN.getValue())) {
				System.out.println("##### Turn #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			} else if (card.getType().equals(TypeCardEnum.RIVER.getValue())) {
				System.out.println("##### River #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
		}
	}

	public void showPlayerCards() {
		System.out.println();
		System.out.println("************** Players Cards **************");
		System.out.println();
		for (HandPlayer player : hand.getHandPlayers()) {
			System.out.println("##### Player: " + player.getTablePlayer().getPlayer().getNickname() + " #####");
			System.out.println(player.getCards().get(0).getCharacter() + "-" + player.getCards().get(0).getSymbol());
			System.out.println(player.getCards().get(1).getCharacter() + "-" + player.getCards().get(1).getSymbol());
		}
	}

	public void showCards() {
		showHandCards();
		showPlayerCards();
	}

	public List<Round> runRounds() {
		int count = 0;
		List<Round> rounds = new ArrayList<Round>();
		Boolean winner = false;
		List<Card> handCards = new ArrayList<>();
		for (HandCard handCard : hand.getHandCards()) {
			handCards.add(handCard.getCard());
		}
		for (HandPlayer player : hand.getHandPlayers()) {
			List<Card> playerHand = player.getCards();
			playerHand.addAll(handCards);
			player.setHandRanking(setPlayerScore(playerHand));
		}

		return new ArrayList<Round>();
	}

	private Map<Integer, Long> countRanks(List<Card> playerHand) {
		List<Integer> ranks = setRanks(playerHand);
		Map<Integer, Long> rankCount = new HashMap<Integer, Long>();
		Collections.sort(ranks, Collections.reverseOrder());
		for (Integer rank : ranks) {
			rankCount.put(rank, ranks.stream().filter(item -> item == rank).count());
		}
		// sort by value
		rankCount = Util.sortByValueInteger(rankCount);
		return rankCount;
	}

	private List<Integer> setRanks(List<Card> playerHand) {
		List<Integer> ranks = new ArrayList<Integer>();
		for (Card card : playerHand) {
			ranks.add(card.getRank());
		}
		return ranks;
	}

	private HandRanking checkFlush(List<Card> playerHand) {
		HandRanking handRanking = null;
		String flush = "";
		List<Card> flushCards = new ArrayList<Card>();
		Map<String, Long> suitCount = countSuits(playerHand);
		if (suitCount.entrySet().iterator().next().getValue() >= 5) {
			handRanking = new HandRanking();
			flush = suitCount.entrySet().iterator().next().getKey();
			for (Card card : playerHand) {
				if (card.getSuit().equals(flush)) {
					flushCards.add(card);
				}
			}
			Collections.sort(flushCards, Collections.reverseOrder());
			handRanking.setType(ScoreHandEnum.FLUSH);
			handRanking.setHandCards(flushCards.subList(0, 5));
		}

		return handRanking;
	}

	private Map<String, Long> countSuits(List<Card> playerHand) {
		List<String> suits = setSuits(playerHand);
		Map<String, Long> suitCount = new HashMap<String, Long>();
		Collections.sort(suits, Collections.reverseOrder());
		for (SuitsEnum suit : SuitsEnum.values()) {
			suitCount.put(suit.getValue(), suits.stream().filter(item -> item == suit.getValue()).count());
		}
		// sort by value
		suitCount = suitCount.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
		return suitCount;
	}

	private List<String> setSuits(List<Card> playerHand) {
		List<String> suits = new ArrayList<String>();
		for (Card card : playerHand) {
			suits.add(card.getSuit());
		}
		return suits;
	}

	private List<Integer> checkStraights(List<Integer> ranks) {
		SortedSet<Integer> straightNumbers = new TreeSet<Integer>();
		List<Integer> straightSequence = new ArrayList<Integer>();
		if (ranks.contains(14))
			straightNumbers.add(1);
		straightNumbers.addAll(ranks);
		straightSequence = Util.orderedWithNoGap(straightNumbers);
//		if (straightSequence.contains(14)) {
//			straightSequence.remove(0);
//			straightSequence.add(0, 1);
//		}
		return straightSequence;
	}

	private HandRanking checkTypeOfStraight(List<Card> playerHand) {
		List<Integer> straightSequence = checkStraights(setRanks(playerHand));
		Set<Card> straightCards = new TreeSet<Card>();
		List<Card> straightCardsList = new ArrayList<Card>();
		Set<String> straightFlush = new HashSet<String>();
		HandRanking handRanking = new HandRanking();
		Boolean aceIsOne = false;
		if (straightSequence.size() == 5) {
			if(straightSequence.contains(1)) {
				straightSequence.set(4, 14);
				aceIsOne = true;
			}
			for (Integer straightItem : straightSequence) {
				for (Card card : playerHand) {
					if (card.getRank().equals(straightItem)) {
						straightCards.add(card);
						straightFlush.add(card.getSuit());
					}
				}
			}
			straightCardsList.addAll(straightCards);
			Collections.sort(straightCardsList, Collections.reverseOrder());
			if(aceIsOne) {
				straightCardsList.add(straightCardsList.get(0));
				straightCardsList.remove(0);
			}
			if (straightFlush.size() == 1) {
				if (straightCardsList.get(0).getRank().equals(14))
					handRanking.setType(ScoreHandEnum.ROYAL_FLUSH);
				else 
					handRanking.setType(ScoreHandEnum.STRAIGHT_FLUSH);
				handRanking.setHandCards(straightCardsList);
			} else {
				handRanking.setType(ScoreHandEnum.STRAIGHT);
				handRanking.setHandCards(straightCardsList);
			}
		} else {
			handRanking = null;
		}
		return handRanking;
	}

	private List<Card> getCardsByRank(Integer rank, List<Card> playerHand) {
		List<Card> amontCards = new ArrayList<Card>();
//		if (rank.equals(14))
//			rank = 1;
		for (Card card : playerHand) {
			if (card.getRank().equals(rank)) {
				amontCards.add(card);
			}
		}
		return amontCards;
	}

	private List<Card> getKicker(List<Card> playerHand, List<Card> handCards) {
		List<Card> kickers = new ArrayList<Card>();
		kickers.addAll(playerHand);
		if (handCards != null && handCards.size() > 0)
			kickers.removeAll(handCards);
		Collections.sort(kickers, Collections.reverseOrder());
		kickers = kickers.subList(0, (5 - handCards.size()));
		return kickers;
	}

	private List<Card> setHighCard(List<Card> playerHand) {
		Collections.sort(playerHand, Collections.reverseOrder());
		playerHand = playerHand.subList(0, 5);
		return playerHand;
	}

	private Map<String, List<HandRanking>> checkAmountOfKind(List<Card> playerHand) {
		List<HandRanking> pairs = new ArrayList<HandRanking>();
		List<HandRanking> triplets = new ArrayList<HandRanking>();
		List<HandRanking> fours = new ArrayList<HandRanking>();
		Map<Integer, Long> rankCount = countRanks(playerHand);
		Map<String, List<HandRanking>> amountOfKinds = new HashMap<>();
		for (Map.Entry<Integer, Long> entry : rankCount.entrySet()) {
			if (entry.getValue().equals(4L)) {
				// set FOUR_OF_KIND
				HandRanking handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.FOUR_OF_KIND);
				handRanking.setHandCards(getCardsByRank(entry.getKey(), playerHand));
				handRanking.setKickers(getKicker(playerHand, handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				fours.add(handRanking);
				amountOfKinds.put("fours", fours);
			} else {
				if (entry.getValue().equals(3L)) {
					// set THREE_OF_KIND
					HandRanking handRanking = new HandRanking();
					handRanking.setType(ScoreHandEnum.THREE_OF_KIND);
					handRanking.setHandCards(getCardsByRank(entry.getKey(), playerHand));
					triplets.add(handRanking);
					amountOfKinds.put("triplets", triplets);
				}
				if (entry.getValue().equals(2L)) {
					// set ONE_PAIR
					HandRanking handRanking = new HandRanking();
					handRanking.setType(ScoreHandEnum.ONE_PAIR);
					handRanking.setHandCards(getCardsByRank(entry.getKey(), playerHand));
					pairs.add(handRanking);
					amountOfKinds.put("pairs", pairs);
				}
			}
		}
		if (amountOfKinds.size() == 0)
			amountOfKinds = null;
		return amountOfKinds;
	}

	private HandRanking setPlayerScore(List<Card> playerHand) {
		List<HandRanking> pairs = null;
		List<HandRanking> triplets = null;
		List<HandRanking> fours = null;
		Map<String, List<HandRanking>> amountOfKinds = checkAmountOfKind(playerHand);
		if (amountOfKinds != null) {
			pairs = amountOfKinds.get("pairs");
			triplets = amountOfKinds.get("triplets");
			fours = amountOfKinds.get("fours");
		}
		HandRanking straightHand = checkTypeOfStraight(playerHand);
		HandRanking flushHand = checkFlush(playerHand);
		HandRanking tripletHand = null;
		HandRanking handRanking = null;

		if (straightHand != null && straightHand.getType().equals(ScoreHandEnum.ROYAL_FLUSH)) {
			// set ROYAL_FLUSH
			handRanking = straightHand;
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		} else if (straightHand != null && straightHand.getType().equals(ScoreHandEnum.STRAIGHT_FLUSH)) {
			// set STRAIGHT_FLUSH
			handRanking = straightHand;
			//handRanking.setKickers(handRanking.getHandCards());
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		} else if (fours != null && fours.size() > 0) {
			// set FOUR_OF_KIND
			return fours.get(0);
		} else if (triplets != null && triplets.size() > 0) {
			if (triplets.size() == 2) {
				// set FULL_HOUSE
				//Collections.sort(triplets, Collections.reverseOrder());
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.FULL_HOUSE);
				handRanking.setHandCards(triplets.get(0).getHandCards());
				handRanking.getHandCards().addAll(triplets.get(1).getHandCards());
				handRanking.getHandCards().remove(handRanking.getHandCards().size() - 1);
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			} else if (pairs != null && pairs.size() > 0) {
				// set FULL_HOUSE
				//Collections.sort(triplets, Collections.reverseOrder());
				//Collections.sort(pairs, Collections.reverseOrder());
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.FULL_HOUSE);
				handRanking.setHandCards(triplets.get(0).getHandCards());
				handRanking.getHandCards().addAll(pairs.get(0).getHandCards());
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			} else {
				// set THREE_OF_KIND
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.THREE_OF_KIND);
				handRanking.setHandCards(triplets.get(0).getHandCards());
				handRanking.setKickers(getKicker(playerHand, handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				tripletHand = handRanking;
			}
		} else if (flushHand != null) {
			// set FLUSH
			handRanking = flushHand;
			handRanking.setKickers(handRanking.getHandCards());
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		} else if (straightHand != null && straightHand.getType().equals(ScoreHandEnum.STRAIGHT)) {
			// set STRAIGHT
			handRanking = straightHand;
			//handRanking.setKickers(handRanking.getHandCards());
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		}
		if (tripletHand != null) {
			// return THREE_OF_KIND
			return tripletHand;
		} else if (pairs != null && pairs.size() > 0) {
			if (pairs.size() >= 2) {
				// set TWO_PAIR
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.TWO_PAIR);
				handRanking.setHandCards(pairs.get(0).getHandCards());
				handRanking.getHandCards().addAll(pairs.get(1).getHandCards());
				handRanking.setKickers(getKicker(playerHand, handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			} else {
				// set ONE_PAIR
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.ONE_PAIR);
				handRanking.setHandCards(pairs.get(0).getHandCards());
				handRanking.setKickers(getKicker(playerHand, handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			}
		} else {
			// set HIGH_CARD
			handRanking = new HandRanking();
			handRanking.setType(ScoreHandEnum.HIGH_CARD);
			handRanking.setHandCards(setHighCard(playerHand));
			handRanking.setKickers(handRanking.getHandCards());
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		}
	}

	private Double setValueLevels(HandRanking handRanking) {
		Double value = 0.0;
		List<Card> handCards = handRanking.getHandCards();
		ScoreHandEnum type = handRanking.getType();

		switch (type) {
		case ROYAL_FLUSH:
			value = ScoreHandEnum.ROYAL_FLUSH.getValue();
			break;
		case STRAIGHT_FLUSH:
			value = ScoreHandEnum.STRAIGHT_FLUSH.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			break;
		case FOUR_OF_KIND:
			value = ScoreHandEnum.FOUR_OF_KIND.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			break;
		case FULL_HOUSE:
			value = ScoreHandEnum.FULL_HOUSE.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			value += (double) handCards.get(3).getRank() * LevelValueEnum.LEVEL_TWO.getValue();
			break;
		case FLUSH:
			value = ScoreHandEnum.FLUSH.getValue();
			break;
		case STRAIGHT:
			value = ScoreHandEnum.STRAIGHT.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			break;
		case THREE_OF_KIND:
			value = ScoreHandEnum.THREE_OF_KIND.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			break;
		case TWO_PAIR:
			value = ScoreHandEnum.TWO_PAIR.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			value += (double) handCards.get(2).getRank() * LevelValueEnum.LEVEL_TWO.getValue();
			break;
		case ONE_PAIR:
			value = ScoreHandEnum.ONE_PAIR.getValue();
			value += (double) handCards.get(0).getRank() * LevelValueEnum.LEVEL_ONE.getValue();
			break;
		case HIGH_CARD:
			value = ScoreHandEnum.HIGH_CARD.getValue();
			break;

		default:
			break;
		}
		return value;
	}

	private Double calcValue(HandRanking handRanking) {
		Double value = 0.0;
		value = setValueLevels(handRanking);
		if (handRanking.getKickers() != null && handRanking.getKickers().size() > 0)
			value += setValueKickers(handRanking.getKickers());
		return value;
	}

	private Double setValueKickers(List<Card> kickers) {
		Double value = 0.0;
		Integer level = LevelValueEnum.LEVEL_FIVE.getValue();
		Collections.sort(kickers);
		for (Card card : kickers) {
			value += (double) (card.getRank() * level);
			level *= 10;
		}
		return value;
	}

}
