package by.du.repository;

import by.du.model.Meeting;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class MeetingDao5 extends AbstractHibernateDao2<Meeting> {
    public MeetingDao5(Session session) {
        super(Meeting.class, session);
    }

    @Override
    protected void getWhereBetween(
            LocalDateTime from,
            LocalDateTime to,
            CriteriaBuilder cb,
            CriteriaQuery<Meeting> criteriaQuery,
            Root<Meeting> root
    ) {
        criteriaQuery.where(cb.between(root.get("time"), from, to));
    }
}
