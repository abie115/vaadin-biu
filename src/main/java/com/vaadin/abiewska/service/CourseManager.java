package com.vaadin.abiewska.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.User;

public class CourseManager {

	private CourseManager() {
	}

	public static CourseManager search = null;

	public static List<Course> getCourseByName(String name) {
		try {
			String query = "select * from course where name like '%" + name
					+ "%'";
			PreparedStatement pstmt = DBConnection.con()
					.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs == null)
				return null;

			List<Course> listCourse = new ArrayList<Course>();
			Course course = null;

			while (rs.next()) {
				course = new Course();
				course.setId(rs.getInt(1));
				course.setLogin(rs.getString(2));
				course.setName(rs.getString(3));
				course.setLocation(rs.getString(4));
				course.setDescription(rs.getString(5));
				course.setEmail(rs.getString(6));
				course.setDateBegin(rs.getTimestamp(7));
				course.setDateEnd(rs.getTimestamp(8));
				listCourse.add(course);
			}
			rs.close();
			pstmt.close();
			return listCourse;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<Course> getAllCourse() {
		try {
			String query = "select * from course";
			PreparedStatement pstmt = DBConnection.con()
					.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs == null)
				return null;

			List<Course> listCourse = new ArrayList<Course>();
			Course course = null;

			while (rs.next()) {
				course = new Course();
				course.setId(rs.getInt(1));
				course.setLogin(rs.getString(2));
				course.setName(rs.getString(3));
				course.setLocation(rs.getString(4));
				course.setDescription(rs.getString(5));
				course.setEmail(rs.getString(6));
				course.setDateBegin(rs.getTimestamp(7));
				course.setDateEnd(rs.getTimestamp(8));
				listCourse.add(course);
			}
			rs.close();
			pstmt.close();
			return listCourse;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createCourse(Course course) {
		try {
			String insert = "insert into course (id, login, name, location, description, email, datebegin, dateend) "
					+ " values ( default, ?, ?, ?, ?, ?, ?, ? )";
			PreparedStatement pstmt = DBConnection.con().prepareStatement(
					insert);
			pstmt.setString(1, course.getLogin());
			pstmt.setString(2, course.getName());
			pstmt.setString(3, course.getLocation());
			pstmt.setString(4, course.getDescription());
			pstmt.setString(5, course.getEmail());
			pstmt.setTimestamp(6, new java.sql.Timestamp(course.getDateBegin()
					.getTime()));
			pstmt.setTimestamp(7, new java.sql.Timestamp(course.getDateEnd()
					.getTime()));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Course> getCoursesByLogin(User user) {
		try {
			String query = "select id, login, name, location, description, email, datebegin, dateend "
					+ "from course where login='" + user.getLogin() + "'";
			PreparedStatement pstmt = DBConnection.con()
					.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs == null)
				return null;

			List<Course> listCourse = new ArrayList<Course>();
			Course course = null;

			while (rs.next()) {
				course = new Course();
				course.setId(rs.getInt(1));
				course.setLogin(rs.getString(2));
				course.setName(rs.getString(3));
				course.setLocation(rs.getString(4));
				course.setDescription(rs.getString(5));
				course.setEmail(rs.getString(6));
				course.setDateBegin(rs.getTimestamp(7));
				course.setDateEnd(rs.getTimestamp(8));
				listCourse.add(course);
			}
			rs.close();
			pstmt.close();

			return listCourse;
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return null;
	}

	public static void removeCourse(Course course) {
		try {
			String delete = "delete from course where id=?";
			PreparedStatement pstmt = DBConnection.con().prepareStatement(
					delete);
			pstmt.setInt(1, course.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
	}

	public static void editCourse(Course course) {
		try {
			String update = "Update course set name=?, description=?, location=?, email=?, datebegin=?, dateend=? where id=?";
			PreparedStatement pstmt = DBConnection.con().prepareStatement(
					update);
			pstmt.setString(1, course.getName());
			pstmt.setString(2, course.getLocation());
			pstmt.setString(3, course.getDescription());
			pstmt.setString(4, course.getEmail());
			pstmt.setTimestamp(5, new java.sql.Timestamp(course.getDateBegin()
					.getTime()));
			pstmt.setTimestamp(6, new java.sql.Timestamp(course.getDateEnd()
					.getTime()));
			pstmt.setInt(7, course.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
	}

}
