package ejbs;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class TimeBean {
  public Date getCurrentDate(){
    return new Date();
  }
}