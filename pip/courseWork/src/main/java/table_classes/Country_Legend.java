package table_classes;

import javax.persistence.Entity;

@Entity
public class Country_Legend {
  private int country_id;
  private int legend_id;

  public Country_Legend() {
  }

  public Country_Legend(int country_id, int legend_id) {
    this.country_id = country_id;
    this.legend_id = legend_id;
  }

  public int getCountry_id() {
    return country_id;
  }

  public void setCountry_id(int country_id) {
    this.country_id = country_id;
  }

  public int getLegend_id() {
    return legend_id;
  }

  public void setLegend_id(int legend_id) {
    this.legend_id = legend_id;
  }
}
