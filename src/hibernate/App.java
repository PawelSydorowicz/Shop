package hibernate;

import hibernate.shop.*;

import java.math.BigDecimal;
import java.util.*;

public class App {

    public static void main(String[] args) throws Exception {

        Product ciastko = new Product("Pieguski", ProductType.FOOD, new Price(new BigDecimal(1.99), new BigDecimal(0.99)));
        ProductRepository.saveProduct(ciastko);

        User klient1 = new User((long) 1, "pawelsydorowicz@gmail.com", "test", "Paweł", "Sydorowicz");
        UserRepository.saveUser(klient1);
    }
}
