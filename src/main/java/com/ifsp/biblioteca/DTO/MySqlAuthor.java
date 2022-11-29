package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.AuthorDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.AuthorModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MySqlAuthor implements AuthorDAO {
    private Connection connection;

    public MySqlAuthor() {
        this.connection = new Conexao().getConexao();
    }

    @Override
    public ResponseEntity insert(AuthorModel author) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO author(auid, name) VALUES(?,?)");

                stmt.setInt(1, author.getId());
                stmt.setString(2, author.getNome());
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

    @Override
    public ResponseEntity<AuthorModel> findById(int id) {
        if (this.connection != null) {
            try {
                AuthorModel authorModel = new AuthorModel();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM author WHERE auid = " + id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    authorModel.setId(rs.getInt("auid"));
                    authorModel.setNome(rs.getString("name"));
                    return ResponseEntity.ok(authorModel);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<List<AuthorModel>> findAll() {
        if (this.connection != null) {
            try {
                List<AuthorModel> dados = new ArrayList<>();

                PreparedStatement ps = connection.prepareStatement("SELECT * FROM author");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    AuthorModel authorModel = new AuthorModel();
                    authorModel.setId(rs.getInt("auid"));
                    authorModel.setNome(rs.getString("name"));
                    dados.add(authorModel);
                }
                return ResponseEntity.ok(dados);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<AuthorModel> updated(AuthorModel author, int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt =
                        connection.prepareStatement("UPDATE author SET name = ? WHERE auid = " + id);

                stmt.setString(1, author.getNome());

                stmt.execute();
                return ResponseEntity.ok(author);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM author WHERE auid = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
