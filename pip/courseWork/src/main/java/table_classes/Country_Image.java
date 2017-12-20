package table_classes;

import javax.persistence.Entity;

@Entity
public class Country_Image {
  private int country_id;
  private int image_id;

  public Country_Image() {
  }

  public Country_Image(int country_id, int image_id) {
    this.country_id = country_id;
    this.image_id = image_id;
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
}
