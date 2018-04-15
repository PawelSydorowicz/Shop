package hibernate.shop;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name="orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDetailList", "orderComplaintSet", "orderHistorySet"})

public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    BigDecimal totalNetto;
    BigDecimal totalGross;

    @ManyToOne
    @JoinColumn
    User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //  jedno zamówienie może mieć wiele pozycji
    // wlascicielem relacji jest "orderDetail"
    Set<OrderDetail> orderDetailList;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<OrderHistory> orderHistorySet;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tabela_order_complaint_history",
    joinColumns = @JoinColumn(name="order_complaint_id", referencedColumnName = "id"),
    inverseJoinColumns=@JoinColumn(name="order_id"))
    Set<OrderComplaint> orderComplaintSet;

    public Order(BigDecimal totalGross, User user) {
        this.totalGross = totalGross;
        this.user = user;
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        orderDetailList.add(orderDetail);
    }

    public void addOrderHistory(OrderHistory orderHistory){
        orderHistory.setOrder(this);
        this.orderHistorySet.add(orderHistory);
    }

    public OrderHistory getCurrentOrderHistory(){
        return this.getOrderHistorySet()
                .stream()
                .sorted(Comparator.comparing(OrderHistory::getId)).findFirst()
                .orElse(new OrderHistory());
    }
}
