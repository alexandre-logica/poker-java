package br.com.alexandre.domain;

import java.io.Serializable;

public class Client implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String genero;
	
	public Client() {
		
	}
	
	public Client(int id, String nome, String genero) {
		super();
		this.id = id;
		this.nome = nome;
		this.genero = genero;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	
}
