package com.vaadin.abiewska.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vaadin.abiewska.domain.Enrollment;

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

}
