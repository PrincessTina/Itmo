package ejb.data;

import entity.Character;
import entity.Country;
import entity.Legend;
import entity.Users;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class LegendAccess extends Access {

  @EJB
  private ImageAccess images;

  @EJB
  private AuthorAccess authors;

  public List<Users> getUsers(String name) throws ServletException {
    Legend legend = find(name);

    return legend.getUsers();
  }

  public List<Character> getCharacters(String name) throws ServletException {
    Legend legend = find(name);

    return legend.getCharacters();
  }

  public Country getCountry(String name) throws ServletException {
    Legend legend = find(name);

    return legend.getCountry();
  }

  public Legend find(String name) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Legend e where e.name = :name");
    query.setParameter("name", name);

    List<Legend> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public void create(String authorName, String authorSurname, String link, String name, String description, int country_id)
      throws ServletException {

    Integer image_id = null;
    Integer author_id = null;

    if (!link.isEmpty()) {
      images.create(link);
      image_id = images.findImage(link);
    }

    if (!authorName.isEmpty()) {
      authors.create(authorName, authorSurname);
      author_id = authors.findAuthor(authorName, authorSurname);
    }

    EntityManager entityManager = generateEntityManager();
    Legend row = new Legend(name, author_id, country_id, image_id, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
