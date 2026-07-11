package services;

import java.util.List;

import database.dao.ClientDao;
import model.Client;

public class ClientService {
	private final ClientDao clientDao = new ClientDao();
	
	public void add (String name, String email) throws IllegalArgumentException{
		if(name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
		if(email.contains("@") != true) {
			throw new IllegalArgumentException("Email address must contain @");
		}
		clientDao.addClient(new Client(name, email));
	}
	 
	public List<Client> getAll(){
		return clientDao.getAllClients();
	}
	
	public Client getById(long id) {
		return clientDao.getClientById(id);
	}
}
