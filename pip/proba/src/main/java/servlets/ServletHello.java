package servlets;

import ejb.HelloWorldBean;
import ejb.IHelloWorld;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "sayHello", urlPatterns = {"/sayHello"})
public class ServletHello extends HttpServlet {
  @EJB
  private HelloWorldBean hello;

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String answer = hello.sayHello(request.getParameter("name"));
      request.getSession().setAttribute("answer", answer);

      RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
      rd.forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e.getMessage());
    }
  }
}
