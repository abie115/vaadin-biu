package com.vaadin.abiewska.view;

import com.vaadin.abiewska.domain.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		this.setUp();
	}

	public void setUp() {

		this.addComponent(new MenuPanel());

		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");
		
		if (user != null) {

			Label lblLogin = new Label("Witaj, " + user.getLogin() + " ");

			final TextField name = new TextField();
			name.setCaption("Type your name here:");

			Button button = new Button("Click Me");
			button.addClickListener(e -> {
				this.addComponent(new Label("Thanks " + name.getValue()
						+ ", it works!"));
			});

			this.addComponents(lblLogin);
			setMargin(true);
			setSpacing(true);
			
		} else {
			UI.getCurrent().getNavigator().navigateTo("login");
		}

	}

}
