package com.moetez.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.moetez.entities.Order;
import com.moetez.repos.OrderRepository;
import com.moetez.service.OrderService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order order = orderService.updateOrder(id, updatedOrder);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/invoice")
    public ResponseEntity<Map<String, Object>> generateInvoice(@RequestParam Long userId) {
        List<Order> pendingOrders = orderService.getPendingOrdersByUserId(userId);
        double invoiceTotal = orderService.calculateInvoiceTotal(pendingOrders);
        
        if (pendingOrders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("user", pendingOrders.get(0).getUser()); 
        response.put("orders", pendingOrders);
        response.put("invoiceTotal", invoiceTotal);
        
        return ResponseEntity.ok(response);
    }
    @GetMapping("/user/{id}")
    public List<Order> getOrdersByUserId(@PathVariable("id") Long userId) {
        return orderService.getOrdersByUserId(userId);
    }


    
}
