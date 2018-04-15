package hibernate.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double rating;
    String description;
    LocalDateTime createDate;

    @ManyToOne
    User user;

    @ManyToOne
   Product product;

    boolean isVisible;
}
