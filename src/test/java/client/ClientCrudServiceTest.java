package client;

import interfaces.EntityDAO;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import storage.DatabaseInitService;
import storage.HibernateService;
import utils.ConfigManager;

import java.util.List;
import java.util.Properties;

public class ClientCrudServiceTest {
    private static ClientCrudService clientService;

    @BeforeAll
    static void beforeAll() {
        Properties testProps = ConfigManager.INSTANCE.getAllProperties();
        testProps.put("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        DatabaseInitService.INSTANCE.setProperties(testProps);
        DatabaseInitService.INSTANCE.initialize();

        HibernateService.INSTANCE.setProperties(testProps);
        SessionFactory sessionFactory = HibernateService.INSTANCE.getSessionFactory();

        EntityDAO<Client> clientDAO = new ClientDAO(sessionFactory);
        clientService = new ClientCrudService(clientDAO);
    }

    @AfterAll
    static void afterAll() {
        HibernateService.INSTANCE.close();
    }

    @Test
    void testThatGetAllWorksCorrect() {
        List<Client> allClients = clientService.getAll();

        Assertions.assertFalse(allClients.isEmpty());
    }

    @Test
    void testThatCreateWorksCorrect() {
        String expected = "Ada Lovelace";

        Client newClient = clientService.create(expected);

        Assertions.assertTrue(newClient.getId()>0);
        Assertions.assertEquals(expected, newClient.getName());
    }

    @Test
    void testThatGetByIdWorksCorrect() {

        Client expected = new Client();
        expected.setName("Maria Garcia");
        expected.setId(2L);

        Client actual = clientService.getById(2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testThatUpdateWorksCorrect() {

        Client expected = new Client();
        expected.setName("Jackie Chan");
        expected.setId(3L);

        clientService.update(expected);

        Client actual = clientService.getById(3);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testThatDeleteWorksCorrect() {

        Client expected = clientService.create("Diego Maradona");

        Client actual = clientService.delete(expected.getId());

        Assertions.assertEquals(expected, actual);
    }
}
