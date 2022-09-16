package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            final String sql = "CREATE TABLE IF NOT EXISTS users (user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(45) NOT NULL, second_name VARCHAR(45) NOT NULL, age INT NOT NULL )";

            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            final String sqlToDrop = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sqlToDrop).executeUpdate();
            transaction.commit();

        } catch (Exception exception) {
            System.out.println("Не удалось удалить таблицу пользователей");
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception exception) {
            System.out.println("Не удалось добавить пользователей в таблицу");
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User userToBeDeleted = session.get(User.class, id);
            if (userToBeDeleted != null) {
                session.delete(userToBeDeleted);
            }
            transaction.commit();
        } catch (
                Exception exception) {
            System.out.println("Не удалось удалить пользователя из таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {

            String hql = "From " + User.class.getSimpleName();
            return session.createQuery(hql, User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (
                Exception exception) {
            System.out.println("Не удалось очистить таблицу");
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }
}
