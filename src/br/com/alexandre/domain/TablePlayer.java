package br.com.alexandre.domain;

public class TablePlayer {

	private Long id;
	private Table table;
	private Player player;
	private int position;
	private Boolean dealer;
	
	public TablePlayer(Long id, Table table, Player player, int position, Boolean dealer) {
		super();
		this.id = id;
		this.table = table;
		this.player = player;
		this.position = position;
		this.dealer = dealer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Boolean getDealer() {
		return dealer;
	}

	public void setDealer(Boolean dealer) {
		this.dealer = dealer;
	}
	
}
