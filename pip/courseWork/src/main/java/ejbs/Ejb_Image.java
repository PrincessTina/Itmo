package ejbs;

import entities.Image;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;

@Stateless
public class Ejb_Image {
  public ArrayList<String> readInitImages(ArrayList<Integer> listOfId) {
    ArrayList<String> links = new ArrayList<String>();

    for (Integer aListOfId : listOfId) {
      links.add(read(aListOfId).getLink());
    }

    return links;
  }

  private Image read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Image.class, id);
    } finally {
      entityManager.close();
    }
  }

  private static EntityManager generateEntityManager() {
    return Persistence.createEntityManagerFactory("MyPersistence").createEntityManager();
  }
}
