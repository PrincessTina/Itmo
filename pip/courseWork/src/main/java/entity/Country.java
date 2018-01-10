package entity;

import javax.persistence.*;
import java.util.List;

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
  private List<Legend> legends;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "country_image",
      joinColumns = @JoinColumn(name = "country_id"),
      inverseJoinColumns = @JoinColumn(name = "image_id")
  )
  private List<Image> images;

  public Country() {}

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

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }

  public List<Legend> getLegends() {
    return legends;
  }

  public void setLegends(List<Legend> legends) {
    this.legends = legends;
  }
}
