package models.cruds;

import javax.persistence.EntityManager;
import models.entities.Users;
import java.sql.Date;
import java.util.Calendar;

/**
 * Todo: make ejb
 */
public class UsersCrud extends Crud {
  public static void registerUser(String login, String email, String password) {
    Date date = new Date(Calendar.getInstance().getTime().getTime());

    create(login, password, date, email);
  }

  private static void create(String login, String password, Date date_of_check, String email) {
    EntityManager entityManager = generateEntityManager();
    Users row = new Users(login, email, password, date_of_check);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
  }

  private static Users read(int id) {
    EntityManager entityManager = generateEntityManager();

    return entityManager.find(Users.class, id);
  }

  private static void delete(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.remove(read(id));
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  private static void update(int id, String login, String password, String email, Date date_of_check) {
    EntityManager entityManager = generateEntityManager();
    Users row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setEmail(email);
      row.setDate_of_check(date_of_check);
      row.setLogin(login);
      row.setPassword(password);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
