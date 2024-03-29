package Service;

import DAO.GenericDAO;
import Entity.Empleado;
import BBDD.HibenateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class GenericDAOImpl<T> implements GenericDAO<T> {
    private final Class<T> entityClass;
    public GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    @Override
    public Optional<T> findById(Long id) {
        try (Session session = HibenateUtil.getSessionFactory().openSession();) {
            return Optional.ofNullable(session.find(entityClass, id));
        }
    }

    @Override
    public boolean guardar(T entity) {
        try (Session session = HibenateUtil.getSessionFactory().openSession();) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(entity);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void eliminar(Long id) {
        try (Session session = HibenateUtil.getSessionFactory().openSession();) {
            session.beginTransaction();
            session.remove(session.find(entityClass, id));
            session.getTransaction().commit();
        }
    }

}
