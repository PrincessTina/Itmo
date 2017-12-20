package cruds;

import table_classes.Character_Artifact;

import javax.persistence.EntityManager;

public class Crud_Character_Artifact extends Crud_Api {
  static Character_Artifact read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Character_Artifact.class, id);
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

  static void update(int id, int art_id, int character_id) {
    EntityManager entityManager = generateEntityManager();
    Character_Artifact row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setCharacter_id(character_id);
      row.setArt_id(art_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
