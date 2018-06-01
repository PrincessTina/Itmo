package ejb.data;

import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Controller {
  public JSONObject getAnswerFromPost(HttpServletRequest request) throws IOException, JSONException {
    BufferedReader reader = request.getReader();
    StringBuilder builder = new StringBuilder();
    String line;

    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }

    return new JSONObject(builder.toString());
  }
}
