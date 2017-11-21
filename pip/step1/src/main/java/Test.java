import ejbs.TimeBean;
import models.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
// Показывает, где лежит ресурс
@Path("test")
@Stateless
public class Test {

  @EJB
  private
  TimeBean time;

  @GET @Produces("text/plain")
  public String getClichedMessage() {
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("persistence_unit_1");
    EntityManager em = emFactory.createEntityManager();

    em.getTransaction().begin();

    User u = new User();
    u.setName("Ram");
    u.setDateOfCheck("1997-05-17");
    em.persist(u);

    em.getTransaction().commit();
    em.clear();

    System.out.println("Done");
    return "Hello World: " + time.getCurrentDate().toString();
  }

}