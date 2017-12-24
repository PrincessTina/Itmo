package cruds;

import table_classes.Phrase;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;

@Path("phraseBean")
@Stateful(name = "phraseBean")
public class Crud_Phrase extends Crud_Api {
  private int id;
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
