package com.vaadin.abiewska.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.abiewska.domain.Course;

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
}
