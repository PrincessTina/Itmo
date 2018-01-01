package cruds;

import ejbs.Ejb_Image;
import entities.Country;
import entities.Image;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import java.util.ArrayList;

@RequestScoped
@ManagedBean(name = "imageBean")
public class Crud_Image extends Crud_Api {
  private int id;
  private String link;
  private ArrayList<String> images = new ArrayList<String>();

  private Country country;

  @EJB
  private Ejb_Image ejb = new Ejb_Image();

  @PostConstruct
  public void init() {
    ArrayList<Integer> listOfId = new ArrayList<Integer>();
    listOfId.add(0);
    listOfId.add(1);
    listOfId.add(2);
    listOfId.add(3);
    listOfId.add(4);

    this.images = ejb.readInitImages(listOfId);
  }

  public ArrayList<String> getImages() {
    return images;
  }

  public Image read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Image.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Image row = new Image(this.link);
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
    Image row = read();

    try {
      entityManager.getTransaction().begin();
      row.setLink(this.link);
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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }
}
