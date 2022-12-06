package com.ifsp.biblioteca.DTO;

import com.ifsp.biblioteca.BibliotecaDAO.LivroDAO;
import com.ifsp.biblioteca.BibliotecaDAO.OrderDAO;
import com.ifsp.biblioteca.Conexao;
import com.ifsp.biblioteca.model.ItemPedidoModel;
import com.ifsp.biblioteca.model.LivroModel;
import com.ifsp.biblioteca.model.OrderModel;
import com.ifsp.biblioteca.model.UsuarioModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MySqlOrder implements OrderDAO {
    private LivroDAO livroDAO;
    private Connection connection;

    public MySqlOrder() {
        this.connection = new Conexao().getConexao();
    }

    @Override
    public ResponseEntity insert(OrderModel order) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO orderp(orid, date, fkusid , endereco, pagamento) VALUES(?,?,?,?,?)"
                        , PreparedStatement.RETURN_GENERATED_KEYS);
                UsuarioModel usuario = order.getUsuario();

                if (usuario != null) {
                    stmt.setInt(1, order.getId());
                    stmt.setString(2, order.getDate());
                    stmt.setInt(3, usuario.getId());
                    stmt.setString(4, order.getEndereco());
                    stmt.setString(5, order.getPagamento());

                    stmt.executeUpdate();
                    ResultSet idOrdemVenda = stmt.getGeneratedKeys();
                    //idOrdemVenda.next();

                    if(idOrdemVenda.next()) {
                        System.out.println("TESTE: "+ idOrdemVenda.getInt(1));
                    }

                    int idOV = idOrdemVenda.getInt(1);

                    PreparedStatement ps2 = this.connection.prepareStatement("INSERT INTO orderitens(fkorid, fkboid, qtde) VALUES (?,?,?)");

                    for (ItemPedidoModel item : order.getItemPedido()) {
                        LivroModel livro = item.getLivro();
                        if (livro != null) {
                            ps2.setInt(1, idOV);
                            ps2.setLong(2, livro.getBoid());
                            ps2.setInt(3, item.getQuantidade());
                            ps2.executeUpdate();

                            LivroModel livroTemp;
                            livroTemp = livroDAO.findById(livro.getBoid()).getBody();
                            livroDAO.update(livroTemp, livro.getBoid());
                        }
                    }
                    return ResponseEntity.ok("Ordem criada");
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<OrderModel> findById(int id) {
        return null;
    }

    @Override
    public ResponseEntity<List<OrderModel>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<OrderModel> update(OrderModel order, int id) {
        return null;
    }

    @Override
    public void remove(int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM orderp WHERE orid = ?");

                stmt.setInt(1, id);

                stmt.executeUpdate();
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }

    }
}
