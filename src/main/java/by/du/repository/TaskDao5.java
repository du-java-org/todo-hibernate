package by.du.repository;

import by.du.model.Task;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class TaskDao5 extends AbstractHibernateDao2<Task> {
    public TaskDao5(Session session) {
        super(Task.class, session);
    }

    @Override
    protected void getWhereBetween(
            LocalDateTime from,
            LocalDateTime to,
            CriteriaBuilder cb,
            CriteriaQuery<Task> criteriaQuery,
            Root<Task> root
    ) {
        criteriaQuery.where(cb.between(root.get("date"), from.toLocalDate(), to.toLocalDate()));
    }
}
