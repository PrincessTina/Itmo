package models.cruds;

import models.entities.Image;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;

@Stateless
public class ImagesCrud extends Crud {
    public ArrayList<Image> readInitImages(ArrayList<Integer> listOfId) {
    ArrayList<Image> links = new ArrayList<Image>();

    for (Integer aListOfId : listOfId) {
      links.add(read(aListOfId));
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
}
