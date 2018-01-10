package ejb.data;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Stateless
@LocalBean
public class Access {
  public EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
