package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Legend {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private int country_id;
  private Integer image_id;
  private String description;
  private int rating;
  @Transient
  private Image image;
  @Transient
  private List<Users> users;

  public Legend() {
  }

  public Legend(String name, int country_id, Integer image_id, String description) {
    this.name = name;
    this.country_id = country_id;
    this.image_id = image_id;
    this.description = description;
  }

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

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public List<Users> getUsers() {
    return users;
  }

  public void setUsers(List<Users> users) {
    this.users = users;
  }
}
