package cruds;

import table_classes.Award;

import javax.persistence.EntityManager;

public class Crud_Award extends Crud_Api {
  static Award read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Award.class, id);
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

  static void update(int id, String description) {
    EntityManager entityManager = generateEntityManager();
    Award row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDescription(description);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
