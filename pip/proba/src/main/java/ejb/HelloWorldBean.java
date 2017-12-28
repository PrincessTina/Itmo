package ejb;

import javax.ejb.Stateless;

@Stateless
public class HelloWorldBean {
  public String sayHello(String name) {
    return String.format("Hello %s, welcome to EJB 3.1!", name);
  }
}
