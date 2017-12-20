package cruds;

import table_classes.User_Legend;

import javax.persistence.EntityManager;

public class Crud_User_Legend extends Crud_Api {
  static User_Legend read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(User_Legend.class, id);
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

  static void update(int id, int legend_id, int user_id) {
    EntityManager entityManager = generateEntityManager();
    User_Legend row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setUser_id(user_id);
      row.setLegend_id(legend_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
