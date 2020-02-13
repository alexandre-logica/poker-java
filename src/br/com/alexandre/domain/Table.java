package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private long id;
	private List<Player> players = new ArrayList<>();
	private List<Hand> hands = new ArrayList<>();
	private Double initialCashBet = 0.0;
	
}
