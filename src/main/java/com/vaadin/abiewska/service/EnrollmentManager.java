package com.vaadin.abiewska.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.abiewska.domain.Course;
import com.vaadin.abiewska.domain.Enrollment;
import com.vaadin.abiewska.domain.User;

public class EnrollmentManager {

	private static boolean findCourseUser(String login, Integer id)
			throws SQLException {
		String query = "select * from enrollment where login = '" + login
				+ "' AND id_course =" + id;
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			System.out.println("Ten User zapisał się juz na ten kurs.");
			rs.close();
			pstmt.close();
			return true;
		} else {
			System.out.println("Ten User nie zapisał się na ten kurs.");
		}
		return false;
	}

	public static boolean addEnroll(Enrollment enroll) throws SQLException {
		System.out.println("ZAPIS user" + enroll.getLogin() + "id: "
				+ enroll.getCourse().getId());
		if (!findCourseUser(enroll.getLogin(), enroll.getId_course())) {
			String insert = "insert into enrollment (id, login, id_course) values ( default, ?, ? )";
			PreparedStatement pstmt = DBConnection.con().prepareStatement(
					insert);
			pstmt.setString(1, enroll.getLogin());
			pstmt.setInt(2, enroll.getCourse().getId());
			pstmt.executeUpdate();
			pstmt.close();
			return true;

		} else {
			return false;
		}
	}

	public static List<Course> getAllEnroll(User user) throws SQLException {
		String query = "select enrollment.id, enrollment.id_course, course.name, course.location, course.description, course.email, "
				+ "course.datebegin, course.dateend "
				+ "from enrollment join course on enrollment.id_course=course.id where enrollment.login='"
				+ user.getLogin() + "'";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		if (rs == null)
			return null;

		List<Course> listEnroll = new ArrayList<Course>();
		// Enrollment enroll = null;
		Course course = null;

		try {
			while (rs.next()) {
				// enroll = new Enrollment();
				course = new Course();
				course.setId(rs.getInt(2));
				course.setName(rs.getString(3));
				course.setLocation(rs.getString(4));
				course.setDescription(rs.getString(5));
				course.setEmail(rs.getString(6));
				course.setDateBegin(rs.getTimestamp(7));
				course.setDateEnd(rs.getTimestamp(8));

				/*
				 * enroll.setId(rs.getInt(1));
				 * enroll.setId_course(rs.getInt(2)); enroll.setCourse(course);
				 */
				listEnroll.add(course);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return listEnroll;
	}

	public static void removeEnroll(User user, Course course)
			throws SQLException {
		String delete = "delete from enrollment where id_course=? and login=?";
		PreparedStatement pstmt = DBConnection.con().prepareStatement(delete);
		pstmt.setInt(1, course.getId());
		pstmt.setString(2, user.getLogin());
		pstmt.executeUpdate();
		pstmt.close();
	}

	public static List<User> getAllUserEnrollByCourse(Course course) {
		try {
			String query = "select enrollment.login "
					+ "from enrollment join course on enrollment.id_course=course.id where course.id="
					+ course.getId() + "";
			PreparedStatement pstmt = DBConnection.con()
					.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs == null)
				return null;

			List<User> listUser = new ArrayList<User>();
			User user = null;

			while (rs.next()) {
				user = new User();
				user.setLogin(rs.getString(1));
				listUser.add(user);
			}
			rs.close();
			pstmt.close();
			return listUser;
		} catch (SQLException e) {
			System.out.println("Brak polaczenia z baza.");
			e.printStackTrace();
		}
		return null;
	}
}
