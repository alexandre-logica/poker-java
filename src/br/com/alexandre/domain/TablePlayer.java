package br.com.alexandre.domain;

public class TablePlayer implements Comparable<TablePlayer> {

	private Table table;
	private Player player;
	private Integer position;
	private Boolean dealer;
	private Double chips;
	
	public TablePlayer(Table table, Player player, Integer position, Boolean dealer, Double chips) {
		super();
		this.table = table;
		this.player = player;
		this.position = position;
		this.dealer = dealer;
		this.chips = chips;
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

	public Boolean getDealer() {
		return dealer;
	}

	public void setDealer(Boolean dealer) {
		this.dealer = dealer;
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

	@Override
	public int compareTo(TablePlayer o) {
		return this.getPosition().compareTo(o.getPosition());
	}
	
}
