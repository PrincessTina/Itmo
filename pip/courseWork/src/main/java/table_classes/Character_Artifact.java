package table_classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Character_Artifact {
  @Id
  @GeneratedValue
  private int id;
  private int character_id;
  private int art_id;

  public Character_Artifact() {
  }

  public Character_Artifact(int character_id, int art_id) {
    this.character_id = character_id;
    this.art_id = art_id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCharacter_id() {
    return character_id;
  }

  public void setCharacter_id(int character_id) {
    this.character_id = character_id;
  }

  public int getArt_id() {
    return art_id;
  }

  public void setArt_id(int art_id) {
    this.art_id = art_id;
  }
}
