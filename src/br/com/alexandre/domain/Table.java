package br.com.alexandre.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Table {

	private Long id;
	private List<TablePlayer> tablePlayers;
	private List<Hand> hands;
	private Double initialCashBet = 0.0;
	private LocalDateTime gameStart;
	private LocalDateTime gameFinish;
	private Boolean gameOver;
	
	public Table(Long id, List<TablePlayer> tablePlayers, List<Hand> hands, Double initialCashBet, LocalDateTime gameStart, LocalDateTime gameFinish, Boolean gameOver) {
		this.id = id;
		this.tablePlayers = tablePlayers;
		this.hands = hands;
		this.initialCashBet = initialCashBet;
		this.gameStart = gameStart;
		this.gameFinish = gameFinish;
		this.gameOver = gameOver;
	}
	
	public Table(Long id, Double initialCashBet, LocalDateTime gameStart) {
		this(id, new ArrayList<TablePlayer>(), new ArrayList<Hand>(), initialCashBet, gameStart, null, false);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<TablePlayer> getTablePlayers() {
		return tablePlayers;
	}

	public void setTablePlayers(List<TablePlayer> tablePlayers) {
		this.tablePlayers = tablePlayers;
	}

	public List<Hand> getHands() {
		return hands;
	}

	public void setHands(List<Hand> hands) {
		this.hands = hands;
	}

	public Double getInitialCashBet() {
		return initialCashBet;
	}

	public void setInitialCashBet(Double initialCashBet) {
		this.initialCashBet = initialCashBet;
	}

	public LocalDateTime getGameStart() {
		return gameStart;
	}

	public void setGameStart(LocalDateTime gameStart) {
		this.gameStart = gameStart;
	}

	public LocalDateTime getGameFinish() {
		return gameFinish;
	}

	public void setGameFinish(LocalDateTime gameFinish) {
		this.gameFinish = gameFinish;
	}

	public Boolean getGameOver() {
		return gameOver;
	}

	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}
	
}
