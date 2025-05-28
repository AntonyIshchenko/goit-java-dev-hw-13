package client;

import interfaces.EntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDAO implements EntityDAO<Client> {
    private final SessionFactory sessionFactory;

    public ClientDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Client create(Client client) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        }

        return client;
    }

    @Override
    public Client get(long id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Client.class, id);
        }
    }

    @Override
    public Client update(Client client) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(client);
            transaction.commit();
        }

        return client;
    }

    @Override
    public Client delete(long id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.find(Client.class, id);
            if (client != null) {
                session.remove(client);
            }
            transaction.commit();
            return client;
        }
    }

    @Override
    public List<Client> getAll() throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }

}
