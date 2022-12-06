package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.OrderModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderDAO {
    ResponseEntity insert(OrderModel order);

    ResponseEntity<OrderModel> findById(int id);

    ResponseEntity<List<OrderModel>> findAll();

    void update(OrderModel order, int id);

    void remove(int id);
}
