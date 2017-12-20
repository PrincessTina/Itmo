package cruds;

import table_classes.Character;
import javax.persistence.EntityManager;

public class Crud_Character extends Crud_Api{
  static Character read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Character.class, id);
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

  static void update(int id, String name, String type, int father_id, int mother_id, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Character row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setDescription(description);
      row.setFather_id(father_id);
      row.setMother_id(mother_id);
      row.setImage_id(image_id);
      row.setName(name);
      row.setType(type);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
