package by.du.util;

import by.du.model.Meeting;
import by.du.model.Task;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateConfig {

    public static Session createSession() {
        final Configuration configuration = new Configuration();
        configuration.addProperties(getProps());
        configuration.addAnnotatedClass(Meeting.class);
        configuration.addAnnotatedClass(Task.class);
        return configuration.buildSessionFactory().openSession();
    }

    @SneakyThrows
    private static Properties getProps() {
        final Properties properties = new Properties();
        properties.load(HibernateConfig.class.getClassLoader().getResourceAsStream("hibernate.properties"));
        return properties;
    }
}
