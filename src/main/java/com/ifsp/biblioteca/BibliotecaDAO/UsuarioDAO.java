package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.UsuarioModel;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    ResponseEntity insert(UsuarioModel usuario);
    ResponseEntity<UsuarioModel> findById(int id);
    ResponseEntity<List<UsuarioModel>> findAll() throws SQLException;
    ResponseEntity<UsuarioModel> update(UsuarioModel livro, int id) throws SQLException;
    void remove(int id);
}
