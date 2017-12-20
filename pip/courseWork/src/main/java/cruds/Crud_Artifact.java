package cruds;

import table_classes.Artifact;

import javax.persistence.EntityManager;

public class Crud_Artifact extends Crud_Api {
  static Artifact read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Artifact.class, id);
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

  static void update(int id, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Artifact row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDescription(description);
      row.setImage_id(image_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
