package com.keyholesoftware.lambda.dao;

import com.keyholesoftware.lambda.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository("mysql")
public class MySqlStudentDaoImpl implements StudentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static class StudentRowMapper implements RowMapper<Student> {

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setId(rs.getInt("id"));
			student.setName(rs.getString("name"));
			student.setCourse(rs.getString("course"));
			return student;
		}

	}

	@Override
	public Collection<Student> getAllStudents() {

		final String sql = "SELECT ID, NAME, COURSE FROM STUDENTS";
		List<Student> students = jdbcTemplate.query(sql, new StudentRowMapper());
		return students;
	}

	@Override
	public Student getStudentById(int id) {
		final String sql = "SELECT ID, NAME, COURSE FROM STUDENTS WHERE ID = ?";
		Student student = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
		return student;

	}

	@Override
	public void removeStudentById(int id) {
		final String sql = "DELETE FROM STUDENTS WHERE ID = ?";
		jdbcTemplate.update(sql, id);

	}

	@Override
	public void updateStudent(Student student) {
		final String sql = "UPDATE STUDENTS SET name = ? , course = ?  WHERE ID = ?";
		int id = student.getId();
		String name = student.getName();
		String course = student.getCourse();
		jdbcTemplate.update(sql, new Object[] { name, course, id });

	}

	@Override
	public void insertStudentToDb(Student student) {
		final String sql = "INSERT INTO STUDENTS (NAME, COURSE) VALUES( ? , ? )";
		String name = student.getName();
		String course = student.getCourse();
		jdbcTemplate.update(sql, new Object[] { name, course });

	}

}
