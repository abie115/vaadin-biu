package com.vaadin.abiewska.view;

import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.CourseManager;
import com.vaadin.abiewska.window.AddCourseWindow;
import com.vaadin.abiewska.window.EditCourseWindow;
import com.vaadin.abiewska.window.UserListByCourseWindow;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CreateCourseView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	private Course courseSelect = null;
	private Object idSelect = null;
	private User user = null;

	@Override
	public void enter(ViewChangeEvent event) {

		user = (User) UI.getCurrent().getSession().getAttribute("currentUser");

		if (user == null) {
			UI.getCurrent().getNavigator().navigateTo("login");
		} else {
			this.addComponent(new MenuPanel());
			this.setUp();
		}

	}

	public void setUp() {

		setMargin(true);
		setSpacing(true);
		Button btnAddCourse = new Button("Dodaj kurs");
		Button btnEditCourse = new Button("Edytuj kurs");
		Button btnRemoveCourse = new Button("Usuń");
		Button btnListUser = new Button("Lista zapisanych");

		HorizontalLayout hButton = new HorizontalLayout();

		hButton.addComponents(btnAddCourse, btnRemoveCourse, btnEditCourse,
				btnListUser);
		hButton.setSpacing(true);

		BeanItemContainer<Course> courses = new BeanItemContainer<Course>(
				Course.class);

		Table coursesTable = new Table("Kursy, które dodałeś", courses);
		coursesTable.setColumnHeader("id", "Id");
		coursesTable.setColumnHeader("name", "Nazwa");
		coursesTable.setColumnHeader("category", "Kategoria");
		coursesTable.setColumnHeader("location", "Lokalizacja");
		coursesTable.setColumnHeader("description", "Szczegóły");
		coursesTable.setColumnHeader("email", "E-mail");
		coursesTable.setColumnHeader("dateBegin", "Data rozpoczęcia");
		coursesTable.setColumnHeader("dateEnd", "Data zakończenia");
		coursesTable.setImmediate(true);
		coursesTable.setSizeFull();
		coursesTable.setSelectable(true);
		coursesTable.setColumnWidth("description", 200);
		coursesTable.setVisibleColumns("login", "name", "category", "location",
				"description", "email", "dateBegin", "dateEnd");
		coursesTable.setImmediate(true);

		List<Course> listCourse = null;

		listCourse = CourseManager.getCoursesByLogin(user);

		courses.addAll(listCourse);
		coursesTable.setPageLength(coursesTable.size());

		btnAddCourse.addClickListener(e -> {
			AddCourseWindow addCourseWindow = new AddCourseWindow();
			UI.getCurrent().addWindow(addCourseWindow);
			addCourseWindow.addCloseListener(e1 -> {
				List<Course> list = null;
				list = CourseManager.getCoursesByLogin(user);
				courses.removeAllItems();
				courses.addAll(list);
				coursesTable.setPageLength(coursesTable.size());
			});
		});

		btnEditCourse.addClickListener(e -> {
			editCourse(courses, user, coursesTable, courseSelect, "EDIT");

		});

		btnListUser
				.addClickListener(e -> {
					UserListByCourseWindow userListByCourseWindow = new UserListByCourseWindow(
							courseSelect);
					UI.getCurrent().addWindow(userListByCourseWindow);
				});

		coursesTable.addItemClickListener(e -> {
			BeanItem<Course> courseItem = courses.getItem(e.getItemId());
			idSelect = e.getItemId();
			courseSelect = courseItem.getBean();
			if (e.isDoubleClick()) {
				editCourse(courses, user, coursesTable, courseSelect, "EDIT");
			}

		});

		btnRemoveCourse.addClickListener(e -> {
			if (CreateCourseView.this.courseSelect == null) {
				return;
			} else {
				CourseManager.removeCourse(courseSelect);
				courses.removeItem(idSelect);
				Notification.show("Usunąłeś kurs.",
						Notification.Type.HUMANIZED_MESSAGE);
				coursesTable.setPageLength(coursesTable.size());
			}

		});

		addComponents(coursesTable, hButton);
	}

	public void editCourse(BeanItemContainer<Course> courses, User user,
			Table coursesTable, Course courseSelect, String action) {
		EditCourseWindow editCourseWindow = new EditCourseWindow(courseSelect,
				action);
		UI.getCurrent().addWindow(editCourseWindow);
		editCourseWindow.addCloseListener(e1 -> {
			List<Course> list = null;
			list = CourseManager.getCoursesByLogin(user);
			courses.removeAllItems();
			courses.addAll(list);
			coursesTable.setPageLength(coursesTable.size());
		});
	}
}
