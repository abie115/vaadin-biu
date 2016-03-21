package com.vaadin.abiewska.view;

import java.sql.SQLException;

import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.UserManager;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
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

		BeanItem<User> userItem = new BeanItem<User>(new User());

		Button btnLogin = new Button("Zaloguj");
		Button btnRegister = new Button("Zarejestruj się");
		TextField txtLogin = new TextField("Login");
		PasswordField pfPassword = new PasswordField("Hasło");
		txtLogin.setNullRepresentation("");
		pfPassword.setNullRepresentation("");

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

		FieldGroup binder = new FieldGroup(userItem);
		binder.setBuffered(true);

		binder.bind(txtLogin, "login");
		binder.bind(pfPassword, "password");

		txtLogin.setRequired(true);
		pfPassword.setRequired(true);

		txtLogin.addValidator(new BeanValidator(User.class, "login"));
		pfPassword.addValidator(new BeanValidator(User.class, "password"));

		txtLogin.setImmediate(true);
		pfPassword.setImmediate(true);

		btnLogin.addClickListener(e -> {
			try {
				binder.commit();
				String login = (String) binder.getField("login").getValue();
				String password = (String) binder.getField("password")
						.getValue();

				try {
					if (UserManager.checkAuthentication(login, password) != null) {
						UI.getCurrent().getNavigator().navigateTo("main");
					} else {
						Notification.show("Login lub hasło jest niepoprawne.",
								Notification.Type.WARNING_MESSAGE);
						txtLogin.setValue("");
						pfPassword.setValue("");
					}
				} catch (SQLException ex) {
					Notification.show("Brak połączenia z bazą.",
							Notification.Type.ERROR_MESSAGE);
				}
			} catch (CommitException e1) {
				Notification.show("Wprowadzono nieprawidłowe dane",
						Notification.Type.WARNING_MESSAGE);
				System.out.println("Nieprawidlowe dane");
			}
		});

		btnRegister.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("register");
		});

		setMargin(true);
		setSpacing(true);

	}
}
