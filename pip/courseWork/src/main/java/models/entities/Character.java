package models.entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Character {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String type;
  private String description;
  private int image_id;
  private int father_id;
  private int mother_id;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "character_artifact",
      joinColumns = @JoinColumn(name = "character_id"),
      inverseJoinColumns = @JoinColumn(name = "art_id")
  )
  private ArrayList<Artifact> artifacts;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "character_legend",
      joinColumns = @JoinColumn(name = "character_id"),
      inverseJoinColumns = @JoinColumn(name = "legend_id")
  )
  private ArrayList<Legend> legends;

  public Character(String name, String type, String description, int image_id, int father_id, int mother_id) {
    this.name = name;
    this.type = type;
    this.description = description;
    this.image_id = image_id;
    this.father_id = father_id;
    this.mother_id = mother_id;
  }

  Character() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getImage_id() {
    return image_id;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
  }

  public int getFather_id() {
    return father_id;
  }

  public void setFather_id(int father_id) {
    this.father_id = father_id;
  }

  public int getMother_id() {
    return mother_id;
  }

  public void setMother_id(int mother_id) {
    this.mother_id = mother_id;
  }

  public ArrayList<Artifact> getArtifacts() {
    return artifacts;
  }

  public void setArtifacts(ArrayList<Artifact> artifacts) {
    this.artifacts = artifacts;
  }

  public ArrayList<Legend> getLegends() {
    return legends;
  }

  public void setLegends(ArrayList<Legend> legends) {
    this.legends = legends;
  }
}
