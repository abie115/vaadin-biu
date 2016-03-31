package com.vaadin.abiewska;

import javax.servlet.annotation.WebServlet;

import com.vaadin.abiewska.view.EnrollCourseView;
import com.vaadin.abiewska.view.LoginView;
import com.vaadin.abiewska.view.MainView;
import com.vaadin.abiewska.view.RegisterView;
import com.vaadin.abiewska.view.CreateCourseView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("mytheme")
@Widgetset("com.vaadin.abiewska.MyAppWidgetset")
@Title("App")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		Navigator navigator = new Navigator(this, this);
		navigator.addView("main", MainView.class);
		navigator.addView("register", RegisterView.class);
		navigator.addView("enrollcourse", EnrollCourseView.class);
		navigator.addView("createcourse", CreateCourseView.class);
		navigator.addView("login", LoginView.class);
		
		navigator.navigateTo("login");
	
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
	}
}
