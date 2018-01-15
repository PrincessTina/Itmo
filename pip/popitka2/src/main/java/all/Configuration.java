package all;

import all.entity.User;
import all.models.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
    UsersRepository repository = (UsersRepository) context.getBean("repository");
  }

  @Bean(name = "repository")
  public static UsersRepository repository() {
    return new UsersRepository() {
      @Override
      public User save(User entity) {
        return null;
      }

      @Override
      public Iterable<User> save(Iterable<? extends User> entities) {
        return null;
      }

      @Override
      public User findOne(Integer integer) {
        return null;
      }

      @Override
      public boolean exists(Integer integer) {
        return false;
      }

      @Override
      public Iterable<User> findAll() {
        return null;
      }

      @Override
      public long count() {
        return 0;
      }

      @Override
      public void delete(Integer integer) {

      }

      @Override
      public void delete(User entity) {

      }

      @Override
      public void delete(Iterable<? extends User> entities) {

      }

      @Override
      public void deleteAll() {

      }
    };
  }
}
