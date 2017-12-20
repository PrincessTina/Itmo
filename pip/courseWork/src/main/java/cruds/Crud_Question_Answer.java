package cruds;

import table_classes.Question_Answer;

import javax.persistence.EntityManager;

public class Crud_Question_Answer extends Crud_Api {
  static Question_Answer read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Question_Answer.class, id);
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

  static void update(int id, int question_id, int answer_id) {
    EntityManager entityManager = generateEntityManager();
    Question_Answer row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setQuestion_id(answer_id);
      row.setAnswer_id(question_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
