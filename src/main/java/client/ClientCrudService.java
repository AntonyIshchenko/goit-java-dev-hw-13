package client;

import interfaces.EntityDAO;

import java.util.List;

public class ClientCrudService {
    private final EntityDAO<Client> dao;

    public ClientCrudService(EntityDAO<Client> dao){
        this.dao = dao;
    }

    public Client create(String name){
        checkName(name);

        Client client = new Client();
        client.setName(name.trim());

        return dao.create(client);
    }

    public Client getById(long id){
        checkId(id);

        return dao.get(id);
    }

    public Client update(Client client){
        if (client == null) {
            throw new IllegalArgumentException("Client mustn't be a null");
        }

        return dao.update(client);
    }

    public Client delete(long id){
        checkId(id);

        return dao.delete(id);
    }

    public List<Client> getAll(){
        return dao.getAll();
    }

    private void checkName(String name) throws IllegalArgumentException{
        // Step 1: Check empty value
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must be not empty value!");
        }

        // Step 2: Check length
        int length = name.trim().length();
        if (length < 3 || length > 200) {
            throw new IllegalArgumentException("Name length must be between 2 and 1000!");
        }
    }

    private void checkId(long id) throws IllegalArgumentException{
        if (id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0!");
        }
    }

}
