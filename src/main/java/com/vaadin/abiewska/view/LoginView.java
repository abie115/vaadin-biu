package com.vaadin.abiewska.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		this.setUp();
	}

	public void setUp() {

		Button btnLogin = new Button("Zaloguj");
		Button btnRegister = new Button("Zarejestruj się");
		TextField txtLogin = new TextField("Login");
		PasswordField pfPassword = new PasswordField("Hasło");

		Panel panel = new Panel("Logowanie");
		panel.setSizeUndefined();// dopasowuje do zawartosci
		this.addComponent(panel);

		FormLayout formLogin = new FormLayout();
		formLogin.addStyleName("login-form");
		formLogin.addComponent(txtLogin);
		formLogin.addComponent(pfPassword);
		formLogin.addComponent(btnLogin);
		formLogin.addComponent(btnRegister);
		formLogin.setMargin(true);
		formLogin.setSpacing(true);
		panel.setContent(formLogin);

		btnLogin.addClickListener(e -> {
			String login = txtLogin.getValue();
			String password = pfPassword.getValue();
			UI.getCurrent().getNavigator().navigateTo("main");
		});

		setMargin(true);
		setSpacing(true);

	}
}
