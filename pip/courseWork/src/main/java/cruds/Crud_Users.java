package cruds;

import table_classes.Users;

import javax.persistence.EntityManager;
import java.sql.Date;

public class Crud_Users extends Crud_Api {
  static Users read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Users.class, id);
    } finally {
      entityManager.close();
    }
  }

  static void delete(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.remove(read(id));
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  static void update(int id, String login, String password, Date date_of_check) {
    EntityManager entityManager = generateEntityManager();
    Users row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDate_of_check(date_of_check);
      row.setLogin(login);
      row.setPassword(password);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
