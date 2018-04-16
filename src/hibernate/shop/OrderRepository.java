package hibernate.shop;

import hibernate.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    public static Optional<Order> findOrder(Long id){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailList od WHERE o.id = :id ";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            Optional<Order> order = Optional.ofNullable((Order) query.getSingleResult());

            return order;
        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }

    }

    public static void saveOrder(Order order){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(order);
            session.getTransaction().commit();
        }catch (Exception ex){
            ex.printStackTrace();
            if(session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
        }finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public static List<Order> findAllDTO(){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT new hibernate.shop.Order(p.totalGross, p.userEmail) FROM Order p";
            Query query = session.createQuery(jpql);
            return query.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
            return Collections.emptyList();
        }finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public static List<Order> findAll(){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailList od ";
            Query query = session.createQuery(jpql);
            return query.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
            return Collections.emptyList();
        }finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public static List<Order> findAllByUserId(Long userId, int offset){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.id DESC";
            Query query = session.createQuery(jpql);
            query.setParameter("userId", userId);
            query.setMaxResults(15);
            query.setFirstResult(offset);
            return query.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
            return Collections.emptyList();
        }finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }


    public static List<Order> findAllOrderWithProduct(Long productId){
        Session session = null;
        try{
            session = HibernateUtil.openSession();
            String jpql = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailList od WHERE od.product.id = :id";
//            String jpql2 = "SELECT o FROM OrderDetail od LEFT JOIN od.order o WHERE od.product.id = :id";
            Query query = session.createQuery(jpql);
            query.setParameter("id", productId);
            return query.getResultList();

        }catch (Exception ex){
            ex.printStackTrace();
            return Collections.emptyList();
        }finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }
}
