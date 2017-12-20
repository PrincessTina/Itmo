package cruds;

import table_classes.Image;

import javax.persistence.EntityManager;

public class Crud_Image extends Crud_Api{
  static Image read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Image.class, id);
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

  static void update(int id, String link) {
    EntityManager entityManager = generateEntityManager();
    Image row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setLink(link);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
