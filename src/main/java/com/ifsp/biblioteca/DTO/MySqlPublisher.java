package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.PublisherDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.PublisherModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MySqlPublisher implements PublisherDAO {
    private Connection connection;


    public MySqlPublisher() {
        this.connection = new Conexao().getConexao();
    }

    public ResponseEntity insert(PublisherModel publisher) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO publisher(puid, name, endereco) VALUES(?,?,?)");

                stmt.setInt(1, publisher.getId());
                stmt.setString(2, publisher.getNome());
                stmt.setString(3, publisher.getEndereco());
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

    public ResponseEntity<List<PublisherModel>> findAll() {
        if (this.connection != null) {
            try {
                List<PublisherModel> dados = new ArrayList<>();

                PreparedStatement ps = connection.prepareStatement("SELECT * FROM publisher");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    PublisherModel publisherModel = new PublisherModel();
                    publisherModel.setId(rs.getInt("puid"));
                    publisherModel.setNome(rs.getString("name"));
                    publisherModel.setEndereco(rs.getString("endereco"));
                    dados.add(publisherModel);
                }
                return ResponseEntity.ok(dados);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<PublisherModel> findById(int id) {
        if (this.connection != null) {
            try {
                PublisherModel publisherModel = new PublisherModel();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM publisher WHERE puid = " + id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    publisherModel.setId(rs.getInt("puid"));
                    publisherModel.setNome(rs.getString("name"));
                    publisherModel.setEndereco(rs.getString("endereco"));
                    return ResponseEntity.ok(publisherModel);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    public ResponseEntity<PublisherModel> update(PublisherModel publisher, int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt =
                        connection.prepareStatement("UPDATE publisher SET endereco = ? WHERE puid = " + id);

                stmt.setString(1, publisher.getEndereco());

                stmt.execute();
                return ResponseEntity.ok(publisher);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    public void remove(int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM publisher WHERE puid = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
