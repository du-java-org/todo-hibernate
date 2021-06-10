package by.du.repository;

import by.du.model.Task;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;

public class TaskDao4 extends AbstractHibernateDao<Task> {

    private final Session session;

    public TaskDao4(Session session) {
        super(Task.class, session);
        this.session = session;
    }

    @Override
    protected Query<Task> createBetween(LocalDateTime from, LocalDateTime to) {
        final Query<Task> query = session.createQuery("from Task t where t.date between :from and :to", Task.class);
        query.setParameter("from", from.toLocalDate());
        query.setParameter("to", to.toLocalDate());
        return query;
    }
}
