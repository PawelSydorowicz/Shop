package hibernate.shop.servlets;

import hibernate.shop.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@WebServlet(name = "CreateOrderServlet", urlPatterns = "/createOrder")
public class CreateOrderServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User userFromCookie = UserSessionHelper.getUserFromCookie(req.getCookies());
        if(userFromCookie != null){
            Optional<Cart> byUserId = CartRepository.findByUserId(userFromCookie.getId());

            byUserId.ifPresent(x ->createAndSaveOrder(x));
            req.getRequestDispatcher("/orderHistory.jsp").forward(req,resp);
        }



    }

    private void createAndSaveOrder(Cart cart) {
        Order order = new Order();
        order.setTotalGross(cart.getTotalGrossPrice());
        order.setTotalNetto(cart.getTotalNettoPrice());
        order.setUser(cart.getUser());
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setStatusDate(LocalDateTime.now());
        orderHistory.setOrderStatus(OrderStatus.NEW);
        Set<OrderHistory> orderHistorySet = new HashSet<>();
        order.setOrderHistorySet(orderHistorySet);
        order.addOrderHistory(orderHistory);

        order.setOrderDetailList(new HashSet<>());

        for(CartDetail cd :cart.getCartDetailSet()){
            OrderDetail od = new OrderDetail();
            od.setProduct(cd.getProduct());
            od.setPrice(cd.getPrice());
            od.setAmount(cd.getAmount());
            order.addOrderDetail(od);

        }
        OrderRepository.saveOrder(order);
        CartRepository.removeCart(cart);


    }
}
