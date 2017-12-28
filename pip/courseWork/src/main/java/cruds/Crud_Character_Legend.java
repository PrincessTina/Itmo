package cruds;

import table_classes.Character_Legend;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

@RequestScoped
@ManagedBean(name = "characterLegendBean")
public class Crud_Character_Legend extends Crud_Api {
  private int id;
  private int character_id;
  private int legend_id;

  public Character_Legend read() {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Character_Legend.class, this.id);
    } finally {
      entityManager.close();
    }
  }

  public void create() {
    Character_Legend row = new Character_Legend(this.legend_id, this.character_id);
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
    Character_Legend row = read();

    try {
      entityManager.getTransaction().begin();
      row.setCharacter_id(this.character_id);
      row.setLegend_id(this.legend_id);
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

  public int getLegend_id() {
    return legend_id;
  }

  public void setLegend_id(int legend_id) {
    this.legend_id = legend_id;
  }
}
