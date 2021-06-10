package by.du.repository;

import by.du.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TaskDao3 implements Dao<Task> {

    private final Session session;

    @Override
    public Optional<Task> findById(int id) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Optional<Task> Task = Optional.ofNullable(session.get(Task.class, id));
            transaction.commit();
            return Task;
        } catch (Exception ex) {
            transaction.rollback();
            return Optional.empty();
        }
    }

    @Override
    public List<Task> findAll() {
        final Transaction transaction = session.beginTransaction();
        try {
            final Query<Task> query = session.createQuery("from Task", Task.class);
            final List<Task> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Task> findAllBetween(LocalDateTime from, LocalDateTime to) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Query<Task> query = session.createQuery("from Task m where m.time between :from and :to", Task.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            final List<Task> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public Task create(Task Task) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.persist(Task);
            transaction.commit();
            return Task;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public void delete(Task Task) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.remove(Task);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public Task update(Task Task) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.persist(Task);
            transaction.commit();
            return Task;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }
}
