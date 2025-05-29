package ticket;

import interfaces.EntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TicketDAO implements EntityDAO<Ticket> {
    private final SessionFactory sessionFactory;

    public TicketDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Ticket create(Ticket ticket) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
        }

        return ticket;
    }

    @Override
    public Ticket get(Long id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Ticket.class, id);
        }
    }

    @Override
    public Ticket update(Ticket ticket) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(ticket);
            transaction.commit();
        }

        return ticket;
    }

    @Override
    public Ticket delete(Long id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = session.find(Ticket.class, id);
            if (ticket != null) {
                session.remove(ticket);
            }
            transaction.commit();
            return ticket;
        }
    }

    @Override
    public List<Ticket> getAll() throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Ticket", Ticket.class).list();
        }
    }
}
