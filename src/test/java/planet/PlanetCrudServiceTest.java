package planet;

import interfaces.EntityWithCustomIdDAO;
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

class PlanetCrudServiceTest {
    private static PlanetCrudService planetService;

    @BeforeAll
    static void beforeAll() {
        Properties testProps = ConfigManager.INSTANCE.getAllProperties();
        testProps.put("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        DatabaseInitService.INSTANCE.setProperties(testProps);
        DatabaseInitService.INSTANCE.initialize();

        HibernateService.INSTANCE.setProperties(testProps);
        SessionFactory sessionFactory = HibernateService.INSTANCE.getSessionFactory();

        EntityWithCustomIdDAO<Planet> planetDAO = new PlanetDAO(sessionFactory);
        planetService = new PlanetCrudService(planetDAO);
    }

    @AfterAll
    static void afterAll() {
        HibernateService.INSTANCE.close();
    }

    @Test
    void testThatGetAllWorksCorrect() {
        List<Planet> allClients = planetService.getAll();

        Assertions.assertFalse(allClients.isEmpty());
    }

    @Test
    void testThatCreateWorksCorrect() {
        Planet expected = new Planet();
        expected.setName("Mercury Shadow Station");
        expected.setId("MERCURY05");

        Planet actual = planetService.create(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testThatGetByIdWorksCorrect() {

        Planet expected = new Planet();
        expected.setName("Earth");
        expected.setId("EARTH");

        Planet actual = planetService.getById("EARTH");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testThatUpdateWorksCorrect() {

        Planet expected = new Planet();
        expected.setName("Moon Main Base ");
        expected.setId("MOON");

        planetService.update(expected);

        Planet actual = planetService.getById("MOON");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testThatDeleteWorksCorrect() {

        Planet expected = new Planet();
        expected.setName("Uranus First Station");
        expected.setId("URANUS1");

        planetService.create(expected);

        Planet actual = planetService.delete("URANUS1");

        Assertions.assertEquals(expected, actual);
    }
}
