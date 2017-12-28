package cruds;

import table_classes.Country_Legend;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "countryLegendBean")
public class Crud_Country_Legend extends Crud_Api {
  private int id;
  private int country_id;
  private int legend_id;

  public Country_Legend read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Country_Legend.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Country_Legend row = new Country_Legend(this.country_id, this.legend_id);
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.persist(row);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  public void delete() {
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.remove(read());
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  public void update() {
    EntityManager entityManager = generateEntityManager();
    Country_Legend row = read();

    try {
      entityManager.getTransaction().begin();
      row.setCountry_id(this.country_id);
      row.setLegend_id(this.legend_id);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
