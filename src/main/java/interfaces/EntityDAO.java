package interfaces;

import org.hibernate.HibernateException;

import java.util.List;

public interface EntityDAO<T> {
    T create(T entity) throws HibernateException;

    T get(Long id) throws HibernateException;

    T update(T entity) throws HibernateException;

    T delete(Long id) throws HibernateException;

    List<T> getAll() throws HibernateException;

}
