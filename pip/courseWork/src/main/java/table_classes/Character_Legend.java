package table_classes;

import javax.persistence.Entity;

@Entity
public class Character_Legend {
  private int legend_id;
  private int character_id;

  public Character_Legend() {
  }

  public Character_Legend(int legend_id, int character_id) {
    this.legend_id = legend_id;
    this.character_id = character_id;
  }

  public int getLegend_id() {
    return legend_id;
  }

  public void setLegend_id(int legend_id) {
    this.legend_id = legend_id;
  }

  public int getCharacter_id() {
    return character_id;
  }

  public void setCharacter_id(int character_id) {
    this.character_id = character_id;
  }
}
