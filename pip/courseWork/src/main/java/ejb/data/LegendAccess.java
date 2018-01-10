package ejb.data;

import entity.Character;
import entity.Country;
import entity.Legend;
import entity.Users;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
@LocalBean
public class LegendAccess extends Access {
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
}
