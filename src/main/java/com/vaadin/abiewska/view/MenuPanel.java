package com.vaadin.abiewska.view;

import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.UserManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuPanel extends HorizontalLayout {

	public MenuPanel() {
		
		Button btnMain = new Button("Strona Główna");
		Button btnLogin = new Button("Logowanie");
		Button btnRegister = new Button("Rejestracja");
		Button btnLogout = new Button("Wyloguj");

		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");

		if (user == null) {
			this.addComponents(btnMain, btnLogin, btnRegister);
		} else {
			this.addComponents(btnMain, btnLogout);
		}

		btnLogin.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("login");
		});

		btnLogout.addClickListener(e -> {
			UserManager.LogoutUser();
		});

		btnRegister.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("register");
		});

		btnMain.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("main");
		});
	}

}
