//package cruds;
//
//import models.entities.Artifact;
//import models.entities.Character;
//import models.entities.Legend;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//
//@RequestScoped
//@ManagedBean(name = "characterBean")
//public class Crud_Character extends Crud_Api{
//  private int id;
//  private String description;
//  private String type;
//  private String name;
//  private int father_id;
//  private int mother_id;
//  private int image_id;
//
//  private ArrayList<Artifact> artifacts;
//  private ArrayList<Legend> legends;
//
//  public Character read() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      return entityManager.find(Character.class, this.id);
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void create() {
//    Character row = new Character(this.name, this.type, this.description, this.image_id, this.father_id, this.mother_id);
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
//    Character row = read();
//
//    try {
//      entityManager.getTransaction().begin();
//      row.setDescription(this.description);
//      row.setFather_id(this.father_id);
//      row.setMother_id(this.mother_id);
//      row.setImage_id(this.image_id);
//      row.setName(this.name);
//      row.setType(this.type);
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
//  public String getType() {
//    return type;
//  }
//
//  public void setType(String type) {
//    this.type = type;
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
//  public int getFather_id() {
//    return father_id;
//  }
//
//  public void setFather_id(int father_id) {
//    this.father_id = father_id;
//  }
//
//  public int getMother_id() {
//    return mother_id;
//  }
//
//  public void setMother_id(int mother_id) {
//    this.mother_id = mother_id;
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
//  public ArrayList<Artifact> getArtifacts() {
//    return artifacts;
//  }
//
//  public void setArtifacts(ArrayList<Artifact> artifacts) {
//    this.artifacts = artifacts;
//  }
//
//  public ArrayList<Legend> getLegends() {
//    return legends;
//  }
//
//  public void setLegends(ArrayList<Legend> legends) {
//    this.legends = legends;
//  }
//}
