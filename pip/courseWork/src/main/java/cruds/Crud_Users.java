package cruds;
/**
import models.entities.Award;
import models.entities.Legend;
import models.entities.Users;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.ArrayList;

@RequestScoped
@ManagedBean(name = "usersBean")
public class Crud_Users extends Crud_Api {
  private int id;
  private String login;
  private String password;
  private Date date_of_check;

  private ArrayList<Award> awards;
  private ArrayList<Legend> legends;

  public Users read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Users.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Users row = new Users(this.login, this.password, this.date_of_check);
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
    Users row = read();

    try {
      entityManager.getTransaction().begin();
      row.setDate_of_check(this.date_of_check);
      row.setLogin(this.login);
      row.setPassword(this.password);
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

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getDate_of_check() {
    return date_of_check;
  }

  public void setDate_of_check(Date date_of_check) {
    this.date_of_check = date_of_check;
  }

  public ArrayList<Award> getAwards() {
    return awards;
  }

  public void setAwards(ArrayList<Award> awards) {
    this.awards = awards;
  }

  public ArrayList<Legend> getLegends() {
    return legends;
  }

  public void setLegends(ArrayList<Legend> legends) {
    this.legends = legends;
  }
}
*/