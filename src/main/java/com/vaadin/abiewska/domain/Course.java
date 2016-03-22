package com.vaadin.abiewska.domain;

import java.util.Date;

public class Course {

	private Integer id;
	private String name;
	private String description;
	private String location;
	private String email;
	private Date dateBegin;
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
