package by.du.repository;

import by.du.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractHibernateDao2<T extends Event> implements Dao<T> {

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
            final CriteriaBuilder cb = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteriaQuery = cb.createQuery(type);
            criteriaQuery.from(type);
            final Query<T> query = session.createQuery(criteriaQuery);
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
            final CriteriaBuilder cb = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteriaQuery = cb.createQuery(type);
            final Root<T> root = criteriaQuery.from(type);
            getWhereBetween(from, to, cb, criteriaQuery, root);
            final Query<T> query = session.createQuery(criteriaQuery);
            final List<T> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    protected abstract void getWhereBetween(LocalDateTime from, LocalDateTime to, CriteriaBuilder cb, CriteriaQuery<T> query, Root<T> root);

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

    public AbstractHibernateDao2(Class<T> type, Session session) {
        this.type = type;
        this.session = session;
    }
}
