package com.ifsp.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	Connection conn = null;

	private Connection conectar() {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			System.out.println("conn:" + conn);
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/livraria?user=root&password=isabelly1611");
			if (conn != null) {
				System.out.println("Conex�o realizada!");
				System.out.println("conn:" + conn);
				return conn;
			}
		} catch (SQLException e) { // esse erro ocorre quando n�o conexta ao banco
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	public Connection getConexao() {
		if(conn==null) {
			conn=conectar();
		}
		return conn;
	}

}
