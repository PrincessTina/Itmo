package cruds;

import table_classes.Country_Image;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "countryImageBean")
public class Crud_Country_Image extends Crud_Api {
  private int id;
  private int country_id;
  private int image_id;

  public Country_Image read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Country_Image.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Country_Image row = new Country_Image(this.country_id, this.image_id);
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
    Country_Image row = read();

    try {
      entityManager.getTransaction().begin();
      row.setCountry_id(this.image_id);
      row.setImage_id(this.country_id);
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

  public int getImage_id() {
    return image_id;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
  }
}
