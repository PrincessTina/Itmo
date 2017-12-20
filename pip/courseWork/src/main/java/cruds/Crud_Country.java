package cruds;

import table_classes.Country;

import javax.persistence.EntityManager;

public class Crud_Country extends Crud_Api{
    static Country read(int id) {
      EntityManager entityManager = generateEntityManager();

      try {
        return entityManager.find(Country.class, id);
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

    static void update(int id, String name, String description) {
      EntityManager entityManager = generateEntityManager();
      Country country = read(id);

      try {
        entityManager.getTransaction().begin();
        country.setDescription(description);
        country.setName(name);
        entityManager.getTransaction().commit();
      } finally {
        entityManager.close();
      }
    }
}
