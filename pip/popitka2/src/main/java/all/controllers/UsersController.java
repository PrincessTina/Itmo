package all.controllers;

import all.entity.User;
import all.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {
  private final UsersRepository repository;

  @Autowired
  public UsersController(UsersRepository repository) {
    this.repository = repository;
  }

  @RequestMapping(value = "users", method = RequestMethod.POST)
  public ResponseEntity user(@RequestParam String login, @RequestParam String password) {
    User user = new User(login, password);

    repository.save(user);

    User user1 = repository.findOne(10);

    throw new NullPointerException("user: " + user1);

    //return new ResponseEntity<Object>(HttpStatus.OK);
  }
}
