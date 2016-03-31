package com.vaadin.abiewska.window;

import java.sql.SQLException;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.Enrollment;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.EnrollmentManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmWindow extends Window {

	private static final long serialVersionUID = 1L;

	public ConfirmWindow(Course course) {
		super("Potwierdź zapis na kurs:");
		center();
		VerticalLayout content = new VerticalLayout();
		Label lblCourse = new Label("Nazwa: " + course.getName());
		Label lblDescription = new Label("Opis: " + course.getDescription());
		Label lblLocation = new Label("Lokalizacja: " + course.getLocation());
		Label lblEmail = new Label("Kontakt: " + course.getEmail());
		Button btnConfirm = new Button("Zapisz");

		content.addComponents(lblCourse, lblDescription, lblLocation, lblEmail);
		content.setMargin(true);
		content.setSpacing(true);
		setWidth("30%");
		setHeight("40%");
		setContent(content);
	//	setClosable(true);

		User user = (User) UI.getCurrent().getSession()
				.getAttribute("currentUser");

		btnConfirm.addClickListener(e -> {

			Enrollment enroll = new Enrollment();
			enroll.setLogin(user.getLogin());
			enroll.setId_course(course.getId());
			enroll.setCourse(course);
			enroll.setUser(user);

			try {
				if (EnrollmentManager.addEnroll(enroll)) {
					ConfirmWindow.this.close();
					Notification.show("Zapisałeś się na kurs.",
							Notification.Type.HUMANIZED_MESSAGE);
				} else {
					Notification.show("Zapisałeś się już na ten kurs.",
							Notification.Type.WARNING_MESSAGE);
				}
			} catch (SQLException ex) {
				Notification.show("Brak połączenia z bazą.",
						Notification.Type.ERROR_MESSAGE);
			}

		});

		content.addComponent(btnConfirm);

	}
}
