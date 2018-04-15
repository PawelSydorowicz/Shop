package hibernate.shop;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Embeddable
public class Price {

    BigDecimal nettoPrice;
    BigDecimal grossPrice;

    @Transient
    BigDecimal vatPrice;

    public Price(){

    }

    public Price(BigDecimal nettoPrice, BigDecimal grossPrice) {
        this.nettoPrice = nettoPrice;
        this.grossPrice = grossPrice;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public BigDecimal getVatPrice() {
        return vatPrice;
    }

    public void setVatPrice(BigDecimal vatPrice) {
        this.vatPrice = vatPrice;
    }
}
