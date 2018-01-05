//package cruds;
//
//import Award;
//import Users;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//
//@RequestScoped
//@ManagedBean(name = "awardBean")
//public class Crud_Award extends Crud_Api {
//  /*
//  Тест ManyToMany
//  private int id = 1;
//  <h:outputText value="#{awardBean.read().users.get(0).password}"/>
//  */
//  private int id = 1;
//  private String description;
//
//  private ArrayList<Users> users;
//
//  public Award read() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      return entityManager.find(Award.class, this.id);
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void create() {
//    Award row = new Award(this.description);
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      entityManager.getTransaction().begin();
//      entityManager.persist(row);
//      entityManager.getTransaction().commit();
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void delete() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      entityManager.getTransaction().begin();
//      entityManager.remove(read());
//      entityManager.getTransaction().commit();
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void update() {
//    EntityManager entityManager = generateEntityManager();
//    Award row = read();
//
//    try {
//      entityManager.getTransaction().begin();
//      row.setDescription(this.description);
//      entityManager.getTransaction().commit();
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public int getId() {
//    return id;
//  }
//
//  public void setId(int id) {
//    this.id = id;
//  }
//
//  public String getDescription() {
//    return description;
//  }
//
//  public void setDescription(String description) {
//    this.description = description;
//  }
//
//  public ArrayList<Users> getUsers() {
//    return users;
//  }
//
//  public void setUsers(ArrayList<Users> users) {
//    this.users = users;
//  }
//}
