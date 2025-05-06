package com.moetez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moetez.entities.Order;
import com.moetez.repos.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        double prixArticle = order.getArticle().getPrix();
        double total = prixArticle * order.getQuantite();
        order.setTotal(total);

        return orderRepository.save(order);
    }


    public Order updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);

        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setQuantite(updatedOrder.getQuantite());
            existingOrder.setArticle(updatedOrder.getArticle());
            existingOrder.setUser(updatedOrder.getUser());

            double prixArticle = updatedOrder.getArticle().getPrix();
            double total = prixArticle * updatedOrder.getQuantite();
            existingOrder.setTotal(total);

            return orderRepository.save(existingOrder);
        }

        return null;
    }


    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    /*public List<Order> getPendingOrdersByUserName(String nameUser) {
        return orderRepository.findByStatusAndUser_NameUserContainingIgnoreCase("Pending", nameUser);
    }*/

    public double calculateInvoiceTotal(List<Order> orders) {
        return orders.stream()
                    .mapToDouble(Order::getTotal)
                    .sum();
    }
    public List<Order> getPendingOrdersByUserId(Long userId) {
        return orderRepository.findByStatusAndUser_IdUser("Pending", userId);
    }
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_IdUser(userId);
    }


}
