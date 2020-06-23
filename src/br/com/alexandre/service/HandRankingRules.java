package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.HandRanking;
import br.com.alexandre.domain.enums.LevelValueEnum;
import br.com.alexandre.domain.enums.ScoreHandEnum;
import br.com.alexandre.domain.enums.SuitsEnum;
import br.com.alexandre.util.Util;

public class HandRankingRules {
	
	private List<Card> playerHand;
	private Map<Integer, Long> rankCount;
	private Map<String, Long> suitCount;
	private List<Card> flushCards;
	private List<Card> straightCards;
	private List<Card> straightFlushCards;
	private List<Integer> straightSequence;
	private List<Integer> ranks = new ArrayList<Integer>();
	private List<String> suits = new ArrayList<String>();
	
	public HandRanking setPlayerScore(List<Card> playerHand) {
		List<HandRanking> pairs = null;
		List<HandRanking> triplets = null;
		List<HandRanking> fours = null;
		this.playerHand = playerHand;
		setPreviosValues();
		Map<String, List<HandRanking>> amountOfKinds = checkAmountOfKind();
		if (amountOfKinds != null) {
			pairs = amountOfKinds.get("pairs");
			triplets = amountOfKinds.get("triplets");
			fours = amountOfKinds.get("fours");
		}
		checkTypeOfStraightAndFlush();
		HandRanking tripletHand = null;
		HandRanking handRanking = null;

		if (straightFlushCards != null && straightFlushCards.size() >= 5) {
			handRanking = new HandRanking();
			if(straightFlushCards.get(0).getRank().equals(14)) {
				// set ROYAL_FLUSH
				handRanking.setType(ScoreHandEnum.ROYAL_FLUSH);
			}else {
				// set STRAIGHT_FLUSH
				handRanking.setType(ScoreHandEnum.STRAIGHT_FLUSH);
			}
			handRanking.setHandCards(straightFlushCards.subList(0, 5));
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		} else if (fours != null && fours.size() > 0) {
			// set FOUR_OF_KIND
			return fours.get(0);
		} else if (triplets != null && triplets.size() > 0) {
			if (triplets.size() == 2) {
				// set FULL_HOUSE
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.FULL_HOUSE);
				handRanking.setHandCards(triplets.get(0).getHandCards());
				handRanking.getHandCards().addAll(triplets.get(1).getHandCards());
				handRanking.getHandCards().remove(handRanking.getHandCards().size() - 1);
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			} else if (pairs != null && pairs.size() > 0) {
				// set FULL_HOUSE
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
				handRanking.setKickers(getKicker(handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				tripletHand = handRanking;
			}
		} 
		if (flushCards != null && flushCards.size() >= 5) {
			// set FLUSH
			handRanking = new HandRanking();
			handRanking.setType(ScoreHandEnum.FLUSH);
			handRanking.setHandCards(flushCards.subList(0, 5));
			handRanking.setKickers(handRanking.getHandCards());
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		} else if (straightCards != null && straightCards.size() >= 5) {
			// set STRAIGHT
			handRanking = new HandRanking();
			handRanking.setType(ScoreHandEnum.STRAIGHT);
			handRanking.setHandCards(straightCards.subList(0, 5));
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		} else if (tripletHand != null) {
			// return THREE_OF_KIND
			return tripletHand;
		} else if (pairs != null && pairs.size() > 0) {
			if (pairs.size() >= 2) {
				// set TWO_PAIR
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.TWO_PAIR);
				handRanking.setHandCards(pairs.get(0).getHandCards());
				handRanking.getHandCards().addAll(pairs.get(1).getHandCards());
				handRanking.setKickers(getKicker(handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			} else {
				// set ONE_PAIR
				handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.ONE_PAIR);
				handRanking.setHandCards(pairs.get(0).getHandCards());
				handRanking.setKickers(getKicker(handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				return handRanking;
			}
		} else {
			// set HIGH_CARD
			handRanking = new HandRanking();
			handRanking.setType(ScoreHandEnum.HIGH_CARD);
			handRanking.setHandCards(setHighCard());
			handRanking.setKickers(handRanking.getHandCards());
			handRanking.setValue(calcValue(handRanking));
			return handRanking;
		}
	}
	
	@SuppressWarnings("unused")
	private void testPlayerScore() {
		System.out.println("straightFlushCards: " + straightFlushCards);
		System.out.println("flushCards: " + flushCards);
		System.out.println("rankCount: " + rankCount);
		System.out.println("straightCards: " + straightCards);
		System.out.println("straightSequence: " + straightSequence);
		System.out.println("ranks: " + ranks);
		System.out.println("suits: " + suits);
	}
	
	private Map<String, List<HandRanking>> checkAmountOfKind() {
		List<HandRanking> pairs = new ArrayList<HandRanking>();
		List<HandRanking> triplets = new ArrayList<HandRanking>();
		List<HandRanking> fours = new ArrayList<HandRanking>();
		Map<String, List<HandRanking>> amountOfKinds = new HashMap<>();
		for (Map.Entry<Integer, Long> entry : rankCount.entrySet()) {
			if (entry.getValue().equals(4L)) {
				// set FOUR_OF_KIND
				HandRanking handRanking = new HandRanking();
				handRanking.setType(ScoreHandEnum.FOUR_OF_KIND);
				handRanking.setHandCards(getCardsByRank(entry.getKey()));
				handRanking.setKickers(getKicker(handRanking.getHandCards()));
				handRanking.getHandCards().addAll(handRanking.getKickers());
				handRanking.setValue(calcValue(handRanking));
				fours.add(handRanking);
				amountOfKinds.put("fours", fours);
			} else {
				if (entry.getValue().equals(3L)) {
					// set THREE_OF_KIND
					HandRanking handRanking = new HandRanking();
					handRanking.setType(ScoreHandEnum.THREE_OF_KIND);
					handRanking.setHandCards(getCardsByRank(entry.getKey()));
					triplets.add(handRanking);
					amountOfKinds.put("triplets", triplets);
				}
				if (entry.getValue().equals(2L)) {
					// set ONE_PAIR
					HandRanking handRanking = new HandRanking();
					handRanking.setType(ScoreHandEnum.ONE_PAIR);
					handRanking.setHandCards(getCardsByRank(entry.getKey()));
					pairs.add(handRanking);
					amountOfKinds.put("pairs", pairs);
				}
			}
		}
		if (amountOfKinds.size() == 0)
			amountOfKinds = null;
		return amountOfKinds;
	}
	
	private void setPreviosValues() {
		SortedSet<Integer> straightSetNumbers = new TreeSet<Integer>();
		for (Card card : playerHand) {
			ranks.add(card.getRank());
			suits.add(card.getSuit());
		}
		if (ranks.contains(14))
			ranks.add(1);
		straightSetNumbers.addAll(ranks);
		straightSequence = Util.orderedWithNoGap(straightSetNumbers);
		Collections.sort(straightSequence, Collections.reverseOrder());
		rankCount = new HashMap<Integer, Long>();
		for (Integer rank : ranks) {
			rankCount.put(rank, ranks.stream().filter(item -> item == rank).count());
		}
		rankCount = Util.sortByValueInteger(rankCount);
		suitCount = new HashMap<String, Long>();
		for (SuitsEnum suit : SuitsEnum.values()) {
			suitCount.put(suit.getValue(), suits.stream().filter(item -> item == suit.getValue()).count());
		}
		suitCount = suitCount.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
	}

	private void checkTypeOfStraightAndFlush() {
		List<Integer> ranksFlush = new ArrayList<Integer>();
		List<Integer> straightFlushSequence;
		String flush = null;
		Boolean aceIsOne = false;
		// Check flush
		if (suitCount.entrySet().iterator().next().getValue() >= 5) {
			flush = suitCount.entrySet().iterator().next().getKey();
			flushCards = new ArrayList<Card>();
			for (Card card : playerHand) {
				if (card.getSuit().equals(flush)) {
					flushCards.add(card);
					ranksFlush.add(card.getRank());
				}
			}
			Collections.sort(flushCards, Collections.reverseOrder());
		}
		// Check straightFlush and straight
		if(straightSequence.size() >= 5) {
			if(flush != null && flush != "") {
				SortedSet<Integer> straightSetNumbers = new TreeSet<Integer>();
				if (ranksFlush.contains(14))
					ranksFlush.add(1);
				straightSetNumbers.addAll(ranksFlush);
				straightFlushSequence = Util.orderedWithNoGap(straightSetNumbers);
				Collections.sort(straightFlushSequence, Collections.reverseOrder());
				if(straightFlushSequence.size() >= 5) {
					straightFlushCards = new ArrayList<Card>();
					if(straightFlushSequence.contains(1)) {
						straightFlushSequence.set(straightFlushSequence.size()-1, 14);
						aceIsOne = true;
					}
					for (Integer straightItem : straightFlushSequence) {
						for (Card card : playerHand) {
							if (card.getRank().equals(straightItem) && card.getSuit().equals(flush)) {
								straightFlushCards.add(card);
								break;
							}
						}
					}
					Collections.sort(straightFlushCards, Collections.reverseOrder());
					if(aceIsOne) {
						straightFlushCards.add(straightFlushCards.get(0));
						straightFlushCards.remove(0);
					}
				}
			}else {
				if(straightSequence.contains(1)) {
					straightSequence.set(straightSequence.size()-1, 14);
					aceIsOne = true;
				}
				straightCards = new ArrayList<Card>();
				for (Integer straightItem : straightSequence) {
					for (Card card : playerHand) {
						if (card.getRank().equals(straightItem)) {
							straightCards.add(card);
							break;
						}
					}
				}
				Collections.sort(straightCards, Collections.reverseOrder());
				if(aceIsOne) {
					straightCards.add(straightCards.get(0));
					straightCards.remove(0);
				}
			}
		}
	}

	private List<Card> getCardsByRank(Integer rank) {
		List<Card> amontCards = new ArrayList<Card>();
		for (Card card : playerHand) {
			if (card.getRank().equals(rank)) {
				amontCards.add(card);
			}
		}
		return amontCards;
	}

	private List<Card> getKicker(List<Card> handCards) {
		List<Card> kickers = new ArrayList<Card>();
		kickers.addAll(playerHand);
		if (handCards != null && handCards.size() > 0)
			kickers.removeAll(handCards);
		Collections.sort(kickers, Collections.reverseOrder());
		kickers = kickers.subList(0, (5 - handCards.size()));
		return kickers;
	}

	private List<Card> setHighCard() {
		Collections.sort(playerHand, Collections.reverseOrder());
		playerHand = playerHand.subList(0, 5);
		return playerHand;
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
		Collections.sort(kickers, Collections.reverseOrder());
		return value;
	}

}
