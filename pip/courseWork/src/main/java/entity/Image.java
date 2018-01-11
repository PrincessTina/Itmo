package entity;

import javax.persistence.*;

@Entity
public class Image {
  @Id
  @GeneratedValue
  private int id;
  private String link;

  public Image () {}

  public Image (String link) {
    this.link = link;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
