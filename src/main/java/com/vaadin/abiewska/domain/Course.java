package com.vaadin.abiewska.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Course {

	private Integer id;

	@NotNull(message = "Nazwa jest wymagana")
	@Size(min = 3, max = 30, message = "Nazwa  od 3 do 30 znaków")
	private String name;

	@NotNull(message = "Kategoria jest wymagana")
	@Size(min = 3, max = 30, message = "Kategoria  od 3 do 30 znaków")
	private String category;

	@NotNull(message = "Opis jest wymagany")
	@Size(min = 3, max = 300, message = "Opis od 3 do 300 znaków")
	private String description;

	@NotNull(message = "Lokalizacja jest wymagana")
	@Size(min = 3, max = 30, message = "Lokalizacja  od 3 do 30 znaków")
	private String location;

	@NotNull(message = "Email jest wymagany")
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Email musi być poprawny")
	private String email;

	@NotNull(message = "Data jest wymagana")
	private Date dateBegin;

	@NotNull(message = "Data jest wymagana")
	private Date dateEnd;

	private String login;

	public Course() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
