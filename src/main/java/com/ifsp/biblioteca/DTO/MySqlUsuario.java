package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.UsuarioDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.UsuarioModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MySqlUsuario implements UsuarioDAO {
    private Connection connection;


    public MySqlUsuario() {
        this.connection = new Conexao().getConexao();
    }

    public ResponseEntity insert(UsuarioModel usuario) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO user(usid, name) VALUES(?,?)");

                stmt.setInt(1, usuario.getId());
                stmt.setString(2, usuario.getNome());
                stmt.execute();
                stmt.close();
                return ResponseEntity.ok("Criado");
            } catch (Exception e) {
                e.getMessage();
            }
            return ResponseEntity.badRequest().body("Falha na request");
        }
        return null;
    }

    public ResponseEntity<List<UsuarioModel>> findAll() {
        if (this.connection != null) {
            try {
                List<UsuarioModel> dados = new ArrayList<>();

                PreparedStatement ps = connection.prepareStatement("SELECT * FROM user");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    UsuarioModel usuarioModel = new UsuarioModel();
                    usuarioModel.setId(rs.getInt("usid"));
                    usuarioModel.setNome(rs.getString("name"));
                    dados.add(usuarioModel);
                }
                return ResponseEntity.ok(dados);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<UsuarioModel> findById(int id) {
        if (this.connection != null) {
            try {
                UsuarioModel usuarioModel = new UsuarioModel();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE usid = " + id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    usuarioModel.setId(rs.getInt("usid"));
                    usuarioModel.setNome(rs.getString("name"));
                    return ResponseEntity.ok(usuarioModel);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    public ResponseEntity<UsuarioModel> update(UsuarioModel usuario, int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt =
                        connection.prepareStatement("UPDATE user SET name = ? WHERE usid = " + id);

                stmt.setString(1, usuario.getNome());

                stmt.execute();
                return ResponseEntity.ok(usuario);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    public void remove(int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM user WHERE usid = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
