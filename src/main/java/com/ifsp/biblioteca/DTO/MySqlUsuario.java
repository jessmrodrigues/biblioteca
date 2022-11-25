package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.UsuarioDAO;
import com.ifsp.biblioteca.model.UsuarioModel;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class MySqlUsuario implements UsuarioDAO {
    private Connection connection;

    public void insert(UsuarioModel usuario) throws SQLException {

        String sql;
        if (String.valueOf(usuario.getId()).isEmpty()) {
            sql = "INSERT INTO usuario(nome,endereco,telefone) VALUES(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setInt(3, usuario.getTelefone());
            stmt.execute();
            stmt.close();
        }
    }

    public void remove(UsuarioModel usuario) {
        try {
            String sql;
            if (!String.valueOf(usuario.getId()).isEmpty()) {
                sql = "DELETE FROM usuario WHERE IDusuario = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setInt(1, usuario.getId());
                stmt.execute();
                stmt.close();

            }
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public UsuarioModel findById(UsuarioModel usuario) {
        try {
            String sql = "";
            if (!usuario.getNome().isEmpty()) {
                sql = "SELECT * FROM usuario WHERE nome LIKE '%" + usuario.getNome() + "%' ";

            }

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
        return usuario;
    }

    public void update(UsuarioModel usuario) throws SQLException {
        String sql = "UPDATE livros SET nome = ?, endereco = ?, telefone = ? WHERE IDautor = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEndereco());
        stmt.setString(3, usuario.getEndereco());
        stmt.execute();
        stmt.close();
    }

    public ArrayList findAll() {
        try {
            ArrayList dado = new ArrayList();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                dado.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getInt("telefone"),
                });
            }
            ps.close();
            rs.close();
            connection.close();

            return dado;
        } catch (SQLException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, "Erro preencher o ArrayList");
            return null;
        }
    }

}
