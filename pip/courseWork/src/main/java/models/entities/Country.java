package models.entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Country {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String description;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "country_legend",
      joinColumns = @JoinColumn(name = "country_id"),
      inverseJoinColumns = @JoinColumn(name = "legend_id")
  )
  private ArrayList<Legend> legends;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "country_image",
      joinColumns = @JoinColumn(name = "country_id"),
      inverseJoinColumns = @JoinColumn(name = "image_id")
  )
  private ArrayList<Image> images;

  Country() {}

  public Country(String name, String description) {
    this.name = name;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<Image> getImages() {
    return images;
  }

  public void setImages(ArrayList<Image> images) {
    this.images = images;
  }

  public ArrayList<Legend> getLegends() {
    return legends;
  }

  public void setLegends(ArrayList<Legend> legends) {
    this.legends = legends;
  }
}
