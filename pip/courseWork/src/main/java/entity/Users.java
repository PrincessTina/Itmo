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

  public String getEmail() {
    return email;
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

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
