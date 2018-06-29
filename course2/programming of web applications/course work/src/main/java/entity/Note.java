package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;

@Entity
public class Note {
  @Id
  @GeneratedValue
  private int id;
  private int image_id;
  private Date date;
  private String description;
  @Transient
  private Image image;

  public Note() {
  }

  public Note(int image_id, Date date, String description) {
    this.image_id = image_id;
    this.date = date;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }
}
