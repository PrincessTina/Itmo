import ejbs.TimeBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

// Показывает, где лежит ресурс
@Path("test")
@Stateless
public class Test {

  @EJB
  private
  TimeBean time;

  @GET @Produces("text/plain")
  public String getClichedMessage() {
    return "Hello World: " + time.getCurrentDate().toString();
  }

}