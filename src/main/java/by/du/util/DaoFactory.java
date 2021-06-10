package by.du.util;

import by.du.model.Event;
import by.du.model.Meeting;
import by.du.repository.Dao;
import lombok.SneakyThrows;
import org.hibernate.Session;

public class DaoFactory {

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T extends Event> Dao<T> buildRepository(Class<T> type, Session session) {
        String key;
        if (type == Meeting.class) {
            key = "repository.meeting";
        } else {
            key = "repository.task";
        }

        final String path = Props.getValue(key).orElseThrow(IllegalArgumentException::new);
        final Class<?> repClass = Class.forName(path);
        return (Dao<T>) repClass.getConstructor(Session.class).newInstance(session);
    }
}
