package br.com.alexandre.domain;

public class TablePlayer implements Comparable<TablePlayer> {

	private Table table;
	private Player player;
	private Integer position;
	private Integer dealerPosition;
	private Double chips;
	private Boolean winner;
	
	public TablePlayer(Table table, Player player, Integer position, Integer dealerPosition, Double chips, Boolean winner) {
		super();
		this.table = table;
		this.player = player;
		this.position = position;
		this.dealerPosition = dealerPosition;
		this.chips = chips;
		this.winner = winner;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	public Integer getDealerPosition() {
		return dealerPosition;
	}

	public void setDealerPosition(Integer dealerPosition) {
		this.dealerPosition = dealerPosition;
	}

	public Double getChips() {
		return chips;
	}

	public void setChips(Double chips) {
		this.chips = chips;
	}
	
	public void increaseChips(Double chips) {
		this.chips += chips;
	}
	
	public void decreaseChips(Double chips) {
		this.chips -= chips;
	}
	
	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	@Override
	public int compareTo(TablePlayer o) {
		return this.getDealerPosition().compareTo(o.getDealerPosition());
	}
	
}
