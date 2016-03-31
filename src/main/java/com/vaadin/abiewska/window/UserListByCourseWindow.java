package com.vaadin.abiewska.window;

import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.EnrollmentManager;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UserListByCourseWindow extends Window {

	private static final long serialVersionUID = 1L;

	public UserListByCourseWindow(Course course) {
		super("Lista zapisanych użytkowników");
		center();
		final VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		setContent(content);
		setClosable(true);
		setWidth("20%");
		setHeight("70%");
		BeanItemContainer<User> users = new BeanItemContainer<User>(
				User.class);
		Table usersTable = new Table("", users);
		usersTable.setColumnHeader("login", "Login");
		usersTable.setImmediate(true);
		usersTable.setSizeFull();
		usersTable.setVisibleColumns("login");
		
		List<User> listUser= EnrollmentManager.getAllUserEnrollByCourse(course);
		users.addAll(listUser);
		usersTable.setPageLength(usersTable.size());
		content.addComponent(usersTable);

	}

}
