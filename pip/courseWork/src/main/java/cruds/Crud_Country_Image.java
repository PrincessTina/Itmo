package cruds;

import table_classes.Country_Image;

import javax.persistence.EntityManager;

public class Crud_Country_Image extends Crud_Api {
  static Country_Image read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Country_Image.class, id);
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

  static void update(int id, int country_id, int image_id) {
    EntityManager entityManager = generateEntityManager();
    Country_Image row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setCountry_id(image_id);
      row.setImage_id(country_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
