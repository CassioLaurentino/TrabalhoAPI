package br.com.upf.entity;

public class Coment {

	private String comentario;
	private String nota;
	private String id;
	private Product produto;
	private User usuario;
	
	public Coment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coment(String comentario, String nota, String id, Product produto, User usuario) {
		super();
		this.comentario = comentario;
		this.nota = nota;
		this.id = id;
		this.produto = produto;
		this.usuario = usuario;
	}



	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	


}
