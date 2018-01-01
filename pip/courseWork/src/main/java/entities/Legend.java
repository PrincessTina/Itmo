package entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Legend {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private int author_id;
  private int country_id;
  private int image_id;
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinTable(
      name = "country_legend",
      joinColumns = @JoinColumn(name = "legend_id"),
      inverseJoinColumns = @JoinColumn(name = "country_id")
  )
  private Country country;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "character_legend",
      joinColumns = @JoinColumn(name = "legend_id"),
      inverseJoinColumns = @JoinColumn(name = "character_id")
  )
  private ArrayList<Legend> characters;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_legend",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "legend_id")
  )
  private ArrayList<Users> users;

  public Legend() {
  }

  public Legend(String name, int author_id, int country_id, int image_id, String description) {
    this.name = name;
    this.author_id = author_id;
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

  public int getAuthor_id() {
    return author_id;
  }

  public void setAuthor_id(int author_id) {
    this.author_id = author_id;
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

  public ArrayList<Legend> getCharacters() {
    return characters;
  }

  public void setCharacters(ArrayList<Legend> characters) {
    this.characters = characters;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public ArrayList<Users> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<Users> users) {
    this.users = users;
  }
}
