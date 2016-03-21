package com.vaadin.abiewska;

import javax.servlet.annotation.WebServlet;

import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.view.LoginView;
import com.vaadin.abiewska.view.MainView;
import com.vaadin.abiewska.view.RegisterView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.vaadin.abiewska.MyAppWidgetset")
@Title("App")
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		Navigator navigator = new Navigator(this, this);
		navigator.addView("main", MainView.class);
		navigator.addView("login", LoginView.class); // http://localhost:8080/#!login
		navigator.addView("register", RegisterView.class);

		navigator.navigateTo("login");
	
	}

	/*
	 * public void setUp() { final VerticalLayout layout = new VerticalLayout();
	 * 
	 * final TextField name = new TextField();
	 * name.setCaption("Type your name here:");
	 * 
	 * Button button = new Button("Click Me"); button.addClickListener(e -> {
	 * layout.addComponent(new Label("Thanks " + name.getValue() +
	 * ", it works!")); });
	 * 
	 * layout.addComponents(name, button); layout.setMargin(true);
	 * layout.setSpacing(true);
	 * 
	 * setContent(layout);
	 * 
	 * }
	 */

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
