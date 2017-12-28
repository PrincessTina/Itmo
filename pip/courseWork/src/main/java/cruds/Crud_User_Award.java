package cruds;

import table_classes.User_Award;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "userAwardBean")
public class Crud_User_Award extends Crud_Api {
  private int id;
  private int award_id;
  private int user_id;

  public User_Award read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(User_Award.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    User_Award row = new User_Award(this.user_id, this.award_id);
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
    User_Award row = read();

    try {
      entityManager.getTransaction().begin();
      row.setUser_id(this.award_id);
      row.setAward_id(this.user_id);
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

  public int getAward_id() {
    return award_id;
  }

  public void setAward_id(int award_id) {
    this.award_id = award_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
}
