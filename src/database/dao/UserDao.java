package database.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.dao.UserDao;
import model.User;

public class UserDao extends BaseDao {
	
	public UserDao(Connection connection) {
		super(connection);
	}

	public void addUser(User user) {
		String insertSQL = "INSERT INTO users (username) VALUES (?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
			pstmt.setString(1, user.getUsername());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
		}
	}

	public User getUserByUsername(String username) {
		String selectSQL = "SELECT * FROM users WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
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

	@Override
	void ensureTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"username TEXT NOT NULL UNIQUE" +
				")";
		ensureTableExists(createTableSQL);
	}

}
