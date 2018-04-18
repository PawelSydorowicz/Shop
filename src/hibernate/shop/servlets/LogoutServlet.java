package hibernate.shop.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getCookies() != null) {
            Optional<Cookie> emailCookie = Arrays.stream(request.getCookies())
                    .filter(x -> x.getName().equals("email"))
                    .findFirst();

            if (emailCookie.isPresent()) {
                Cookie cookie = emailCookie.get();
                cookie.setMaxAge(1);
                response.addCookie(cookie);
            }
        }

        request.getRequestDispatcher("/index.jsp?isSuccessLogout=true").forward(request, response);

    }
}