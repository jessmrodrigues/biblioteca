package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.LivroDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.LivroModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.*;
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

    public ResponseEntity<String> insert(LivroModel livro) throws SQLException {
        //this.connection = new Conexao().getConexao();

        String sql;
        if (this.connection != null) {
            sql = "INSERT INTO books(titulo, author, email) VALUES(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAuthor());
            stmt.setString(3, livro.getEmail());
            stmt.execute();
            stmt.close();
            return ResponseEntity.ok("deu certo");
        }
        return ResponseEntity.badRequest().body("conexão falhou");
    }

    public ResponseEntity<List<LivroModel>> findAll() {
        if (this.connection != null) {
            try {
                List<LivroModel> dados = new ArrayList<>();

                PreparedStatement ps = connection.prepareStatement("SELECT * FROM books");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("entrou");

                    LivroModel livroModel = new LivroModel();
                    livroModel.setBoid(rs.getInt("boid"));
                    livroModel.setTitulo(rs.getString("titulo"));
                    livroModel.setAuthor(rs.getString("author"));
                    livroModel.setEmail(rs.getString("email"));
                    dados.add(livroModel);
                }
                //ps.close();
                // rs.close();
                // connection.close();
                return ResponseEntity.ok(dados);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<LivroModel> findById(int id) {
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
                    return ResponseEntity.ok(livroModel);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }


    public void remove(int id) {
        if (this.connection != null) {
            try {
                LivroModel livroModel = new LivroModel();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM books WHERE boid = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
                //stmt.close();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }

    public ResponseEntity<LivroModel> update(LivroModel livro) throws SQLException {
        String sql = "UPDATE livros SET titulo = ?, author = ?, email = ? WHERE boid = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, livro.getTitulo());
        stmt.setString(2, livro.getAuthor());
        stmt.setString(3, livro.getEmail());
        stmt.execute();
        stmt.close();
        return null;
    }
}
