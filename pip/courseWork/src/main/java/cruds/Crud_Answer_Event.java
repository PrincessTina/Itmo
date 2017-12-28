package cruds;

import table_classes.Answer_Event;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "answerEventBean")
public class Crud_Answer_Event extends Crud_Api {
  private int id;
  private int answer_id;
  private int event_id;

  public Answer_Event read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Answer_Event.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Answer_Event row = new Answer_Event(this.answer_id, this.event_id);
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
    Answer_Event row = read();

    try {
      entityManager.getTransaction().begin();
      row.setAnswer_id(this.event_id);
      row.setEvent_id(this.answer_id);
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

  public int getAnswer_id() {
    return answer_id;
  }

  public void setAnswer_id(int answer_id) {
    this.answer_id = answer_id;
  }

  public int getEvent_id() {
    return event_id;
  }

  public void setEvent_id(int event_id) {
    this.event_id = event_id;
  }
}
