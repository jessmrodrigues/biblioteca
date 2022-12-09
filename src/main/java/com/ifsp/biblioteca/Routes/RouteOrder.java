package com.ifsp.biblioteca.Routes;


import com.ifsp.biblioteca.DTO.MySqlOrder;
import com.ifsp.biblioteca.model.LivroModel;
import com.ifsp.biblioteca.model.OrderModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class RouteOrder {
    private final MySqlOrder serviceOrder;

    public RouteOrder(MySqlOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createOrder(@RequestBody OrderModel order) {
        return serviceOrder.insert(order);
    }

    @GetMapping
    public List<OrderModel> getOrder() {
        return serviceOrder.findAll();
    }

    @GetMapping("/{id}")
    public OrderModel getOrder(@PathVariable("id") int id) {
        return serviceOrder.findById(id);
    }
    @PutMapping("/{id}")
    public void updateOrder(@RequestBody OrderModel order, @PathVariable("id") int id) {
         serviceOrder.update(order, id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        serviceOrder.remove(id);
    }

}
