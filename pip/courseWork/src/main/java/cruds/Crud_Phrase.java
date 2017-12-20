package cruds;

import table_classes.Phrase;

import javax.persistence.EntityManager;

public class Crud_Phrase extends Crud_Api {
  static Phrase read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Phrase.class, id);
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
    Phrase row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDescription(description);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
