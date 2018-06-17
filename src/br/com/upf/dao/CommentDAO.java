package br.com.upf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.entity.Comment;
import br.com.upf.entity.Product;
import br.com.upf.util.Conexao;

public class CommentDAO extends Comment{
	
Conexao con = new Conexao();

//public Comment findCommentUserProd (Integer productid) {
//	Connection conn = con.getConn();
//	String consulta = "SELECT * from comments inner join produto on comments.produtoid = produto.id where produto.id = ?";
//	if (conn != null) {
//		try {
//			PreparedStatement stm = conn.prepareStatement(consulta);
//			stm.setInt(1, productid);
//			ResultSet rs = stm.executeQuery();
//	 		if (rs.next()) {
//				this.setId(rs.getString("id"));
//				this.setComentario(rs.getString("Comentario"));
//				this.setNota(rs.getString("nota"));
//				this.setProductid(rs.getInt("productid"));
//			} else {
//				return null;
//			}
//		} catch (SQLException e) {
//			System.out.println(e);
//		}
//	}
//	return this;
//}	
//public boolean saveComent1(String comentario,String nota,Integer productid) {
//
//Connection conn = con.getConn();
//
//Comment selectComent = findCommentUserProd(productid);
//
//if(selectComent != null){
//	return false;
//}
//
//String insert = "insert into comments (comentario, nota, produtoid) VALUES(?,?,?)";
//
//if (conn != null) {
//	try {
//		PreparedStatement stm = conn.prepareStatement(insert);
//		stm.setString(1, comentario);
//		stm.setString(2, nota);
//		stm.setInt(3, productid);
//		System.out.println(stm.toString());
//		stm .executeUpdate();
//	} catch (SQLException e) {
//		System.out.println(e);
//		return false;			
//}
//}
//return true;
//
//} 
//public boolean alterComent (String comentario,String nota, Integer productid) {
//	Connection conn = con.getConn();
//	Comment selectComents = findCommentUserProd(productid);	
//	
//	String sql = "UPDATE comments set nota = ?, comentario = ? where produtoid = ?";
//	if (conn != null) {
//		try {
//			PreparedStatement stm = conn.prepareStatement(sql);
//			stm.setString(1, nota);
//			stm.setString(2, comentario);
//			stm.setInt(3, productid);
//			stm.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println(e);
//			return false;
//		}
//	}
//	return true;
//}
public Comment findCommentUserProd (Integer productid, Integer usuarioid ) {
	Connection conn = con.getConn();
	
	String consulta = "SELECT * from comment inner join produto on comment.produtoid = produto.id " + 
	"inner join usuario on comment.usuarioid = usuario.id where produto.id = ? and usuarioid = ?";
	if (conn != null) {
		try {
			PreparedStatement stm = conn.prepareStatement(consulta);
			stm.setInt(1, productid);
			stm.setInt(2, usuarioid);
			ResultSet rs = stm.executeQuery();
	 		if (rs.next()) {
				this.setId(rs.getString("id"));
				this.setComentario(rs.getString("Comentario"));
				this.setNota(rs.getString("nota"));
				this.setProductid(rs.getInt("productid"));
				this.setUsuarioid(rs.getInt("usuarioid"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	return this;
}	
	
	public List<Comment> listComents() {
		Connection conn = con.getConn();
		String consulta = "SELECT * from comment";
		List<Comment> list = new ArrayList<Comment>();
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					this.setId(rs.getString("id"));
					this.setComentario(rs.getString("Comentario"));
					this.setNota(rs.getString("Nota"));
					this.setProductid(rs.getInt("productid"));
					this.setUsuarioid(rs.getInt("usuarioid"));
					
					list.add(this);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}

	
	
public boolean saveComent1(String comentario,String nota,Integer productid, Integer usuarioid) {
		
		Connection conn = con.getConn();

		Comment selectComent = findCommentUserProd(productid, usuarioid);
		if(selectComent == null){
			return false;
		}
		
		String insert = "insert into comment (comentario, nota, produtoid, usuarioid) VALUES(?,?,?,?)";
		
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(insert);
				stm.setString(1, comentario);
				stm.setString(2, nota);
				stm.setInt(3, productid);
				stm.setInt(4, usuarioid);
				System.out.println(stm.toString());
				stm .executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;			
		}
	}
		return true;

}


	public boolean alterComent (String comentario,String nota, Integer productid, Integer usuarioid) {
		Connection conn = con.getConn();
		Comment selectComent = findCommentUserProd(productid, usuarioid);
		if(selectComent == null){
			return false;
		}
		
		String sql = "UPDATE comment set nota = ?, comentario = ? where produtoid = ? and usuarioid = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, nota);
				stm.setString(2, comentario);
				stm.setInt(3, productid);
				stm.setInt(4, usuarioid);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
	
	public boolean deleteComents (Integer productid, Integer usuarioid) {
		Connection conn = con.getConn();
		
		Comment selectComent = findCommentUserProd(productid, usuarioid);
		if(selectComent == null){
			return false;
		}
		
		String sql = "DELETE from comment where produtoid = ? and usuarioid = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setInt(1, productid);
				stm.setInt(2, usuarioid);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
		
	}
}
	

