package cruds;

import table_classes.Event;

import javax.persistence.EntityManager;

public class Crud_Event extends Crud_Api {
  static Event read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Event.class, id);
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

  static void update(int id, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Event row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDescription(description);
      row.setImage_id(image_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
