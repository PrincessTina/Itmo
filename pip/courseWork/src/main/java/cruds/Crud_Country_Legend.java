package cruds;

import table_classes.Country_Legend;

import javax.persistence.EntityManager;

public class Crud_Country_Legend extends Crud_Api {
  static Country_Legend read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Country_Legend.class, id);
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

  static void update(int id, int legend_id, int country_id) {
    EntityManager entityManager = generateEntityManager();
    Country_Legend row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setCountry_id(country_id);
      row.setLegend_id(legend_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
