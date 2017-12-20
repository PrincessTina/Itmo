package table_classes;

import javax.persistence.Entity;

@Entity
public class Answer_Event {
  private int answer_id;
  private int event_id;

  public Answer_Event() {
  }

  public Answer_Event(int answer_id, int event_id) {
    this.answer_id = answer_id;
    this.event_id = event_id;
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
