package cruds;

import table_classes.User_Award;

import javax.persistence.EntityManager;

public class Crud_User_Award extends Crud_Api {
  static User_Award read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(User_Award.class, id);
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

  static void update(int id, int user_id, int award_id) {
    EntityManager entityManager = generateEntityManager();
    User_Award row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setUser_id(award_id);
      row.setAward_id(user_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
