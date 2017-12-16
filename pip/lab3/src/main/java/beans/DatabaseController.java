package beans;

import classes.Point;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ManagedBean(name = "database_controller")
@RequestScoped
public class DatabaseController {

  public static Point getPoint(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Point.class, id);
    } finally {
      entityManager.close();
    }
  }

  // Todo: add logic
  public static void addPoint(Point point) {
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
