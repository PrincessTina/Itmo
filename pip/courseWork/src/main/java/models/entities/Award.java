package models.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Award {
  @Id
  @GeneratedValue
  private int id;
  private String description;

  /*@ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_award",
      joinColumns = @JoinColumn(name = "award_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<Users> users;
  */

  public Award() {
  }

  public Award(String description) {
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
}
