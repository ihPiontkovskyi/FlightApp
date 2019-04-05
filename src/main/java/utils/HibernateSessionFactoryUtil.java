package utils;

import lombok.experimental.UtilityClass;
import models.Airport;
import models.Board;
import models.Client;
import models.Flight;
import models.FlightInfo;
import models.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class HibernateSessionFactoryUtil {


	public static void doInHibernateSession(Consumer<Session> sessionConsumer) {
		SessionFactory sessionFactory = null;

			try {
				Configuration configuration = new Configuration().configure();
				configuration.addAnnotatedClass(Airport.class);
				configuration.addAnnotatedClass(Board.class);
				configuration.addAnnotatedClass(Client.class);
				configuration.addAnnotatedClass(Flight.class);
				configuration.addAnnotatedClass(FlightInfo.class);
				configuration.addAnnotatedClass(Ticket.class);
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(builder.build());
			} catch (Exception e) {
				System.out.println("Exception!" + e);


		}
		try (Session session = sessionFactory.openSession()) {
			sessionConsumer.accept(session);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}