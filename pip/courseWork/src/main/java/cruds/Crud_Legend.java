package cruds;

import table_classes.Legend;

import javax.persistence.EntityManager;

public class Crud_Legend extends Crud_Api{
  static Legend read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Legend.class, id);
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

  static void update(int id, String name, int author_id, int country_id, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Legend row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDescription(description);
      row.setAuthor_id(author_id);
      row.setCountry_id(country_id);
      row.setImage_id(image_id);
      row.setName(name);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
