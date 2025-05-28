package interfaces;

import org.hibernate.HibernateException;

import java.util.List;

public interface EntityDAO<T> {
    T create(T entity) throws HibernateException;

    T get(long id) throws HibernateException;

    T update(T entity) throws HibernateException;

    T delete(long id) throws HibernateException;

    List<T> getAll() throws HibernateException;

}
