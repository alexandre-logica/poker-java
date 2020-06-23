package br.com.alexandre.domain;

import java.io.Serializable;

public class Client implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	protected String nome;
	protected String genero;
	protected String nickname;
	
	public Client() {
		
	}
	
	public Client(Integer id, String nome, String genero, String nickname) {
		super();
		this.id = id;
		this.nome = nome;
		this.genero = genero;
		this.nickname = nickname;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int compareTo(Client o) {
		return this.id.compareTo(o.id);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
