package utils;

import lombok.experimental.UtilityClass;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

@UtilityClass
public class HibernateSessionFactoryUtil {

    static SessionFactory sessionFactory = null;


    public static void doInHibernateSession(Consumer<Session> sessionConsumer) {
        if (sessionFactory == null) {
            setSession();
        }
        try (Session session = sessionFactory.openSession()) {
            sessionConsumer.accept(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSession() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Airport.class);
            configuration.addAnnotatedClass(Board.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Flight.class);
            configuration.addAnnotatedClass(Ticket.class);
            configuration.addAnnotatedClass(FlightInfo.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            System.out.println("Exception!" + e);


        }
    }

}