package com.vaadin.abiewska.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		this.setUp();

	}

	public void setUp() {

		final TextField name = new TextField();
		name.setCaption("Type your name here:");

		Button button = new Button("Click Me");
		button.addClickListener(e -> {
			this.addComponent(new Label("Thanks " + name.getValue()
					+ ", it works!"));
		});

		this.addComponents(name, button);
		setMargin(true);
		setSpacing(true);

	}

}
