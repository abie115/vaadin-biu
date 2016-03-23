package com.vaadin.abiewska.view;

import java.util.Date;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;
import com.vaadin.abiewska.service.CourseManager;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddCourseWindow extends Window {

	public AddCourseWindow() {
		center();

		BeanItem<Course> courseItem = new BeanItem<Course>(new Course());

		final VerticalLayout content = new VerticalLayout();

		TextField txtName = new TextField("Nazwa: ");
		TextField txtDecription = new TextField("Opis: ");
		TextField txtLocation = new TextField("Lokalizacja: ");
		TextField txtEmail = new TextField("Kontakt: ");
		Button btnAdd = new Button("Dodaj");

		DateField dateBegin = new DateField();
		content.addComponent(dateBegin);
		dateBegin.setCaption("Data rozpoczęcia:");
		dateBegin.setDateFormat("yyyy-MM-dd HH:mm");
		dateBegin.setResolution(Resolution.MINUTE);
		dateBegin.setRangeStart(new Date());
		dateBegin.setDateOutOfRangeMessage("Data musi być w przyszłości");
		dateBegin.setInvalidAllowed(false);
		dateBegin.setValue(new Date());

		DateField dateEnd = new DateField();
		content.addComponent(dateEnd);
		dateEnd.setCaption("Data zakończenia:");
		dateEnd.setDateFormat("yyyy-MM-dd HH:mm");
		dateEnd.setResolution(Resolution.MINUTE);
		dateEnd.setRangeStart(new Date());
		dateEnd.setDateOutOfRangeMessage("Data musi być w przyszłości");
		dateEnd.setInvalidAllowed(false);
		dateEnd.setValue(new Date());

		txtName.setNullRepresentation("");
		txtDecription.setNullRepresentation("");
		txtLocation.setNullRepresentation("");
		txtEmail.setNullRepresentation("");

		Panel panel = new Panel("Dodanie kursu");
		panel.setSizeUndefined();
		content.addComponent(panel);

		FormLayout formLogin = new FormLayout();
		formLogin.addStyleName("add-form");
		formLogin.addComponent(txtName);
		formLogin.addComponent(txtDecription);
		formLogin.addComponent(txtLocation);
		formLogin.addComponent(txtEmail);
		formLogin.addComponent(dateBegin);
		formLogin.addComponent(dateEnd);
		formLogin.addComponent(btnAdd);
		formLogin.setMargin(true);
		formLogin.setSpacing(true);
		panel.setContent(formLogin);

		FieldGroup binder = new FieldGroup(courseItem);
		binder.setBuffered(true);

		binder.bind(txtName, "name");
		binder.bind(txtDecription, "description");
		binder.bind(txtLocation, "location");
		binder.bind(txtEmail, "email");
		binder.bind(dateBegin, "dateBegin");
		binder.bind(dateEnd, "dateEnd");

		txtName.addValidator(new BeanValidator(Course.class, "name"));
		txtDecription.addValidator(new BeanValidator(Course.class,
				"description"));
		txtLocation.addValidator(new BeanValidator(Course.class, "location"));
		txtEmail.addValidator(new BeanValidator(Course.class, "email"));
		dateBegin.addValidator(new BeanValidator(Course.class, "dateBegin"));
		dateEnd.addValidator(new BeanValidator(Course.class, "dateEnd"));

		txtName.setImmediate(true);
		txtDecription.setImmediate(true);
		txtLocation.setImmediate(true);
		txtEmail.setImmediate(true);

		txtName.setRequired(true);
		txtName.setRequiredError("Nazwa jest wymagana");
		txtLocation.setRequired(true);
		txtLocation.setRequiredError("Lokalizacja jest wymagana");
		txtDecription.setRequired(true);
		txtDecription.setRequiredError("Lokalizacja jest wymagana");
		txtEmail.setRequired(true);
		txtEmail.setRequiredError("Email jest wymagany");
		dateBegin.setRequired(true);
		dateBegin.setRequiredError("Data jest wymagana");
		dateEnd.setRequired(true);
		dateEnd.setRequiredError("Data jest wymagana");

		btnAdd.addClickListener(e -> {
			try {

				binder.commit();
				User user = (User) UI.getCurrent().getSession()
						.getAttribute("currentUser");
				Course course = new Course();
				course.setLogin(user.getLogin());
				course.setName(txtName.getValue());
				course.setDescription(txtDecription.getValue());
				course.setLocation(txtLocation.getValue());
				course.setEmail(txtEmail.getValue());
				course.setDateBegin(dateBegin.getValue());
				course.setDateEnd(dateEnd.getValue());
				System.out.println("test daty "
						+ dateBegin.getValue().toString());

				CourseManager.createCourse(course);
				AddCourseWindow.this.close();
				Notification.show("Dodałeś nowy kurs.",
						Notification.Type.HUMANIZED_MESSAGE);

			} catch (CommitException ex) {
				Notification.show("Wprowadzono nieprawidłowe dane",
						Notification.Type.WARNING_MESSAGE);
				System.out.println("Nieprawidlowe dane");
				ex.printStackTrace();
			}

		});

		content.setMargin(true);
		content.setSpacing(true);
		setContent(content);
		setClosable(true);

	}
}
