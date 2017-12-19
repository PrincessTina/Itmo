package beans;

import classes.Point;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DataBean {

  static Point getPoint(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Point.class, id);
    } finally {
      entityManager.close();
    }
  }

  static void addPoint(Point point) {
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.persist(point);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  private static EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
