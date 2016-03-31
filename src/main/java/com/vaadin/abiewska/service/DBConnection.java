package com.vaadin.abiewska.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String url = "jdbc:mysql://localhost:3306/biu-vaadin";
	static String user = "root";
	static String password = "";

	public static Connection con() throws SQLException {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Blad polaczenia.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Brak bazy danych.");
			e.printStackTrace();
		}
		return con;
	}
}
