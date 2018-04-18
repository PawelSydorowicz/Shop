package hibernate.shop.servlets;

import hibernate.shop.User;
import hibernate.shop.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        boolean isValid = true;
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            System.out.println("Jedno z pol jest puste!");
            isValid = false;
        }

        if (!password.equals(password2)) {
            System.out.println("Hasla sa rozne!");
            isValid = false;
        }

        Optional<User> byEmail = UserRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            isValid = false;
            System.out.println("Email zajety");
        }

        if (isValid) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            UserRepository.saveUser(user);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);

    }
}
