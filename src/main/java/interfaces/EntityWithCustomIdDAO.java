package interfaces;

import org.hibernate.HibernateException;

import java.util.List;

public interface EntityWithCustomIdDAO<T> {
//    T create(T entity) throws HibernateException;

    T get(String id) throws HibernateException;

    T update(T entity) throws HibernateException;

    T delete(String id) throws HibernateException;

    List<T> getAll() throws HibernateException;

}
