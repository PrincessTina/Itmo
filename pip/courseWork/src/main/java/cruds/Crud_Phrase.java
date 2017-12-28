package cruds;

import table_classes.Phrase;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "phraseBean")
public class Crud_Phrase extends Crud_Api {
  private int id = 1001;
  private String description;

  public Phrase read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Phrase.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Phrase row = new Phrase(this.description);
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
    Phrase row = read();

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
