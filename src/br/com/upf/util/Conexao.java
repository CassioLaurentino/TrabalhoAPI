package br.com.upf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public Connection getConn() {
		String url = "jdbc:postgresql://localhost:5432/trabalhoFinal";
		String usuario = "postgres";
		String senha = "masterkey";
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url, usuario, senha);
			return conn;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		} catch (Exception e) {
			System.out.println("Problemas ao tentar conectar com o banco de dados: " + e);
			return null;
		}
	}
}
