package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.LivroModel;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LivroDAO {

     ResponseEntity<String> insert(LivroModel livro) throws SQLException;
     ResponseEntity<LivroModel> findById(int livro) throws SQLException;
     ResponseEntity<List<LivroModel>> findAll() throws SQLException;
     ResponseEntity<LivroModel> update(LivroModel livro) throws SQLException;
     ResponseEntity<LivroModel> remove(LivroModel livro);
}
