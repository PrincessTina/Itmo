package cruds;

import table_classes.Rating;

import javax.persistence.EntityManager;

public class Crud_Rating extends Crud_Api {
  static Rating read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Rating.class, id);
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

  static void update(int id, int object_id, int one, int two, int three, int four, int five) {
    EntityManager entityManager = generateEntityManager();
    Rating row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setObject_id(object_id);
      row.setOne(one);
      row.setTwo(two);
      row.setThree(three);
      row.setFour(four);
      row.setFive(five);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
