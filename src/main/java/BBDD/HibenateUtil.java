package BBDD;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibenateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Error al inicializar la SessionFactory: " + ex);

            sessionFactory = null;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}