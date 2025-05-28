package storage;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public enum DatabaseInitService {
    INSTANCE;

    private final AtomicBoolean isInitiated = new AtomicBoolean(false);
    private final Properties props = new Properties();

    public boolean isInitiated() {
        return isInitiated.get();
    }

    public synchronized boolean setProperties(Properties props) {
        if (props == null || isInitiated.get()) {
            return false;
        }

        this.props.clear();
        this.props.putAll(props);

        return true;
    }

    public synchronized void initialize() throws IllegalStateException, FlywayException {
        if (isInitiated.get()) {
            throw new IllegalStateException("Database already initialized");
        }

        String url = props.getProperty("hibernate.connection.url");
        String username = props.getProperty("hibernate.connection.username");
        String password = props.getProperty("hibernate.connection.password");

        if (username.isBlank()) {
            username = null;
        }

        if (password.isBlank()) {
            password = null;
        }
        Flyway flyway = Flyway.configure()
                .dataSource(url, username, password)
                .load();

        flyway.migrate();

        isInitiated.set(true);
    }
}
