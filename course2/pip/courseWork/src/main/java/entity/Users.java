package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Users {
  @Id
  @GeneratedValue
  private int id;
  private String login;
  private String email;
  private String password;
  private Date date_of_check;
  @Transient
  private String name;
  @Transient
  private String icon;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_award",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "award_id")
  )
  private List<Award> awards;

  public Users() {
  }

  public Users(String login, String email, String password, Date date_of_check) {
    this.login = login;
    this.email = email;
    this.password = password;
    this.date_of_check = date_of_check;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public void setDate_of_check(Date date_of_check) {
    this.date_of_check = date_of_check;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
