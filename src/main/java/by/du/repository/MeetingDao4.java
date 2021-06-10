package by.du.repository;

import by.du.model.Meeting;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;

public class MeetingDao4 extends AbstractHibernateDao<Meeting> {

    private final Session session;

    public MeetingDao4(Session session) {
        super(Meeting.class, session);
        this.session = session;
    }

    @Override
    protected Query<Meeting> createBetween(LocalDateTime from, LocalDateTime to) {
        final Query<Meeting> query = session.createQuery("from Meeting t where t.time between :from and :to", Meeting.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        return query;
    }
}
