package com.vaadin.abiewska.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.EnrollmentManager;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class EnrollCourseView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private Course courseSelect = null;
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
		Button btnRemove = new Button("Wypisz");

		BeanItemContainer<Course> enroll = new BeanItemContainer<Course>(
				Course.class);

		Table enrollTable = new Table("Kursy, na które się zapisałeś", enroll);
		enrollTable.setColumnHeader("id", "Id");
		enrollTable.setColumnHeader("name", "Nazwa");
		enrollTable.setColumnHeader("category", "Kategoria");
		enrollTable.setColumnHeader("location", "Lokalizacja");
		enrollTable.setColumnHeader("description", "Szczegóły");
		enrollTable.setColumnHeader("email", "E-mail");
		enrollTable.setColumnHeader("dateBegin", "Data rozpoczęcia");
		enrollTable.setColumnHeader("dateEnd", "Data zakończenia");
		enrollTable.setImmediate(true);
		enrollTable.setSizeFull();
		enrollTable.setSelectable(true);
		enrollTable.setColumnWidth("description", 200);
		enrollTable.setVisibleColumns("name", "category", "location",
				"description", "email", "dateBegin", "dateEnd");
		enrollTable.setImmediate(true);

		List<Course> listEnroll = null;

		try {

			listEnroll = EnrollmentManager.getAllEnroll(user);

		} catch (SQLException ex) {

			Notification.show("Brak połączenia z bazą.",
					Notification.Type.ERROR_MESSAGE);

		}

		enroll.addAll(listEnroll);

		enrollTable.setPageLength(enrollTable.size());

		enrollTable.addItemClickListener(e -> {

			BeanItem<Course> courseItem = enroll.getItem(e.getItemId());
			courseSelect = courseItem.getBean();
			System.out.println("Zaznaczone do wypisania"
					+ e.getItem().toString());

		});

		btnRemove.addClickListener(e -> {
			List<Course> list = null;
			if (EnrollCourseView.this.courseSelect == null) {
				return;
			} else {
				try {
					EnrollmentManager.removeEnroll(user, courseSelect);
					list = EnrollmentManager.getAllEnroll(user);
					enroll.removeAllItems();
					enroll.addAll(list);
					enrollTable.setPageLength(enrollTable.size());
				} catch (SQLException ex) {
					Notification.show("Brak połączenia z bazą.",
							Notification.Type.ERROR_MESSAGE);
				}
				System.out.println(EnrollCourseView.this.courseSelect.getId());

			}

		});

		addComponents(enrollTable, btnRemove);
	}
}
