package planet;


import interfaces.EntityWithCustomIdDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlanetDAO implements EntityWithCustomIdDAO<Planet> {
    private final SessionFactory sessionFactory;

    public PlanetDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    @Override
//    public Planet create(Planet planet) throws HibernateException {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.merge(planet);
//            transaction.commit();
//        }
//
//        return planet;
//    }

    @Override
    public Planet get(String id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Planet.class, id);
        }
    }

    @Override
    public Planet update(Planet planet) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(planet);
            transaction.commit();
        }

        return planet;
    }

    @Override
    public Planet delete(String id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Planet planet = session.find(Planet.class, id);
            if (planet != null) {
                session.remove(planet);
            }
            transaction.commit();
            return planet;
        }
    }

    @Override
    public List<Planet> getAll() throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Planet", Planet.class).list();
        }
    }


}
