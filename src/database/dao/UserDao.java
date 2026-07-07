package database.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		String selectSQL = "SELECT * FROM users WHERE username = ?";
		try (Connection connection = DBConnection.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String usernameFromDB = rs.getString("username");
				User user = new User(id, usernameFromDB);
				return user;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to get user by username: " + e.getMessage(), e);
		}
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
