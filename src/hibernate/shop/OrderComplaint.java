package hibernate.shop;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "orderSet")

public class OrderComplaint implements Serializable {

    @Id
    // ustawienie ze klucz główny tabeli ma byc ustawiany
    // automatycznie przez silnik bazy danych -> autoincrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String message;

    @Enumerated(value = EnumType.STRING)
    ComplaintStatus complaintStatus;

    @ManyToMany(mappedBy = "orderComplaintSet", cascade = CascadeType.ALL)
    // relacja wiele do wiele
            Set<Order> orderSet;
}
