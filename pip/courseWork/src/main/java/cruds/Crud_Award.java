package cruds;

import table_classes.Award;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "awardBean")
public class Crud_Award extends Crud_Api {
  private int id;
  private String description;

  public Award read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Award.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Award row = new Award(this.description);
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
    Award row = read();

    try {
      entityManager.getTransaction().begin();
      row.setDescription(this.description);
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
}
