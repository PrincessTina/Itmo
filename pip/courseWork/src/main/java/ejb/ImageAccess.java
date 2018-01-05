package ejb;

import entity.Image;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;

@Stateless
public class ImageAccess extends Access {
  public ArrayList<Image> readInitImages(ArrayList<Integer> listOfId) {
    ArrayList<Image> images = new ArrayList<Image>();

    for (int id : listOfId) {
      images.add(read(id));
    }

    return images;
  }

  private Image read(int id) {
    EntityManager entityManager = generateEntityManager();

    return entityManager.find(Image.class, id);
  }
}
