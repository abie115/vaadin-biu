package com.vaadin.abiewska.domain;

public class Enrollment {
	private Integer id;
	private String login;
	private Integer id_course;
	private Course course;
	private User user;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Integer getId_course() {
		return id_course;
	}
	public void setId_course(Integer id_course) {
		this.id_course = id_course;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
