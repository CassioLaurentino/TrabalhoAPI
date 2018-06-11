package br.com.upf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.entity.User;
import br.com.upf.util.Conexao;

public class UserDAO extends User{
	Conexao con = new Conexao();
	
	public User findUserByLogin (String login) {
		Connection conn = con.getConn();
		String consulta = "SELECT * from usuario where login = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				stm.setString(1, login);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					this.setId(rs.getString("id"));
					this.setLogin(rs.getString("login"));
					this.setNome(rs.getString("nome"));
					this.setSenha(rs.getString("senha"));
				} else {
					return null;
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return this;
	}
	
	public List<User> listUser () {
		Connection conn = con.getConn();
		String consulta = "SELECT * from usuario";
		List<User> list = new ArrayList<User>();
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(consulta);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					this.setId(rs.getString("id"));
					this.setLogin(rs.getString("login"));
					this.setNome(rs.getString("nome"));
					this.setSenha(rs.getString("senha"));
					User u = new User(this.getId(),this.getLogin(),this.getNome(),this.getSenha());
					list.add(u);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}

	public boolean saveUser (String login, String nome, String senha) {
		Connection conn = con.getConn();
		String sql = "INSERT INTO usuario (login,nome,senha) values (?,?,?);";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, login);
				stm.setString(2, nome);
				stm.setString(3 , senha);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
	
	public boolean alterUser (String login, String nome, String senha) {
		Connection conn = con.getConn();
		String sql = "UPDATE usuario set nome = ? set senha = ? where login = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, nome);
				stm.setString(2, senha);
				stm.setString(3, login);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
	
	public boolean deleteUser (String login) {
		Connection conn = con.getConn();
		String sql = "DELETE from usuario where login = ?";
		if (conn != null) {
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, login);
				stm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			}
		}
		return true;
	}
	
}
