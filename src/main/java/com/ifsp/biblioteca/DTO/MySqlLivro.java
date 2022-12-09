package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.LivroDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.LivroModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MySqlLivro implements LivroDAO {
    private Connection connection;

    public MySqlLivro() {
        this.connection = new Conexao().getConexao();
    }

    public ResponseEntity insert(LivroModel livro) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO books(titulo, author, email) VALUES(?,?,?)");

                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAuthor());
                stmt.setString(3, livro.getEmail());
                stmt.execute();
                stmt.close();
                return ResponseEntity.ok("Criado");
            }catch (Exception e) {
                e.getMessage();
            }
            return ResponseEntity.badRequest().body("Falha na request");
        }
        return null;
    }

    public ResponseEntity<List<LivroModel>> findAll() {
        if (this.connection != null) {
            try {
                List<LivroModel> dados = new ArrayList<>();

                PreparedStatement ps = connection.prepareStatement("SELECT * FROM books");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    LivroModel livroModel = new LivroModel();
                    livroModel.setBoid(rs.getInt("boid"));
                    livroModel.setTitulo(rs.getString("titulo"));
                    livroModel.setAuthor(rs.getString("author"));
                    livroModel.setEmail(rs.getString("email"));
                    dados.add(livroModel);
                }
                return ResponseEntity.ok(dados);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public LivroModel findById(int id) {
        if (this.connection != null) {
            try {
                LivroModel livroModel = new LivroModel();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM books WHERE boid = " + id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    livroModel.setBoid(rs.getInt("boid"));
                    livroModel.setTitulo(rs.getString("titulo"));
                    livroModel.setAuthor(rs.getString("author"));
                    livroModel.setEmail(rs.getString("email"));
                    return livroModel;
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    public ResponseEntity<LivroModel> update(LivroModel livro, int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt =
                        connection.prepareStatement("UPDATE books SET titulo = ?, author = ?, email = ? WHERE boid = "+ id);

                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAuthor());
                stmt.setString(3, livro.getEmail());

                stmt.execute();
                return ResponseEntity.ok(livro);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    public void remove(int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM books WHERE boid = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
