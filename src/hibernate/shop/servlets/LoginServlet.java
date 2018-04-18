package hibernate.shop.servlets;

import hibernate.shop.User;
import hibernate.shop.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.font.OpenType;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String password = request.getParameter("password");
        String email = request.getParameter("email");

        System.out.println("password: " + password + " email: " + email);

        Optional<User> byEmailAndPassword = UserRepository.findByEmailAndPassword(email, password);
        byEmailAndPassword.ifPresent(x -> System.out.println(x.getId()));

        if(byEmailAndPassword.isPresent()){
            response.addCookie(new Cookie("email", byEmailAndPassword.get().getEmail()));
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
