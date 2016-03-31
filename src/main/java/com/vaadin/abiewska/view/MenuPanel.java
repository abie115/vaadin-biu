package com.vaadin.abiewska.view;

import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.UserManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

public class MenuPanel extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	public MenuPanel() {

		Button btnMain = new Button("Strona Główna");
		Button btnLogin = new Button("Logowanie");
		Button btnRegister = new Button("Rejestracja");
		Button btnLogout = new Button("Wyloguj");
		MenuBar menubar = new MenuBar();

		MenuItem course = menubar.addItem("Kursy", null);
		
		course.addItem("Uczestnicze", new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo("enrollcourse");

			}

		});
		course.addItem("Dodane", new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo("createcourse");

			}

		});

		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");

		if (user == null) {
			this.addComponents(btnMain, btnLogin, btnRegister);
		} else {
			this.addComponents(btnMain, menubar, btnLogout);
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
