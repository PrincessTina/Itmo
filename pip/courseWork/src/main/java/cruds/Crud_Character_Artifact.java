package cruds;

import table_classes.Character_Artifact;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "characterArtifactBean")
public class Crud_Character_Artifact extends Crud_Api {
  private int id;
  private int character_id;
  private int art_id;

  public Character_Artifact read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Character_Artifact.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Character_Artifact row = new Character_Artifact(this.character_id, this.art_id);
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
    Character_Artifact row = read();

    try {
      entityManager.getTransaction().begin();
      row.setCharacter_id(this.character_id);
      row.setArt_id(this.art_id);
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

  public int getCharacter_id() {
    return character_id;
  }

  public void setCharacter_id(int character_id) {
    this.character_id = character_id;
  }

  public int getArt_id() {
    return art_id;
  }

  public void setArt_id(int art_id) {
    this.art_id = art_id;
  }
}
