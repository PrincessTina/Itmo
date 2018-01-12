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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "character_legend",
      joinColumns = @JoinColumn(name = "legend_id"),
      inverseJoinColumns = @JoinColumn(name = "character_id")
  )
  private List<Character> characters;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_legend",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "legend_id")
  )
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

  public int getCountry_id() {
    return country_id;
  }

  public void setCountry_id(int country_id) {
    this.country_id = country_id;
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

  public List<Character> getCharacters() {
    return characters;
  }

  public void setCharacters(List<Character> characters) {
    this.characters = characters;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public void setImage_id(Integer image_id) {
    this.image_id = image_id;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}
