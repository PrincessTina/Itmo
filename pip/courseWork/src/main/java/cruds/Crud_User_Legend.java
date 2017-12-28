package cruds;

import table_classes.User_Legend;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "userLegendBean")
public class Crud_User_Legend extends Crud_Api {
  private int id;
  private int user_id;
  private int legend_id;

  public User_Legend read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(User_Legend.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    User_Legend row = new User_Legend(this.user_id, this.legend_id);
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
    User_Legend row = read();

    try {
      entityManager.getTransaction().begin();
      row.setUser_id(this.user_id);
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

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getLegend_id() {
    return legend_id;
  }

  public void setLegend_id(int legend_id) {
    this.legend_id = legend_id;
  }
}
