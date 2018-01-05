//package cruds;
//
//import Country;
//import Image;
//import Legend;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//
//@RequestScoped
//@ManagedBean(name = "countryBean")
//public class Crud_Country extends Crud_Api {
//  private int id;
//  private String description;
//  private String name;
//
//  private ArrayList<Legend> legends;
//  private ArrayList<Image> images;
//
//  public Country read() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      return entityManager.find(Country.class, this.id);
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void create() {
//    Country row = new Country(this.name, this.description);
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
//    Country country = read();
//
//    try {
//      entityManager.getTransaction().begin();
//      country.setDescription(this.description);
//      country.setName(this.name);
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
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public ArrayList<Legend> getLegends() {
//    return legends;
//  }
//
//  public void setLegends(ArrayList<Legend> legends) {
//    this.legends = legends;
//  }
//
//  public ArrayList<Image> getImages() {
//    return images;
//  }
//
//  public void setImages(ArrayList<Image> images) {
//    this.images = images;
//  }
//}
