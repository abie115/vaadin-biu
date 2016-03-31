package com.vaadin.abiewska.window;

import java.util.Date;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.service.CourseManager;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EditCourseWindow extends Window {

	private static final long serialVersionUID = 1L;

	public void runEditCourseWindow(Course course) {

	}

	public EditCourseWindow(Course course, String action) {
		center();

		VerticalLayout content = new VerticalLayout();
		Course editCourse = new Course();
		BeanItem<Course> courseItem = new BeanItem<Course>(editCourse);

		editCourse.setId(course.getId());
		editCourse.setLogin(course.getLogin());
		editCourse.setName(course.getName());
		editCourse.setCategory(course.getCategory());
		editCourse.setDescription(course.getDescription());
		editCourse.setLocation(course.getLocation());
		editCourse.setEmail(course.getEmail());
		editCourse.setDateBegin(course.getDateBegin());
		editCourse.setDateEnd(course.getDateEnd());

		TextField txtName = new TextField("Nazwa: ");
		TextArea txtDecription = new TextArea("Opis: ");
		TextField txtLocation = new TextField("Lokalizacja: ");
		TextField txtEmail = new TextField("Kontakt: ");
		Button btnAdd = new Button("Zapisz");
		DateField dateBegin = new DateField();
		content.addComponent(dateBegin);
		dateBegin.setCaption("Data rozpoczęcia:");
		dateBegin.setDateFormat("yyyy-MM-dd HH:mm");
		dateBegin.setResolution(Resolution.MINUTE);

		DateField dateEnd = new DateField();
		content.addComponent(dateEnd);
		dateEnd.setCaption("Data zakończenia:");
		dateEnd.setDateFormat("yyyy-MM-dd HH:mm");
		dateEnd.setResolution(Resolution.MINUTE);
		dateEnd.setRangeStart(new Date());
		dateEnd.setDateOutOfRangeMessage("Data musi być w przyszłości");
		dateEnd.setInvalidAllowed(false);
		dateEnd.setValue(new Date());

		ComboBox comboCategory = new ComboBox();
		comboCategory.setCaption("Kategoria");
		comboCategory.addItem("Kurs");
		comboCategory.addItem("Wyklad");
		comboCategory.setNullSelectionAllowed(false);
		comboCategory.setValue("Kurs");
		
		Panel panel = new Panel();
		panel.setSizeUndefined();
		content.addComponent(panel);

		FormLayout formLogin = new FormLayout();
		formLogin.addStyleName("edit-form");
		formLogin.addComponent(txtName);
		formLogin.addComponent(comboCategory);
		formLogin.addComponent(txtDecription);
		formLogin.addComponent(txtLocation);
		formLogin.addComponent(txtEmail);
		formLogin.addComponent(dateBegin);
		formLogin.addComponent(dateEnd);
		formLogin.setMargin(true);
		formLogin.setSpacing(true);
		panel.setContent(formLogin);

		FieldGroup binder = new FieldGroup(courseItem);
		binder.setBuffered(true);

		binder.bind(txtName, "name");
		binder.bind(comboCategory, "category");
		binder.bind(txtDecription, "description");
		binder.bind(txtLocation, "location");
		binder.bind(txtEmail, "email");
		binder.bind(dateBegin, "dateBegin");
		binder.bind(dateEnd, "dateEnd");

		txtName.addValidator(new BeanValidator(Course.class, "name"));
		comboCategory.addValidator(new BeanValidator(Course.class, "category"));
		txtDecription.addValidator(new BeanValidator(Course.class,
				"description"));
		txtLocation.addValidator(new BeanValidator(Course.class, "location"));
		txtEmail.addValidator(new BeanValidator(Course.class, "email"));
		dateBegin.addValidator(new BeanValidator(Course.class, "dateBegin"));
		dateEnd.addValidator(new BeanValidator(Course.class, "dateEnd"));

		txtName.setImmediate(true);
		comboCategory.setImmediate(true);
		txtDecription.setImmediate(true);
		txtLocation.setImmediate(true);
		txtEmail.setImmediate(true);

		txtName.setRequired(true);
		txtName.setRequiredError("Nazwa jest wymagana");
		comboCategory.setRequired(true);
		comboCategory.setRequiredError("Kategoria jest wymagana");
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

		dateBegin.setReadOnly(true);
		if (action.equals("SHOW")) {
			panel.setCaption("Kurs");
			txtName.setReadOnly(true);
			comboCategory.setReadOnly(true);
			txtLocation.setReadOnly(true);
			txtDecription.setReadOnly(true);
			txtEmail.setReadOnly(true);
			dateEnd.setReadOnly(true);
		} else {
			panel.setCaption("Edycja kursu");
			formLogin.addComponent(btnAdd);
		}

		btnAdd.addClickListener(e -> {
			try {
				binder.commit();
				CourseManager.editCourse(editCourse);
				EditCourseWindow.this.close();
				Notification.show("Edycja kursu zakończona pomyślnie.",
						Notification.Type.HUMANIZED_MESSAGE);

			} catch (CommitException ex) {
				Notification.show("Wprowadzono nieprawidłowe dane",
						Notification.Type.WARNING_MESSAGE);
				ex.printStackTrace();
			}

		});

		content.setMargin(true);
		content.setSpacing(true);
		setContent(content);
		setClosable(true);

	}
}
