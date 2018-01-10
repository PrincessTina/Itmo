package ejb.data;

import entity.Note;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 * Класс для доступа к таблице Note
 */
@Stateless
public class NoteAccess extends Access {
  public Note getById(int id) {
    EntityManager entityManager = generateEntityManager();

    return entityManager.find(Note.class, id);
  }
}
