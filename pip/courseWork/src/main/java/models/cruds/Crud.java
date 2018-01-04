package models.cruds;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Crud {
  public EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
