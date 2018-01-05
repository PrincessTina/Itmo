package entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Phrase {
  @Id
  @GeneratedValue
  private int id;
  private String description;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinTable(
      name = "answer_event",
      joinColumns = @JoinColumn(name = "answer_id"),
      inverseJoinColumns = @JoinColumn(name = "event_id")
  )
  private Event event;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "question_answer",
      joinColumns = @JoinColumn(name = "question_id"),
      inverseJoinColumns = @JoinColumn(name = "answer_id")
  )
  private ArrayList<Phrase> answers;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinTable(
      name = "question_answer",
      joinColumns = @JoinColumn(name = "answer_id"),
      inverseJoinColumns = @JoinColumn(name = "question_id")
  )
  private Phrase question;

  public Phrase() { }

  public Phrase(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public Phrase getQuestion() {
    return question;
  }

  public void setQuestion(Phrase question) {
    this.question = question;
  }

  public ArrayList<Phrase> getAnswers() {
    return answers;
  }

  public void setAnswers(ArrayList<Phrase> answers) {
    this.answers = answers;
  }
}
