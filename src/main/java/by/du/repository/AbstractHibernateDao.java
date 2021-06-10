package by.du.repository;

import by.du.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractHibernateDao<T extends Event> implements Dao<T> {

    private final Class<T> type;
    private final Session session;

    @Override
    public Optional<T> findById(int id) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Optional<T> optional = Optional.ofNullable(session.get(type, id));
            transaction.commit();
            return optional;
        } catch (Exception ex) {
            transaction.rollback();
            return Optional.empty();
        }
    }

    @Override
    public List<T> findAll() {
        final Transaction transaction = session.beginTransaction();
        try {
            final String entity = "from " + type.getSimpleName();
            final Query<T> query = session.createQuery(entity, type);
            final List<T> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public List<T> findAllBetween(LocalDateTime from, LocalDateTime to) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Query<T> query = createBetween(from, to);
            final List<T> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public T create(T t) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.persist(t);
            transaction.commit();
            return t;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public void delete(T t) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.remove(t);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public T update(T t) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.persist(t);
            transaction.commit();
            return t;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    public AbstractHibernateDao(Class<T> type, Session session) {
        this.type = type;
        this.session = session;
    }

    protected abstract Query<T> createBetween(LocalDateTime from, LocalDateTime to);
}
