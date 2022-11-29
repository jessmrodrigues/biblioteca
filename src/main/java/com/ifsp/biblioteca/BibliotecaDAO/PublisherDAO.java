package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.PublisherModel;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface PublisherDAO {
    ResponseEntity insert(PublisherModel publisher);
    ResponseEntity<PublisherModel> findById(int id);
    ResponseEntity<List<PublisherModel>> findAll() throws SQLException;
    ResponseEntity<PublisherModel> update(PublisherModel publisher, int id) throws SQLException;
    void remove(int id);
}
