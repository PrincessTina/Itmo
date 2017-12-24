package cruds;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

class Crud_Api {
  static EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
