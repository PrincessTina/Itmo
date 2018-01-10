package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Artifact {
  @Id
  @GeneratedValue
  private int id;
  private int image_id;
  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "character_artifact",
      joinColumns = @JoinColumn(name = "art_id"),
      inverseJoinColumns = @JoinColumn(name = "character_id")
  )
  private List<entity.Character> characters;

  public Artifact() {
  }

  public Artifact(int image_id, String description) {
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

  public List<entity.Character> getCharacters() {
    return characters;
  }

  public void setCharacters(List<entity.Character> characters) {
    this.characters = characters;
  }
}
