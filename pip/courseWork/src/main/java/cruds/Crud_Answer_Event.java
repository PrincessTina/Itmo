package cruds;

import table_classes.Answer_Event;

import javax.persistence.EntityManager;

public class Crud_Answer_Event extends Crud_Api {
  static Answer_Event read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Answer_Event.class, id);
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

  static void update(int id, int answer_id, int event_id) {
    EntityManager entityManager = generateEntityManager();
    Answer_Event row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setAnswer_id(event_id);
      row.setEvent_id(answer_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
