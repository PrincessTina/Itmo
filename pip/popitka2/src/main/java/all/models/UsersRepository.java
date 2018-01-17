package all.models;


import all.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "/users")
public interface UsersRepository extends CrudRepository<User, Integer> {}
