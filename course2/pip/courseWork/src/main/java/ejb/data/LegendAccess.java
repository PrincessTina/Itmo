package ejb.data;

import entity.Legend;
import entity.User_Legend;
import entity.Users;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class LegendAccess extends Access {

  @EJB
  private ImageAccess images;

  @EJB
  private UsersAccess users;

  public Legend find(String name) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Legend e where e.name = :name");
    query.setParameter("name", name);

    List list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return (Legend) list.get(0);
    }
  }

  public void create(String link, String name, String description, int country_id)
      throws ServletException {

    Integer image_id = null;

    if (!link.isEmpty()) {
      images.create(link);
      image_id = images.findImage(link);
    }

    EntityManager entityManager = generateEntityManager();
    Legend row = new Legend(name, country_id, image_id, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public Legend read(int id) {
    EntityManager entityManager = generateEntityManager();
    Legend legend = entityManager.find(Legend.class, id);

    entityManager.close();

    legend.setImage(images.read(legend.getImage_id()));
    return legend;
  }

  public List<Legend> getTop() throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Legend e order by e.rating desc");

    List<Legend> list = query.setMaxResults(10).getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      for (Legend legend : list) {
        legend.setImage(images.read(legend.getImage_id()));
      }

      return list;
    }
  }

  public void setLike(int id, int user_id) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    User_Legend row = new User_Legend(user_id, id);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public List<Users> getUsers(int id) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from User_Legend e where e.legend_id = :id");
    query.setParameter("id", id);

    List<User_Legend> list = query.getResultList();
    entityManager.close();

    List<Users> usersList = new ArrayList<Users>();

    for (User_Legend user_legend: list) {
      usersList.add(users.read(user_legend.getUser_id()));
    }

    return usersList;
  }
}
