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

public class RegisterView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {

		this.addComponent(new MenuPanel());
		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");
		if (user == null) {
			this.setUp();
		} else {
			UI.getCurrent().getNavigator().navigateTo("main");
		}
	}

	public void setUp() {

		BeanItem<User> userItem = new BeanItem<User>(new User());

		Button btnRegister = new Button("Zarejestruj się");
		TextField txtLogin = new TextField("Login");
		PasswordField pfPassword = new PasswordField("Hasło");
		PasswordField pfPassword2 = new PasswordField("Powtórz hasło");
		txtLogin.setNullRepresentation("");
		pfPassword.setNullRepresentation("");
		pfPassword2.setNullRepresentation("");

		Panel panel = new Panel("Rejestracja");
		panel.setSizeUndefined();
		this.addComponent(panel);

		FormLayout formLogin = new FormLayout();
		formLogin.addStyleName("login-form");
		formLogin.addComponent(txtLogin);
		formLogin.addComponent(pfPassword);
		formLogin.addComponent(pfPassword2);
		formLogin.addComponent(btnRegister);
		formLogin.setMargin(true);
		formLogin.setSpacing(true);
		panel.setContent(formLogin);

		FieldGroup binder = new FieldGroup(userItem);
		binder.setBuffered(true);

		binder.bind(txtLogin, "login");
		binder.bind(pfPassword, "password");

		// txtLogin.setRequired(true);
		// pfPassword.setRequired(true);
		// pfPassword2.setRequired(true);

		txtLogin.addValidator(new BeanValidator(User.class, "login"));
		pfPassword.addValidator(new BeanValidator(User.class, "password"));
		pfPassword2.addValidator(new BeanValidator(User.class, "password"));

		txtLogin.setImmediate(true);
		pfPassword.setImmediate(true);
		pfPassword2.setImmediate(true);

		btnRegister
				.addClickListener(e -> {
					try {

						binder.commit();
						String login = txtLogin.getValue();
						String password = pfPassword.getValue();
						String password2 = pfPassword2.getValue();

						try {
							if (!UserManager.UserExist(login)) {
								if (password.equals(password2)) {
									UserManager.registerUser(login, password);
									Notification
											.show("Rejestracja przebiegła pomyślnie, możesz się zalogować.",
													Notification.Type.HUMANIZED_MESSAGE);
									UI.getCurrent().getNavigator()
											.navigateTo("login");
								} else {
									Notification.show(
											"Hasła muszą być takie same.",
											Notification.Type.WARNING_MESSAGE);
								}
							} else {
								Notification.show(
										"Taki użytkownik już istnieje.",
										Notification.Type.WARNING_MESSAGE);
								txtLogin.setValue("");
								pfPassword.setValue("");
								pfPassword2.setValue("");
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

		setMargin(true);
		setSpacing(true);

	}
}
