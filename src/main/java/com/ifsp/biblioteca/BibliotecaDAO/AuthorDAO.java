package com.ifsp.biblioteca.BibliotecaDAO;


import com.ifsp.biblioteca.model.AuthorModel;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface AuthorDAO {
    ResponseEntity insert(AuthorModel author);
    ResponseEntity<AuthorModel> findById(int id);
    ResponseEntity<List<AuthorModel>> findAll() ;
    ResponseEntity<AuthorModel> updated(AuthorModel author, int id);
    void remove(int id);
}
