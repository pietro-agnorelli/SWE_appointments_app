package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Appointment;

public class AppointmentDao extends BaseDao {
	
	public AppointmentDao(Connection connection) {
		super(connection);
	}
	
	public void addAppointment(Appointment appointment) {
		String insertSQL = "INSERT INTO appointments "
				+ "(user_id, client_id, appointment_date, start_time) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
			pstmt.setLong(1, appointment.getUserId());
			pstmt.setLong(2, appointment.getClientId());
			pstmt.setString(3, appointment.getDate().toString());
			pstmt.setString(4, appointment.getStartTime().toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to add appointment: " + e.getMessage(), e);
		}
	}
	
	public List<Appointment> getAppointmentsByUserId(long userId){
		List<Appointment> appointments = new ArrayList<>();
		String selectSQL = "SELECT * FROM appointments WHERE user_id = ? ORDER BY appointment_date, start_time";
		try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
			pstmt.setLong(1, userId);
			try (ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
					long id = resultSet.getLong("id");
					long clientId = resultSet.getLong("client_id");
					String dateStr = resultSet.getString("appointment_date");
					String startTimeStr = resultSet.getString("start_time");
					Appointment appointment = new Appointment(id, userId, clientId, LocalDate.parse(dateStr), LocalTime.parse(startTimeStr));
					appointments.add(appointment);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve appointments: " + e.getMessage(), e);
		}
		return appointments;
	}
	
	public List<Appointment> getAppointmentsByClientId(long clientId){
		List<Appointment> appointments = new ArrayList<>();
		String selectSQL = "SELECT * FROM appointments WHERE client_id = ? ORDER BY appointment_date, start_time";
		try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
			pstmt.setLong(1, clientId);
			try (ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
					long id = resultSet.getLong("id");
					long userId = resultSet.getLong("user_id");
					String dateStr = resultSet.getString("appointment_date");
					String startTimeStr = resultSet.getString("start_time");
					Appointment appointment = new Appointment(id, userId, clientId, LocalDate.parse(dateStr), LocalTime.parse(startTimeStr));
					appointments.add(appointment);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve appointments: " + e.getMessage(), e);
		}
		return appointments;
	}
	
	@Override
	void ensureTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS appointments (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"user_id INTEGER NOT NULL," +
				"client_id INTEGER NOT NULL," +
				"appointment_date TEXT NOT NULL," +
				"start_time TEXT NOT NULL," +
				"FOREIGN KEY (user_id) REFERENCES users(id)," +
				"FOREIGN KEY (client_id) REFERENCES clients(id)" +
				")";
		ensureTableExists(createTableSQL);
	}
}
