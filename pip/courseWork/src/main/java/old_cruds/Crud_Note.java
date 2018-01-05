//package cruds;
//
//import Note;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.persistence.EntityManager;
//import java.sql.Date;
//
//@RequestScoped
//@ManagedBean(name = "noteBean")
//public class Crud_Note extends Crud_Api {
//  private int id;
//  private int owner_id;
//  private int image_id;
//  private String description;
//  private Date date;
//
//  public Note read() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      return entityManager.find(Note.class, this.id);
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void create() {
//    Note row = new Note(this.owner_id, this.image_id, this.date, this.description);
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
//    Note row = read();
//
//    try {
//      entityManager.getTransaction().begin();
//      row.setOwner_id(this.owner_id);
//      row.setDate(this.date);
//      row.setDescription(this.description);
//      row.setImage_id(this.image_id);
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
//  public int getOwner_id() {
//    return owner_id;
//  }
//
//  public void setOwner_id(int owner_id) {
//    this.owner_id = owner_id;
//  }
//
//  public int getImage_id() {
//    return image_id;
//  }
//
//  public void setImage_id(int image_id) {
//    this.image_id = image_id;
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
//  public Date getDate() {
//    return date;
//  }
//
//  public void setDate(Date date) {
//    this.date = date;
//  }
//}
