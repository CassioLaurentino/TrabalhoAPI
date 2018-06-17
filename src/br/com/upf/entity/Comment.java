package br.com.upf.entity;

public class Comment {

	private String comentario;
	private String nota;
	private String id;
	private Integer productid;
	private Integer usuarioid;

	
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(String comentario, String nota, String id, Integer productid, Integer usuarioid) {
		super();
		this.comentario = comentario;
		this.nota = nota;
		this.id = id;
		this.productid = productid;
		this.usuarioid = usuarioid;
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
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Integer getUsuarioid() {
		return usuarioid;
	}
	public void setUsuarioid(Integer usuarioid) {
		this.usuarioid = usuarioid;
	}
}
