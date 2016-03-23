package com.vaadin.abiewska.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Course {

	private Integer id;

	@Size(min = 3, max = 30, message = "Nazwa  od 3 do 30 znaków")
	private String name;

	//@Size(min = 3, max = 300, message = "Opis nie może być pusty")
	private String description;

	@Size(min = 3, max = 30, message = "Lokalizacja  od 3 do 30 znaków")
	private String location;

	@Size(min = 3, max = 30, message = "Email od 3 do 30 znaków")
	private String email;
	@NotNull(message = "Data nie może być pusta")
	private Date dateBegin;
	@NotNull(message = "Data nie może być pusta")
	private Date dateEnd;

	public Course(Integer id, String name, String description, String location, Date dateBegin, Date dateEnd) {
		super();
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setLocation(location);
		this.setDateBegin(dateBegin);
		this.setDateEnd(dateEnd);
	}

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

}
