package models.cruds;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Crud {
  public static EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
