package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.LivroModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public interface LivroDAO {

     ResponseEntity<String> insert(LivroModel livro) throws SQLException;
     ResponseEntity<LivroModel> findById(int id) throws SQLException;
     ResponseEntity<List<LivroModel>> findAll() throws SQLException;
     ResponseEntity<LivroModel> update(LivroModel livro, int id) throws SQLException;
     void remove(int id);
}
