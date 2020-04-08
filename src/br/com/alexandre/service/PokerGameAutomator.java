package br.com.alexandre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import br.com.alexandre.domain.ActionPlayer;
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
import br.com.alexandre.enuns.ActionEnum;
import br.com.alexandre.enuns.BlindEnum;
import br.com.alexandre.enuns.StatusEnum;
import br.com.alexandre.enuns.TypeCardEnum;

public class PokerGameAutomator {
	
	private Table table;
	private List<Player> players;
	private Deck deck;
	private List<Card> handCards;
	private HandRankingRules handRankingHules;
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
			// showCards(hand);
			table.setGameOver(true);
		}
	}
	
	private Hand runHand(Integer count) {
		deck = new Deck();
		deck.shuffle();
		Hand hand = new Hand(count, deck);
		giveOutCards(hand);
		showCards = new ShowCards(hand);
		showCards.showPlayerCards();
		runRounds(hand);
		//hand.setRounds(runBets());
		return hand;
	}
	
	private List<Round> runRounds(Hand hand) {
		List<Round> rounds = new ArrayList<>();
		Round round;
		Integer count = 0;
		Boolean winner = false;
		while(!winner) {
			round = new Round(++count, hand);
			round = runBets(round);
			winner = round.getWinner();
			rounds.add(round);
			showCards.showTableCards(count);
		}
		return rounds;
	}
	
	private void checkPlayerBet(Map<Long, Double> playerMap, RoundPlayer roundPlayer) {
		if(roundPlayer.getAction().getActionEnum().equals(ActionEnum.FOLD))
			playerMap.remove(roundPlayer.getId());
		else if(roundPlayer.getAction().getActionEnum().equals(ActionEnum.BET))
			playerMap.put(roundPlayer.getId(), roundPlayer.getAction().getBet());
	}
	
	private Boolean checkSameBet(Map<Long, Double> playerMap) {
		Set<Double> bets = new HashSet<Double>();
		for(Map.Entry<Long, Double> entry : playerMap.entrySet()) {
			bets.add(entry.getValue());
		}
		if(bets.size() == 1)
			return true;
		else
			return false;
	}
	
	private Round runBets(Round round) {
		List<RoundPlayer> roundPlayers = createRoundPlayers(round);
		Boolean sameBet = false;
		Map<Long, Double> playerMap = new HashMap<Long, Double>();
		while(!sameBet) {
			for(RoundPlayer roundPlayer : roundPlayers) {
				if(round.getNumber().equals(1)) {
					if(roundPlayers.indexOf(roundPlayer) == 0) {
						getBlindAction(BlindEnum.SMALL, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
					}else if(roundPlayers.indexOf(roundPlayer) == 1){
						getBlindAction(BlindEnum.BIG, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
					}else if(roundPlayers.indexOf(roundPlayer) == roundPlayers.size()-1){
						getBlindAction(BlindEnum.FIRST_ROUND, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
						checkPlayerBet(playerMap, roundPlayer);
						// Small Complement
						getBlindAction(BlindEnum.SMALL_COMPLEMENT, roundPlayers.get(0));
						round.setPot(round.getPot()+roundPlayers.get(0).getAction().getBet());
						checkPlayerBet(playerMap, roundPlayers.get(0));
						// Big Complement
						getBlindAction(BlindEnum.BIG_COMPLEMENT, roundPlayers.get(1));
						round.setPot(round.getPot()+roundPlayers.get(1).getAction().getBet());
						checkPlayerBet(playerMap, roundPlayers.get(1));
					}else {
						getBlindAction(BlindEnum.FIRST_ROUND, roundPlayer);
						round.setPot(round.getPot()+roundPlayer.getAction().getBet());
					}
				}else {
					getBlindAction(BlindEnum.ROUNDS, roundPlayer);
					round.setPot(round.getPot()+roundPlayer.getAction().getBet());
					checkPlayerBet(playerMap, roundPlayer);
				}
			}
			sameBet = checkSameBet(playerMap);
		}
		return round;
	}
	
	private void getBlindAction(BlindEnum blindEnum, RoundPlayer roundPlayer) {
		ActionPlayer actionPlayer = null;
		Boolean canCheck = false;
		String msg = "b for bet | f for fold";
		if(roundPlayer.getRound().getCurrentBet().equals(0.0)) {
			canCheck = true;
			msg = "c for check | b for bet | f for fold";
		}
		switch (blindEnum) {
		case SMALL:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Small blind mandatory");
			System.out.println("Value: "+table.getCurrentBigBlind()/2);
			actionPlayer = new ActionPlayer(ActionEnum.BET, table.getCurrentBigBlind()/2);
			roundPlayer.setAction(actionPlayer);
			roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-table.getCurrentBigBlind()/2);
			break;
		case SMALL_COMPLEMENT:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			System.out.println("b for bet | f for fold");
			getPlayerAction(blindEnum, roundPlayer, false);
			break;	
		case BIG:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Big blind mandatory");
			System.out.println("Value: "+table.getCurrentBigBlind());
			actionPlayer = new ActionPlayer(ActionEnum.BET, table.getCurrentBigBlind());
			roundPlayer.setAction(actionPlayer);
			roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-table.getCurrentBigBlind());
			break;
		case BIG_COMPLEMENT:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			System.out.println(msg);
			getPlayerAction(blindEnum, roundPlayer, canCheck);
			break;
		case FIRST_ROUND:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			System.out.println("b for bet | f for fold");
			getPlayerAction(blindEnum, roundPlayer, false);
			break;
		case ROUNDS:
			System.out.println();
			System.out.println("Action by: "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
			System.out.println("Type:");
			System.out.println(msg);
			getPlayerAction(blindEnum, roundPlayer, canCheck);
			break;
		default:
			break;
		}
	}
	
	private void getPlayerAction(BlindEnum blindEnum, RoundPlayer roundPlayer, Boolean canCheck) {
		Boolean corretAction = false;
		ActionPlayer actionPlayer = null;
		
		while(!corretAction) {
			try {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				System.out.println("Action: ");
				String action = sc.nextLine();
				if(action.equals(ActionEnum.BET.getValue())) {
					System.out.println("Total chips: "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
					System.out.println("Type your bet: ");
					Double bet = sc.nextDouble();
					Double minimumBet = 0.0;
					if(!roundPlayer.getRound().getCurrentBet().equals(0.0))
						minimumBet = roundPlayer.getRound().getCurrentBet();
					else
						minimumBet = table.getCurrentBigBlind();
					if(blindEnum.equals(BlindEnum.SMALL_COMPLEMENT))
						minimumBet = table.getCurrentBigBlind() / 2;
					if(roundPlayer.getHandPlayer().getTablePlayer().getChips() < minimumBet) {
						if(!bet.equals(roundPlayer.getHandPlayer().getTablePlayer().getChips())) {
							System.out.println("Minimum bet (all in): "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
						}else{
							System.out.println("All in by "+roundPlayer.getHandPlayer().getTablePlayer().getPlayer().getNickname());
							System.out.println("Total: "+ bet + "chips");
							roundPlayer.setAllIn(true);
							roundPlayer.getRound().setAllIn(true);
							roundPlayer.getRound().setAllInValue(bet);
							if(bet > roundPlayer.getRound().getCurrentBet())
								roundPlayer.getRound().setCurrentBet(bet);
							corretAction = true;
						}
					}
					else if(bet < minimumBet)
						System.out.println("Minimum bet: "+minimumBet);
					else if(bet > roundPlayer.getHandPlayer().getTablePlayer().getChips())
						System.out.println("Maximum bet (all in) : "+roundPlayer.getHandPlayer().getTablePlayer().getChips());
					else 
						corretAction = true;
					if(corretAction) {
						actionPlayer = new ActionPlayer(ActionEnum.BET, bet);
						roundPlayer.setAction(actionPlayer);
						roundPlayer.getHandPlayer().getTablePlayer().setChips(roundPlayer.getHandPlayer().getTablePlayer().getChips()-bet);
						roundPlayer.getRound().setCurrentBet(bet);
					}
				}else if(action.equals(ActionEnum.FOLD.getValue())){
					actionPlayer = new ActionPlayer(ActionEnum.FOLD, 0.0);
					roundPlayer.setAction(actionPlayer);
					roundPlayer.getHandPlayer().setStatus(StatusEnum.OUT);
					corretAction = true;
				}else if(action.equals(ActionEnum.CHECK.getValue())) {
					if(canCheck) {
						corretAction = true;
					}
				}else 
					System.out.println("Wrong value!");
			} catch (Exception e) {
				//sc.close();
				corretAction = false;
				System.out.println("PokerGameAutomator.getPlayerAction()");
				System.err.println(e);
				System.out.println(e.getMessage());
				System.out.println(e.getCause());
			} finally {
				//sc.close();
			}
		}
		
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
				roundPlayers.add(roundPlayer);
			}
		}
		return roundPlayers;
	}

	private void giveOutCards(Hand hand) {
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


	private void checkWinner() {
		int count = 0;
		List<Round> rounds = new ArrayList<Round>();
		Boolean winner = false;
		
//		for (HandCard handCard : hand.getHandCards()) {
//			handCards.add(handCard.getCard());
//		}
//		for (HandPlayer player : hand.getHandPlayers()) {
//			List<Card> playerHand = player.getCards();
//			playerHand.addAll(handCards);
//			handRankingHules = new HandRankingRules(playerHand);
//			hand.getHandRankings().add(handRankingHules.setPlayerScore());
//		}
	}

	

}
