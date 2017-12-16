package beans;

import entities.Point;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name = "database_controller")
@RequestScoped
public class DatabaseController {
  public static Point getPoint(int id) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistence");
    EntityManager em = emf.createEntityManager();

    try{
      Point p =em.find(Point.class, id);
      return p;
    }finally{
      em.close();
    }
  }

  // Todo: add logic
  public void addPoint(int x, int y) {

  }

}
