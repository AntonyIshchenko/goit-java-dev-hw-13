package storage;

import client.Client;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import planet.Planet;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public enum HibernateService {
    INSTANCE;

    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private final Properties properties = new Properties();

    private SessionFactory sessionFactory;

    public synchronized boolean setProperties(Properties props) {
        if (props == null || isClosed.get() || sessionFactory != null) {
            return false;
        }

        properties.clear();
        properties.putAll(props);

        return true;
    }

    public synchronized SessionFactory getSessionFactory() {
        if (isClosed.get()) {
            throw new IllegalStateException();
        }

        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }

        return sessionFactory;
    }

    private SessionFactory createSessionFactory() throws HibernateException {
        Configuration conf = new Configuration();

        if (!properties.isEmpty()) {
            conf.setProperties(properties);
        }
        addAnnotatedClasses(conf);

        return conf.buildSessionFactory();
    }

    private void addAnnotatedClasses(Configuration conf) {
        conf.addAnnotatedClass(Client.class);
        conf.addAnnotatedClass(Planet.class);
    }

    public synchronized void close() {
        if (sessionFactory != null && !isClosed.get()) {
            try {
                sessionFactory.close();
            } catch (HibernateException ex) {
                System.err.println("Error closing Session Factory: " + ex.getMessage());
            } finally {
                isClosed.set(true);
                sessionFactory = null;
            }
        }
    }

    public boolean isClosed() {
        return isClosed.get();
    }

    public synchronized void restart() {
        if (isClosed.get()) {
            isClosed.set(false);
            sessionFactory = null;
        }
    }
}
