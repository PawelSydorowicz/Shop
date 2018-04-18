package hibernate.shop;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"cartDetailSet", "user"})

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CartDetail> cartDetailSet;

    public void addCartDetail(CartDetail cartDetail){
        cartDetail.setCart(this);
        cartDetailSet.add(cartDetail);
    }

    public BigDecimal getTotalGrossPrice(){
        BigDecimal totalGross = cartDetailSet
                .stream()
                .map(cd -> cd.getAmount()
                        .multiply(cd.getPrice()
                        .getGrossPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalGross;
    }

    public BigDecimal getTotalNettoPrice(){
        BigDecimal totalNetto = cartDetailSet
                .stream()
                .map(cd->cd.getAmount()
                .multiply(cd.getPrice()
                .getNettoPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalNetto;
    }
}
