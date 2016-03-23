package com.vaadin.abiewska.view;

import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.CourseManager;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {
	private Course courseSelect = null;

	@Override
	public void enter(ViewChangeEvent event) {
		this.addComponent(new MenuPanel());
		this.setUp();
	}

	public void setUp() {

		setMargin(true);
		setSpacing(true);

		HorizontalLayout hLayout = new HorizontalLayout();
		HorizontalLayout hButton = new HorizontalLayout();

		Label lblLogin = new Label();
		Button btnSearch = new Button("Wyszukaj", FontAwesome.SEARCH);
		TextField txtCourse = new TextField();
		Label labelCourse = new Label("Nazwa kursu:");
		Button btnSelect = new Button("Zapisz się na kurs");
		Button btnAddCourse = new Button("Dodaj kurs");

		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");

		if (user != null) {
			lblLogin = new Label("Witaj, " + user.getLogin() + " ");

		} else {
			UI.getCurrent().getNavigator().navigateTo("login");
		}

		BeanContainer<Integer, Course> courses = new BeanContainer<Integer, Course>(
				Course.class);

		courses.setBeanIdProperty("id");

		Table coursesTable = new Table("Kursy", courses);
		coursesTable.setColumnHeader("id", "Id");
		coursesTable.setColumnHeader("login", "Login");
		coursesTable.setColumnHeader("name", "Nazwa");
		coursesTable.setColumnHeader("location", "Lokalizacja");
		coursesTable.setColumnHeader("description", "Szczegóły");
		coursesTable.setColumnHeader("email", "E-mail");
		coursesTable.setColumnHeader("dateBegin", "Data rozpoczęcia");
		coursesTable.setColumnHeader("dateEnd", "Data zakończenia");
		coursesTable.setImmediate(true);
		coursesTable.setSizeFull();
		coursesTable.setSelectable(true);
		coursesTable.setColumnWidth("description", 200);
		coursesTable.setVisibleColumns("id", "login","name", "location", "description",
				"email", "dateBegin", "dateEnd");

		// coursesTable.setEditable(true);

		txtCourse.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = -6733373447805994139L;

			@Override
			public void focus(FocusEvent event) {

				btnSearch.setClickShortcut(KeyCode.ENTER);
			}
		});

		List<Course> listCourse = null;
		listCourse = CourseManager.getAllCourse();
		courses.addAll(listCourse);
		coursesTable.setPageLength(coursesTable.size());

		btnSearch.addClickListener(e -> {
			String name = txtCourse.getValue();
			List<Course> list = null;
			list = CourseManager.getCourseByName(name);
			courses.removeAllItems();
			courses.addAll(list);
			coursesTable.setPageLength(coursesTable.size());

		});

		coursesTable.addItemClickListener(e -> {

			BeanItem<Course> courseItem = courses.getItem(e.getItemId());
			courseSelect = courseItem.getBean();
			System.out.println("Zaznaczone" + e.getItem().toString());

		});

		btnSelect.addClickListener(e -> {
			if (MainView.this.courseSelect == null) {
				return;
			} else {
				System.out.println(MainView.this.courseSelect.getName());
				ConfirmWindow confrimWindow = new ConfirmWindow(
						MainView.this.courseSelect);
				UI.getCurrent().addWindow(confrimWindow);
			}

		});

		btnAddCourse.addClickListener(e -> {
			AddCourseWindow addCourseWindow = new AddCourseWindow();
			UI.getCurrent().addWindow(addCourseWindow);
			addCourseWindow.addCloseListener(e1->{
				System.out.println("odswiezamy tabeleczke");
				List<Course> list = null;
				list = CourseManager.getAllCourse();
				courses.removeAllItems();
				courses.addAll(list);
				coursesTable.setPageLength(coursesTable.size());
			});		
		});

		addComponent(lblLogin);
		hLayout.addComponents(labelCourse, txtCourse, btnSearch);
		hLayout.setSpacing(true);
		addComponent(hLayout);
		setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);
		addComponents(coursesTable);
		addComponent(hButton);
		hButton.addComponents(btnSelect, btnAddCourse);
		hButton.setSpacing(true);

	}

}
