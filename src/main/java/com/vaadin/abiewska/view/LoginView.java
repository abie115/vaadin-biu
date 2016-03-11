package com.vaadin.abiewska.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		this.setUp();
	}

	public void setUp() {

		final TextField name = new TextField();
		name.setCaption("Logowanie test");

		this.addComponent(name);
		setMargin(true);
		setSpacing(true);

	}
}
