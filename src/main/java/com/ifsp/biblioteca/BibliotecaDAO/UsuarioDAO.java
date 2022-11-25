package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.UsuarioModel;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UsuarioDAO {
    void insert(UsuarioModel usuario) throws SQLException;
    UsuarioModel findById(UsuarioModel client);
    ArrayList findAll() throws SQLException;
    void update(UsuarioModel usuario) throws SQLException;
    void remove(UsuarioModel usuario);
}
