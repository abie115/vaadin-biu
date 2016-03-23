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

	public static List<Course> getCourseByName(String name) throws SQLException {
		String query = "select * from course where name like '%" + name + "%'";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		if (rs == null)
			return null;

		List<Course> listCourse = new ArrayList<Course>();
		Course course = null;

		try {
			while (rs.next()) {
				course = new Course();
				course.setId(rs.getInt(1));
				course.setName(rs.getString(2));
				course.setLocation(rs.getString(3));
				course.setDescription(rs.getString(4));
				course.setEmail(rs.getString(5));
				course.setDateBegin(rs.getTimestamp(6));
				course.setDateEnd(rs.getTimestamp(7));
			
				listCourse.add(course);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return listCourse;

	}
	
	public static List<Course> getAllCourse() throws SQLException{
		String query = "select * from course";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		if (rs == null)
			return null;

		List<Course> listCourse = new ArrayList<Course>();
		Course course = null;

		try {
			while (rs.next()) {
				course = new Course();
				course.setId(rs.getInt(1));
				course.setName(rs.getString(2));
				course.setLocation(rs.getString(3));
				course.setDescription(rs.getString(4));
				course.setEmail(rs.getString(5));
				course.setDateBegin(rs.getDate(6));
				course.setDateEnd(rs.getDate(7));
				System.out.println(rs.getTimestamp(7));
				listCourse.add(course);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return listCourse;

	}
}
