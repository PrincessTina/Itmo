package ejb.data;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Access {
  public EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
