package ticket;

import client.Client;
import client.ClientCrudService;
import client.ClientDAO;
import interfaces.EntityDAO;
import interfaces.EntityWithCustomIdDAO;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import planet.Planet;
import planet.PlanetCrudService;
import planet.PlanetDAO;
import storage.DatabaseInitService;
import storage.HibernateService;
import utils.ConfigManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class TicketCrudServiceTest {
    private static TicketCrudService ticketService;
    private static ClientCrudService clientService;
    private static PlanetCrudService planetService;

    @BeforeAll
    static void beforeAll() {
        Properties testProps = ConfigManager.INSTANCE.getAllProperties();
        testProps.put("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        DatabaseInitService.INSTANCE.setProperties(testProps);
        DatabaseInitService.INSTANCE.initialize();

        HibernateService.INSTANCE.setProperties(testProps);
        SessionFactory sessionFactory = HibernateService.INSTANCE.getSessionFactory();

        EntityDAO<Ticket> ticketDAO = new TicketDAO(sessionFactory);
        ticketService = new TicketCrudService(ticketDAO);

        EntityDAO<Client> clientDAO = new ClientDAO(sessionFactory);
        clientService = new ClientCrudService(clientDAO);

        EntityWithCustomIdDAO<Planet> planetDAO = new PlanetDAO(sessionFactory);
        planetService = new PlanetCrudService(planetDAO);
    }

    @AfterAll
    static void afterAll() {
        HibernateService.INSTANCE.close();
    }

    @Test
    void testThatGetAllWorksCorrect() {
        List<Ticket> allTickets = ticketService.getAll();

        Assertions.assertFalse(allTickets.isEmpty());
    }

    @Test
    void testThatCreateWorksCorrect() {

        Client client = clientService.getById(1);
        Planet fromPlanet = planetService.getById("JUPITER1");
        Planet toPlanet = planetService.getById("MOON");

        LocalDateTime currentDateTime = LocalDateTime.now();
        Ticket newTicket = ticketService.create(client, fromPlanet, toPlanet);

        Assertions.assertTrue(newTicket.getId()>0);
        Assertions.assertTrue(currentDateTime.isBefore(newTicket.getCreatedAt()));
        Assertions.assertEquals(client, newTicket.getClient());
        Assertions.assertEquals(fromPlanet, newTicket.getFromPlanet());
        Assertions.assertEquals(toPlanet, newTicket.getToPlanet());
    }

    @Test
    void testThatCreateThrowsExceptionsWhenError() {

        Planet fakePlanet = new Planet();
        fakePlanet.setName("FP345");
        fakePlanet.setName("Fake planet");

        Client client = clientService.getById(1);
        Planet planetEarth = planetService.getById("EARTH");
        Planet planetMoon = planetService.getById("MOON");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ticketService.create(client, planetMoon, null );
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ticketService.create(client, null,  planetEarth );
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ticketService.create(null, planetMoon,  planetEarth );
        });

        Assertions.assertThrows(Exception.class, () -> {
            ticketService.create(client, fakePlanet,  planetEarth );
        });

    }

    @Test
    void testThatGetByIdWorksCorrect() {

        Client expectedClient = clientService.getById(1);
        Planet expectedFromPlanet = planetService.getById("EARTH");
        Planet expectedToPlanet = planetService.getById("MARS");

        Ticket actual = ticketService.getById(1);

        Assertions.assertEquals(expectedClient, actual.getClient());
        Assertions.assertEquals(expectedFromPlanet, actual.getFromPlanet());
        Assertions.assertEquals(expectedToPlanet, actual.getToPlanet());
    }

    @Test
    void testThatUpdateWorksCorrect() {

        Client client = clientService.getById(2);
        Planet fromPlanet = planetService.getById("MOON");

        Ticket currentTicket = ticketService.getById(1);
        currentTicket.setClient(client);
        currentTicket.setFromPlanet(fromPlanet);

        Ticket expected = new Ticket();
        expected.setId(currentTicket.getId());
        expected.setCreatedAt(currentTicket.getCreatedAt());
        expected.setClient(client);
        expected.setFromPlanet(fromPlanet);
        expected.setToPlanet(currentTicket.getToPlanet());

        ticketService.update(currentTicket);

        Ticket actual = ticketService.getById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testThatDeleteWorksCorrect() {

        Client client = clientService.getById(1);
        Planet fromPlanet = planetService.getById("VENUS");
        Planet toPlanet = planetService.getById("MARS");

        Ticket expected = ticketService.create(client, fromPlanet, toPlanet);
        Ticket actual = ticketService.delete(expected.getId());

        Assertions.assertEquals(expected, actual);
    }
}
