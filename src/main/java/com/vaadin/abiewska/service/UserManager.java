package com.vaadin.abiewska.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vaadin.abiewska.domain.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class UserManager {
	private static String cryptPassword;

	private static String cryptPassword(String pass) {
		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			String md5 = new BigInteger(1, md.digest()).toString(16);
			return md5.toString();
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	public static User checkAuthentication(String login, String password)
			throws SQLException {
		cryptPassword = cryptPassword(password);
		String query = "select * from user where login = '" + login
				+ "' AND password = '" + cryptPassword + "'";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		User user = null;
		
		try {
			if (rs.next()) {
				user = new User();
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));

				VaadinSession session = UI.getCurrent().getSession();
				session.setAttribute("currentUser", user);

				UI.getCurrent().getNavigator().navigateTo("main");
			} else {
				System.out.println("Zly login lub haslo.");
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return user;
	}

	public static void LogoutUser() {
		UI.getCurrent().getPage().setLocation("/vaadin-biu");
		UI.getCurrent().getSession().close();
	}

	public static boolean UserExist(String login) throws SQLException {
		String query = "select * from user where login = '" + login + "'";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			System.out.println("User o takim loginie istnieje.");
			rs.close();
			pstmt.close();
			return true;
		} else {
			System.out.println("User o takim loginie nie istnieje.");
		}

		return false;
	}

	public static void registerUser(String login, String password)
			throws SQLException {
		cryptPassword = cryptPassword(password);
		String insert = "insert into user (login, password) values ( ?, ? )";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(insert);
		pstmt.setString(1, login);
		pstmt.setString(2, cryptPassword);
		pstmt.executeUpdate();
		pstmt.close();
	}

	

}
