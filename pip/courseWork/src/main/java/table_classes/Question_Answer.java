package table_classes;

import javax.persistence.Entity;

@Entity
public class Question_Answer {
  private int question_id;
  private int answer_id;

  public Question_Answer() {
  }

  public Question_Answer(int question_id, int answer_id) {
    this.question_id = question_id;
    this.answer_id = answer_id;
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
