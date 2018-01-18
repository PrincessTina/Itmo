package all.entity;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cl_seq")
  @SequenceGenerator(name="cl_seq", sequenceName="cl_seq", allocationSize=1)
  private int id;
  private String login;
  private String password;

  public User() {}

  public User(int id, String login, String password) {
    this.login = login;
    this.password = password;
    this.id = id;
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
}
