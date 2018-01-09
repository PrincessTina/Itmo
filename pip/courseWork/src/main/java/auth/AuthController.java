package auth;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "auth", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

  @EJB
  private AuthLogic auth;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String code = request.getParameter("code");
    String service = request.getParameter("address").substring(0, 2);
    String address = request.getParameter("address").substring(2);

    if (code == null) {
      response.sendRedirect(address);
    } else {
      if (service.equals("vk")) {
        auth.authorizeWithVk(code, response, service, address, request);
      } else if (service.equals("in")) {
        auth.authorizeWithIn(code, response, service, address, request);
      } else {
        throw new ServletException("Unknown service: " + service);
      }
    }
  }
}
