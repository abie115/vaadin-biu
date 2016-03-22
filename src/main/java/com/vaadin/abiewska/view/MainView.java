package com.vaadin.abiewska.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.CourseManager;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		this.addComponent(new MenuPanel());
		this.setUp();
	}

	
	public void setUp() {

		setMargin(true);
		setSpacing(true);

		final HorizontalLayout hLayout = new HorizontalLayout();

		Label lblLogin = new Label();
		Button btnSearch = new Button("Wyszukaj", FontAwesome.SEARCH);
		TextField txtCourse = new TextField();
		Label labelCourse = new Label("Nazwa kursu:");
		Button btnCheck = new Button("Zaznacz");

		txtCourse.setImmediate(true);

		hLayout.addComponents(lblLogin, labelCourse, txtCourse, btnSearch);
		addComponent(hLayout);
		setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);

		BeanContainer<Integer, Course> courses = new BeanContainer<Integer, Course>(
				Course.class); 
		courses.setBeanIdProperty("id"); 

		Table coursesTable = new Table("Kursy", courses);
		coursesTable.setColumnHeader("id", "Id");
		coursesTable.setColumnHeader("name", "Nazwa");
		coursesTable.setColumnHeader("location", "Lokalizacja");
		coursesTable.setColumnHeader("description", "Szczegóły");
		coursesTable.setColumnHeader("email", "E-mail");
		coursesTable.setColumnHeader("dateBegin", "Data rozpoczęcia");
		coursesTable.setColumnHeader("dateEnd", "Data zakończenia");
		// coursesTable.setImmediate(true);
		coursesTable.setSizeFull();
		coursesTable.setSelectable(true); // zaznaczalne

		txtCourse.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = -6733373447805994139L;

			@Override
			public void focus(FocusEvent event) {

				btnSearch.setClickShortcut(KeyCode.ENTER);
			}
		});

		addComponent(coursesTable);
		List<Course> listCourse = null;
		try {
			listCourse = CourseManager.allCourse();
		} catch (SQLException ex) {
			Notification.show("Brak połączenia z bazą.",
					Notification.Type.ERROR_MESSAGE);
		}

		courses.addAll(listCourse);
		coursesTable.setPageLength(coursesTable.size());

		addComponent(btnCheck);

		btnSearch.addClickListener(e -> {
			String name = txtCourse.getValue();
			List<Course> list = null;
			try {
				list = CourseManager.getCourseByName(name);
			} catch (SQLException ex) {
				Notification.show("Brak połączenia z bazą.",
						Notification.Type.ERROR_MESSAGE);
			}

			courses.removeAllItems();
			courses.addAll(list);
			coursesTable.setPageLength(coursesTable.size());
			addComponent(btnCheck);

		});

		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");

		if (user != null) {

			lblLogin = new Label("Witaj, " + user.getLogin() + " ");

			setMargin(true);
			setSpacing(true);

		} else {
			UI.getCurrent().getNavigator().navigateTo("login");
		}

	}

}
