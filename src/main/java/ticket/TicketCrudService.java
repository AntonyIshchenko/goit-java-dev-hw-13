package ticket;

import client.Client;
import interfaces.EntityDAO;
import planet.Planet;

import java.util.List;

public class TicketCrudService {
    private final EntityDAO<Ticket> dao;

    public TicketCrudService(EntityDAO<Ticket> dao){
        this.dao = dao;
    }

    public Ticket create(Client client, Planet fromPlanet, Planet toPlanet){
        checkClient(client);
        checkPlanet(fromPlanet, "from");
        checkPlanet(toPlanet, "to");

        if (fromPlanet.equals(toPlanet)){
            throw new IllegalArgumentException("Planet FROM must be different from Planet TO");
        }

        Ticket ticket = new Ticket();
        ticket.setClient(client);
        ticket.setFromPlanet(fromPlanet);
        ticket.setToPlanet(toPlanet);

        return dao.create(ticket);
    }

    public Ticket getById(long id){
        checkId(id);

        return dao.get(id);
    }

    public Ticket update(Ticket ticket){
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket mustn't be a null");
        }

        checkClient(ticket.getClient());
        checkPlanet(ticket.getFromPlanet(), "from");
        checkPlanet(ticket.getToPlanet(), "to");

        if (ticket.getFromPlanet().equals(ticket.getToPlanet())){
            throw new IllegalArgumentException("Planet FROM must be different from Planet TO");
        }

        return dao.update(ticket);
    }

    public Ticket delete(long id){
        checkId(id);

        return dao.delete(id);
    }

    public List<Ticket> getAll(){
        return dao.getAll();
    }

    private void checkClient(Client client) throws IllegalArgumentException{
        if (client == null ) {
            throw new IllegalArgumentException("Client mustn't be null!");
        }
    }

    private void checkPlanet(Planet planet, String description) throws IllegalArgumentException{
        if (planet == null ) {
            String message = "Planet mustn't be null!";

            if (description != null && !description.isBlank()) {
                message = message.replace("Planet", "Planet "+description.toUpperCase());
            }

            throw new IllegalArgumentException(message);
        }
    }

    private void checkId(long id) throws IllegalArgumentException{
        if (id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0!");
        }
    }
}
