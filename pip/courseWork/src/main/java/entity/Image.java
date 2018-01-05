package entity;

import javax.persistence.*;

@Entity
public class Image {
  @Id
  @GeneratedValue
  private int id;
  private String link;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinTable(
      name = "country_image",
      joinColumns = @JoinColumn(name = "image_id"),
      inverseJoinColumns = @JoinColumn(name = "country_id")
  )
  private Country country;

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

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }
}
