package database.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import database.DBConnection;
import database.dao.ClientDao;
import model.Client;

public class ClientDao extends BaseDao {

	public void addClient(Client client) {
		String insertSQL = "INSERT INTO clients (name, email) VALUES (?, ?)";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, client.getName());
			preparedStatement.setString(2, client.getEmail());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to add client: " + e.getMessage(), e);
		}
	}

	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<>();
		String selectSQL = "SELECT * FROM clients ORDER BY name";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Client client = new Client(id, name, email);
				clients.add(client);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve clients: " + e.getMessage(), e);
		}
		return clients;
	}
	
	public Client getClientById(Long id) {
		String selectSQL = "SELECT * FROM clients WHERE id = ?";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				return new Client(id, name, email);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve client by ID: " + e.getMessage(), e);
		}
		return null;
	}

	public void updateClient(ClientDao client) {
		// TODO Auto-generated method stub

	}
	
	public void deleteClient(ClientDao client) {
		// TODO Auto-generated method stub

	}
	

	void ensureTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS clients (" +
				"id INT PRIMARY KEY AUTO_INCREMENT," +
				"name VARCHAR(255) NOT NULL," +
				"email VARCHAR(255) NOT NULL UNIQUE," +
				")";
		ensureTableExists(createTableSQL);
	}

}
