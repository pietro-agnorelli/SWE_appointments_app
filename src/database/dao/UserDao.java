package database.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import database.DBConnection;
import database.dao.UserDao;
import model.User;

public class UserDao extends BaseDao {

	public void addUser(User user) {
		String insertSQL = "INSERT INTO users (username) VALUES (?)";
		try (Connection connection = DBConnection.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
			pstmt.setString(1, user.getUsername());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
		}
	}

	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	void ensureTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
				"id INT PRIMARY KEY AUTO_INCREMENT," +
				"username VARCHAR(255) NOT NULL UNIQUE," +
				")";
		ensureTableExists(createTableSQL);
	}

}
