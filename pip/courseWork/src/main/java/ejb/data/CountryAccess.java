package ejb.data;

import entity.Country;
import entity.Image;
import entity.Legend;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class CountryAccess extends Access {
  public Country find(String name) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Country e where e.name = :name");
    query.setParameter("name", name);

    List<Country> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public Country read(int id) {
    EntityManager entityManager = generateEntityManager();
    Country country = entityManager.find(Country.class, id);

    entityManager.close();
    return country;
  }
}
