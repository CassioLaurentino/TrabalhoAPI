package br.com.upf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.entity.Coment;
import br.com.upf.entity.Product;
import br.com.upf.entity.User;
import br.com.upf.util.Conexao;


public class ComentsDAO extends Coment{
	
Conexao con = new Conexao();
	
	public Coment findComentsByUser (String usuarioId) {
		Connection conn = con.getConn();
		String consulta = "SELECT * from comentario where usuarioId = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				stm.setString(1, usuarioId);
				ResultSet rs = stm.executeQuery();
		 		if (rs.next()) {
					this.setId(rs.getString("id"));
					this.setUsuarioId(rs.getString("usuarioId"));
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
	public Coment findComentsByProduct (String  produtoId) {
		Connection conn = con.getConn();
		String consulta = "SELECT * from comentario where productId = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				stm.setString(1, produtoId);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					this.setId(rs.getString("id"));
					this.setProductId(rs.getString("produtoId"));
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
		String consulta = "SELECT * from comentario";
		List<Coment> list = new ArrayList();
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					this.setId(rs.getString("id"));
					this.setComentario(rs.getString("Comentario"));
					this.setNota(rs.getString("Nota"));
					this.setProductId(rs.getString("produtoId"));
					this.setUsuarioId(rs.getString("usuarioId"));
					list.add(this);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}

	public boolean saveComents (String comentario,String nota,String usuarioId,String productId) {
		
		Connection conn = con.getConn();

		Coment selectComent = findComentsByUser(usuarioId);
		if(selectComent != null){
			return false;
		}
		
		String insert = "insert into comentario (comentario, nota, usuarioId, productId) VALUES(?,?,?,?)";
		
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(insert);
				stm.setString(1, comentario);
				stm.setString(2, nota);
				stm.setString(3, usuarioId);
				stm.setString(4, productId);
				System.out.println(stm.toString());
				stm .executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;			
		}
	}
		return true;

}
	
	public boolean alterComents (String comentario,String nota,String usuarioId) {
		Connection conn = con.getConn();
		Coment selectComents = findComentsByUser(usuarioId);	
		
		String sql = "UPDATE comentario set nota = ? set comentario = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, nota);
				stm.setString(2, comentario);
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
		String sql = "DELETE comentario where id = ?";
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
