package main.java.database.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import main.java.model.Client;

public class ClientDao extends BaseDao {
	
	public ClientDao(Connection connection) {
		super(connection);
	}

	public void addClient(Client client) {
		String insertSQL = "INSERT INTO clients (name, email) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
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
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
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
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
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
	
	public Client getClientByEmail(String email) {
		String selectSQL = "SELECT * FROM clients WHERE email = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				return new Client(id, name, email);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve client by email: " + e.getMessage(), e);
		}
		return null;
	}

	@Override
	void ensureTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS clients (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT NOT NULL," +
				"email TEXT NOT NULL UNIQUE" +
				")";
		ensureTableExists(createTableSQL);
	}
}
