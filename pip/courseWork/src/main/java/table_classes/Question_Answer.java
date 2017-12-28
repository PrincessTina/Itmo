package table_classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question_Answer {
  @Id
  @GeneratedValue
  private int id;
  private int question_id;
  private int answer_id;

  public Question_Answer() {
  }

  public Question_Answer(int question_id, int answer_id) {
    this.question_id = question_id;
    this.answer_id = answer_id;
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
