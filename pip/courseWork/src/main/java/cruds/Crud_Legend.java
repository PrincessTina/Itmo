package cruds;

import table_classes.Legend;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "legendBean")
public class Crud_Legend extends Crud_Api{
  private int id;
  private String name;
  private String description;
  private int image_id;
  private int author_id;
  private int country_id;

  public Legend read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Legend.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Legend row = new Legend(this.name, this.author_id, this.country_id, this.image_id, this.description);
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
    Legend row = read();

    try {
      entityManager.getTransaction().begin();
      row.setDescription(this.description);
      row.setAuthor_id(this.author_id);
      row.setCountry_id(this.country_id);
      row.setImage_id(this.image_id);
      row.setName(this.name);
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

  public int getImage_id() {
    return image_id;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
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
}
