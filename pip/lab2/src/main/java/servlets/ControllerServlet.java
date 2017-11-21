package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "main", urlPatterns = {"/main"})
public class ControllerServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    ServletContext context = getServletContext();

    if (action == null || action.equals("result")) {
      context.setAttribute("x", request.getParameter("x"));
      context.setAttribute("y", request.getParameter("y"));
      context.setAttribute("r", request.getParameter("r"));
      context.setAttribute("condition", true);

      request.getRequestDispatcher("/checker").forward(request, response);
    } else if (action.equals("return")) {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
  }
}