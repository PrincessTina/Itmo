package cruds;

import table_classes.Author;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "authorBean")
public class Crud_Author extends Crud_Api{
  private int id;
  private String name;
  private String surname;

  public Author read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Author.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Author row = new Author(this.name, this.surname);
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
    Author row = read();

    try {
      entityManager.getTransaction().begin();
      row.setSurname(this.surname);
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

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
}
