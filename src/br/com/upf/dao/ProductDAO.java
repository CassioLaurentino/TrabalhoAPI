package br.com.upf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.entity.Product;
import br.com.upf.util.Conexao;

public class ProductDAO extends Product{

	Conexao con = new Conexao();
	
	public Product findProductByName (String Nome) {
		Connection conn = con.getConn();
		String consulta = "SELECT * from produto where nome = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				stm.setString(1, Nome);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					this.setId(rs.getString("id"));
					this.setNome(rs.getString("Nome"));
					this.setDescricao(rs.getString("Descricao"));
				} else {
					return null;
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return this;
	}
	
	public List listProduct() {
		Connection conn = con.getConn();
		String consulta = "SELECT * from produto";
		List<Product> list = new ArrayList();
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					Product printList = new Product();
					printList.setId(rs.getString("id"));
					printList.setNome(rs.getString("nome"));
					printList.setDescricao(rs.getString("descricao"));
					list.add(printList);					
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}

	public boolean saveProduct (String nome,String descricao) {
		
		Connection conn = con.getConn();

		Product selectProduct = findProductByName(nome);
		if(selectProduct != null){
			return false;
		}
		
		String insert = "insert into produto (NOME, DESCRICAO) VALUES(?,?)";
		
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(insert);
				stm.setString(1, nome);
				stm.setString(2, descricao);
				System.out.println(stm.toString());
				stm .executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;			
		}
	}
		return true;

}
	
	public boolean alterProduct (String nome, String descricao) {
		Connection conn = con.getConn();
		Product selectProduct = findProductByName(nome);
		
		if(selectProduct == null){
			return false;
		}
		
		Integer productId = Integer.parseInt(selectProduct.getId());
		String sql = "UPDATE produto set nome = ?, descricao = ? where id = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, nome);
				stm.setString(2, descricao);
				stm.setInt(3, productId);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
	
	public boolean deleteProduct (String nome) {
		Connection conn = con.getConn();
		
		Product selectProduct = findProductByName(nome);
		if(selectProduct == null){
			return false;
		}
		
		String sql = "DELETE from produto where nome = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, nome);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
}