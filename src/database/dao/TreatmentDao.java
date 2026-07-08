package database.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import database.DBConnection;
import model.Treatment;	
import model.Client;

public class TreatmentDao extends BaseDao {
	
	public void addTreatment(Treatment treatment){
		String insertSQL = "INSERT INTO treatments "
				+ "(user_id, client_id, treatment_date, treatment_description) VALUES (?, ?, ?, ?)";
		try (Connection connection = DBConnection.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
			pstmt.setLong(1, treatment.getUserId());
			pstmt.setLong(2, treatment.getClientId());
			pstmt.setString(3, treatment.getDate().toString());
			pstmt.setString(4, treatment.getDescription());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to add treatment: " + e.getMessage(), e);
		}
	}
	
	public List<Treatment> getTreatmentsByClientId(Client client){
		List<Treatment> treatments = new ArrayList<>();
		String selectSQL = "SELECT * FROM treatments WHERE client_id = ? ORDER BY treatment_date";
		try (Connection connection = DBConnection.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
			pstmt.setLong(1, client.getId());
			try (ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
					long id = resultSet.getLong("id");
					long userId = resultSet.getLong("user_id");
					String dateStr = resultSet.getString("treatment_date");
					String description = resultSet.getString("treatment_description");
					Treatment treatment = new Treatment(id, userId, client.getId(), LocalDate.parse(dateStr), description);
					treatments.add(treatment);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve treatments: " + e.getMessage(), e);
		}
		return treatments;
	}

	@Override
	void ensureTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS treatments (" +
				"id INT PRIMARY KEY AUTO_INCREMENT," +
				"user_id INT NOT NULL," +
				"client_id INT NOT NULL," +
				"treatment_date TEXT NOT NULL," +
				"treatment_description TEXT NOT NULL," +
				"FOREIGN KEY (user_id) REFERENCES users(id)," +
				"FOREIGN KEY (client_id) REFERENCES clients(id)" +
				")";
		ensureTableExists(createTableSQL);
	}

}
