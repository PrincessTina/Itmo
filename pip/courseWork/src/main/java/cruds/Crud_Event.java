package cruds;

import table_classes.Event;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "eventBean")
public class Crud_Event extends Crud_Api {
  private int id;
  private String description;
  private  int image_id;

  public Event read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Event.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Event row = new Event(this.image_id, this.description);
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
    Event row = read();

    try {
      entityManager.getTransaction().begin();
      row.setDescription(this.description);
      row.setImage_id(this.image_id);
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
}
