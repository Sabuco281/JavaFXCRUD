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
            // Puedes también loguear la excepción o hacer otras acciones necesarias
            // Devolvemos null si no se puede establecer la conexión
            sessionFactory = null;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}