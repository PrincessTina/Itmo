package entity;

import javax.persistence.*;

@Entity
public class Event {
  @Id
  @GeneratedValue
  private int id;
  private int image_id;
  private String description;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinTable(
      name = "answer_event",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "answer_id")
  )
  private Phrase answer;

  public Event() {
  }

  public Event(int image_id, String description) {
    this.image_id = image_id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getImage_id() {
    return image_id;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Phrase getAnswer() {
    return answer;
  }

  public void setAnswer(Phrase answer) {
    this.answer = answer;
  }
}
