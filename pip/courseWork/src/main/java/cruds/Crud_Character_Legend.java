package cruds;

import table_classes.Character_Legend;

import javax.persistence.EntityManager;

public class Crud_Character_Legend extends Crud_Api {
  static Character_Legend read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Character_Legend.class, id);
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

  static void update(int id, int legend_id, int character_id) {
    EntityManager entityManager = generateEntityManager();
    Character_Legend row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setCharacter_id(character_id);
      row.setLegend_id(legend_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
