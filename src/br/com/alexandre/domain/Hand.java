package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private long id;
	private List<Player> players = new ArrayList<>();
	private List<Round> rounds = new ArrayList<>();
	private int smallBlind;
	private int bigBlind;
	private Player dealer;
	private Player winner;
	private List<Card> cards = new ArrayList<>();
}
