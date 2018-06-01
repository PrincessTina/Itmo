package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Character {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String type;
  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "character_artifact",
      joinColumns = @JoinColumn(name = "character_id"),
      inverseJoinColumns = @JoinColumn(name = "art_id")
  )
  private List<Artifact> artifacts;

  public Character(String name, String type, String description) {
    this.name = name;
    this.type = type;
    this.description = description;
  }

  public Character() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Artifact> getArtifacts() {
    return artifacts;
  }
}
