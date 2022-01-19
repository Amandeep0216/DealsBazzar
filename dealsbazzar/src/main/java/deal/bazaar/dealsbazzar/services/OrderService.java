package deal.bazaar.dealsbazzar.services;

import java.util.List;

import deal.bazaar.dealsbazzar.models.Order;
import deal.bazaar.dealsbazzar.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order addOrder(Order order) {
        try {
            Order data = orderRepository.insert(order);
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Order> getOrders() {
        try {
            List<Order> data = orderRepository.findAll();
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public Order validateId(String orderId) {
        try
        {
            System.out.println(orderId);
            Order o=orderRepository.findById(orderId).get();
            System.out.println(o);
            return o; 
        }catch(Exception e){
            return null;
        }
    }

    public Order updateOrder(Order order) {
        try{
            return orderRepository.save(order);
        }catch(Exception e){
            return null;
        }
    }
    
}
