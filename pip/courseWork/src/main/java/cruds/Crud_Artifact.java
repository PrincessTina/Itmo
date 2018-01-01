package cruds;

import entities.Artifact;
import entities.Character;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import java.util.ArrayList;

@RequestScoped
@ManagedBean(name ="artifactBean")
public class Crud_Artifact extends Crud_Api {
  private int id;
  private int image_id;
  private String description;

  private ArrayList<Character> characters;

  public Artifact read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Artifact.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Artifact row = new Artifact(this.image_id, this.description);
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
    Artifact row = read();

    try {
      entityManager.getTransaction().begin();
      row.setDescription(this.description);
      row.setImage_id(this.image_id);
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

  public int getImage_id() {
    return image_id;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<Character> getCharacters() {
    return characters;
  }

  public void setCharacters(ArrayList<Character> characters) {
    this.characters = characters;
  }
}
