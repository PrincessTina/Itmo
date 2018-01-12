package ejb.data;

import entity.Image;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ImageAccess extends Access {
  public ArrayList<Image> readInitImages(ArrayList<Integer> listOfId) {
    ArrayList<Image> images = new ArrayList<Image>();

    for (int id : listOfId) {
      images.add(read(id));
    }

    return images;
  }

  public Image read(int id) {
    EntityManager entityManager = generateEntityManager();
    Image image = entityManager.find(Image.class, id);

    entityManager.close();
    return image;
  }

  public void create(String link) {
    EntityManager entityManager = generateEntityManager();
    Image row = new Image(link);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  /**
   *
   * @param link
   * @return id or -1
   * @throws ServletException
   */
  public int findImage(String link) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e.id from Image e where e.link = :link");
    query.setParameter("link", link);

    List<Integer> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return -1;
    } else {
      return list.get(0);
    }
  }
}
