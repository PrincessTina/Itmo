package ejb.data;

import entity.Artifact;
import entity.Character;
import entity.Legend;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
@LocalBean
public class CharacterAccess extends Access {
  public List<Artifact> getArtifacts(String name) throws ServletException {
    Character character = find(name);

    return character.getArtifacts();
  }

  public List<Legend> getLegends(String name) throws ServletException {
    Character character = find(name);

    return character.getLegends();
  }

  public Character find(String name) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Character e where e.name = :name");
    query.setParameter("name", name);

    List<Character> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
