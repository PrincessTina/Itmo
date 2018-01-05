//package cruds;
//
//import Rating;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.persistence.EntityManager;
//
//@RequestScoped
//@ManagedBean(name = "ratingBean")
//public class Crud_Rating extends Crud_Api {
//  private int id;
//  private int object_id;
//  private int one;
//  private int two;
//  private int three;
//  private int four;
//  private int five;
//
//  public Rating read() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      return entityManager.find(Rating.class, this.id);
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void create() {
//    Rating row = new Rating(this.object_id, this.one, this.two, this.three, this.four, this.five);
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
//    Rating row = read();
//
//    try {
//      entityManager.getTransaction().begin();
//      row.setObject_id(this.object_id);
//      row.setOne(this.one);
//      row.setTwo(this.two);
//      row.setThree(this.three);
//      row.setFour(this.four);
//      row.setFive(this.five);
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
//  public int getObject_id() {
//    return object_id;
//  }
//
//  public void setObject_id(int object_id) {
//    this.object_id = object_id;
//  }
//
//  public int getOne() {
//    return one;
//  }
//
//  public void setOne(int one) {
//    this.one = one;
//  }
//
//  public int getTwo() {
//    return two;
//  }
//
//  public void setTwo(int two) {
//    this.two = two;
//  }
//
//  public int getThree() {
//    return three;
//  }
//
//  public void setThree(int three) {
//    this.three = three;
//  }
//
//  public int getFour() {
//    return four;
//  }
//
//  public void setFour(int four) {
//    this.four = four;
//  }
//
//  public int getFive() {
//    return five;
//  }
//
//  public void setFive(int five) {
//    this.five = five;
//  }
//}
