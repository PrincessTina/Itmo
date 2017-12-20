package cruds;


import table_classes.Author;
import javax.persistence.EntityManager;

public class Crud_Author extends Crud_Api{
  static Author read(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      return entityManager.find(Author.class, id);
    } finally {
      entityManager.close();
    }
  }

  static void delete(int id) {
    EntityManager entityManager = generateEntityManager();

    try {
      entityManager.getTransaction().begin();
      entityManager.remove(read(id));
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }

  static void update(int id, String name, String surname) {
    EntityManager entityManager = generateEntityManager();
    Author row = read(id);

    try {
      entityManager.getTransaction().begin();
      row.setSurname(surname);
      row.setName(name);
      entityManager.getTransaction().commit();
    } finally {
      entityManager.close();
    }
  }
}
