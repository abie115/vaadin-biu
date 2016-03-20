package com.vaadin.abiewska.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vaadin.abiewska.domain.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class UserManager {

	public static User checkAuthentication(String login, String password)
			throws SQLException {
		String query = "select * from user where login = '" + login
				+ "' AND password = '" + password + "'";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet res = pstmt.executeQuery();
		User user = null;
		try {
			if (res.next()) {
				user = new User();
				user.setLogin(res.getString("login"));
				user.setPassword(res.getString("password"));
				System.out.println("Poprawnie zalogowano.");

				VaadinSession session = UI.getCurrent().getSession();
				session.setAttribute("currentUser", user);
				UI.getCurrent().getNavigator().navigateTo("main");
			} else {
				System.out.println("Zly login lub haslo.");
			}
			res.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return user;

	}
}
