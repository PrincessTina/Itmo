package ejb;

import javax.ejb.Local;

@Local
public interface IHelloWorld {
  public String sayHello(String name);
}
