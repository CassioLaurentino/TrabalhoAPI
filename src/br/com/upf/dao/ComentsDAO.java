package br.com.upf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.entity.Coment;
import br.com.upf.util.Conexao;


public class ComentsDAO extends Coment{
	
Conexao con = new Conexao();
	
	public Coment findComentsByUser (User usuario) {
		Connection conn = con.getConn();
		String consulta = "SELECT * from coments where usuario = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				stm.setUsuario(1, usuario);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					this.setId(rs.getString("id"));
					this.setUsuario(rs.getString("usuario"));
					this.setComentario(rs.getString("Comentario"));
					this.setNota(rs.getString("Nota"));
				} else {
					return null;
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return this;
	}
	
	public List listComents() {
		Connection conn = con.getConn();
		String consulta = "SELECT * from coments";
		List<Coment> list = new ArrayList();
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					this.setId(rs.getString("id"));
					this.setComentario(rs.getString("Comentario"));
					this.setNota(rs.getString("Nota"));
					list.add(this);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}

	public boolean saveComents (String comentario,String nota,String id) {
		
		Connection conn = con.getConn();

		Product selectComent = findComentsByUser(id);
		if(selectComent != null){
			return false;
		}
		
		String insert = "insert into coments (comentario, nota) VALUES(?,?)";
		
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(insert);
				stm.setString(1, comentario);
				stm.setString(2, nota);
				System.out.println(stm.toString());
				stm .executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;			
		}
	}
		return true;

}
	
	public boolean alterComents (String comentario,String nota,User usuario) {
		Connection conn = con.getConn();
		Coment selectComents = findComentsByUser(usuario);	
		
		String sql = "UPDATE coments set nota = ? set comentario = ? where id = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, nota);
				stm.setString(2, comentario);
				stm.setString(3, selectProduct.getId());
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
	
	public boolean deleteComents (String id) {
		Connection conn = con.getConn();
		String sql = "DELETE coments where id = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, id);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
}
