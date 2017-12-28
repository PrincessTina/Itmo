package cruds;

import table_classes.Question_Answer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "questionAnswerBean")
public class Crud_Question_Answer extends Crud_Api {
  private int id;
  private int question_id;
  private int answer_id;

  public Question_Answer read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Question_Answer.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Question_Answer row = new Question_Answer(this.question_id, this.answer_id);
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.persist(row);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  public void delete() {
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.remove(read());
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  public void update() {
    EntityManager entityManager = generateEntityManager();
    Question_Answer row = read();

    try {
      entityManager.getTransaction().begin();
      row.setQuestion_id(this.answer_id);
      row.setAnswer_id(this.question_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getQuestion_id() {
    return question_id;
  }

  public void setQuestion_id(int question_id) {
    this.question_id = question_id;
  }

  public int getAnswer_id() {
    return answer_id;
  }

  public void setAnswer_id(int answer_id) {
    this.answer_id = answer_id;
  }
}
