package br.com.upf.entity;

public class User {

	private String id;
	private String nome;
	private String login;
	private String senha;
	
	public User(){}

	public User(String id, String login, String nome, String senha) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.senha = senha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
