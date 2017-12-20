package cruds;

import table_classes.Note;

import javax.persistence.EntityManager;
import java.sql.Date;

public class Crud_Note extends Crud_Api {
  static Note read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Note.class, id);
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

  static void update(int id, int owner_id, Date date, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Note row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setOwner_id(owner_id);
      row.setDate(date);
      row.setDescription(description);
      row.setImage_id(image_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
