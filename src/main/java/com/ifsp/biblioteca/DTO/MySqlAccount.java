package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.AccountDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.AccountModel;
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
public class MySqlAccount implements AccountDAO {
    private Connection connection;

    public MySqlAccount() {
        this.connection = new Conexao().getConexao();
    }

    @Override
    public ResponseEntity insert(AccountModel account) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO account(id, email, password) VALUES(?,?,?)");

                stmt.setInt(1, account.getId());
                stmt.setString(2, account.getEmail());
                stmt.setString(3, account.getPassword());
                stmt.execute();
                stmt.close();
                return ResponseEntity.ok("Criado");
            } catch (Exception e) {
                e.getMessage();
            }
            return ResponseEntity.badRequest().body("Falha na criação da conta");
        }
        return null;
    }

    @Override
    public ResponseEntity<AccountModel> findById(int id) {
        if (this.connection != null) {
            try {
                AccountModel accountModel = new AccountModel();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE id = " + id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    accountModel.setId(rs.getInt("id"));
                    accountModel.setEmail(rs.getString("email"));
                    accountModel.setPassword(rs.getString("password"));
                    return ResponseEntity.ok(accountModel);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<List<AccountModel>> findAll() {
        if (this.connection != null) {
            try {
                List<AccountModel> dados = new ArrayList<>();

                PreparedStatement ps = connection.prepareStatement("SELECT * FROM account");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    AccountModel accountModel = new AccountModel();
                    accountModel.setId(rs.getInt("id"));
                    accountModel.setEmail(rs.getString("email"));
                    accountModel.setPassword(rs.getString("password"));
                    dados.add(accountModel);
                }
                return ResponseEntity.ok(dados);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<AccountModel> update(AccountModel account, int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt =
                        connection.prepareStatement("UPDATE account SET email = ?, password = ? WHERE id = " + id);

                stmt.setString(1, account.getEmail());
                stmt.setString(2, account.getPassword());

                stmt.execute();
                return ResponseEntity.ok(account);
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
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM account WHERE id = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
