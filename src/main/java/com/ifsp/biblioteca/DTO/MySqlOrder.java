package com.ifsp.biblioteca.DTO;

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
import java.util.ArrayList;
import java.util.List;

@Service
public class MySqlOrder implements OrderDAO {
    private MySqlLivro livroDAO = new MySqlLivro();
    private MySqlUsuario usuarioDAO = new MySqlUsuario();
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

                    if (idOrdemVenda.next()) {
                        System.out.println("TESTE: " + idOrdemVenda.getInt(1));
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
                            livroTemp = livroDAO.findById(livro.getBoid());
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
    public OrderModel findById(int id) {
        OrderModel order = new OrderModel();

        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orderp where orid = " + id);
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM orderitens where fkorid = " + id);

                ResultSet rsPedido = stmt.executeQuery();

                if (rsPedido.next()) {
                    UsuarioModel usuario = usuarioDAO.findById(rsPedido.getInt("fkusid"));

                    if (usuario != null) {
                        order.setId(rsPedido.getInt("orid"));
                        order.setDate(rsPedido.getString("date"));
                        order.setUsuario(usuario);
                        order.setEndereco(rsPedido.getString("endereco"));
                        order.setPagamento(rsPedido.getString("pagamento"));

                        ArrayList<ItemPedidoModel> itens = new ArrayList<>();

                        ResultSet rsItens = stmt2.executeQuery();
                        while (rsItens.next()) {
                            LivroModel livro = livroDAO.findById(rsItens.getInt("fkboid"));
                            if (livro != null) {
                                ItemPedidoModel item = new ItemPedidoModel();
                                item.setId(rsItens.getInt("fkorid"));
                                item.setLivro(livro);
                                item.setQuantidade(rsItens.getInt("qtde"));
                                itens.add(item);
                            }
                        }
                        order.setItemPedido(itens);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return order;
    }

    @Override
    public List<OrderModel> findAll() {
        ArrayList<OrderModel> pedidos = new ArrayList<>();
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orderp");
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM orderitens");

                ResultSet rsPedido = stmt.executeQuery();

                while (rsPedido.next()) {
                    UsuarioModel usuario = usuarioDAO.findById(rsPedido.getInt("fkusid"));
                    if (usuario != null) {
                        OrderModel order = new OrderModel();
                        order.setId(rsPedido.getInt("orid"));
                        order.setDate(rsPedido.getString("date"));
                        order.setUsuario(usuario);
                        order.setEndereco(rsPedido.getString("endereco"));
                        order.setPagamento(rsPedido.getString("pagamento"));

                        ArrayList<ItemPedidoModel> itens = new ArrayList<>();

                        ResultSet rsItens = stmt2.executeQuery();
                        while (rsItens.next()) {
                            LivroModel livro = livroDAO.findById(rsItens.getInt("fkboid"));
                            if (livro != null) {
                                ItemPedidoModel item = new ItemPedidoModel();
                                item.setId(rsItens.getInt("fkorid"));
                                item.setLivro(livro);
                                item.setQuantidade(rsItens.getInt("qtde"));
                                itens.add(item);
                            }
                        }
                        order.setItemPedido(itens);
                        pedidos.add(order);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return pedidos;
    }

    @Override
    public void update(OrderModel order, int id) {
        if (this.connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("UPDATE orderp SET date = ?, fkusid = ?, endereco = ?, pagamento = ? where orid = " + id);
                UsuarioModel usuario = order.getUsuario();
                if (usuario != null) {
                    stmt.setString(1, order.getDate());
                    stmt.setInt(2, usuario.getId());
                    stmt.setString(3, order.getEndereco());
                    stmt.setString(4, order.getPagamento());
                    stmt.executeUpdate();

                    PreparedStatement stmt2 = this.connection.prepareStatement("DELETE FROM orderitens WHERE fkorid = ?");
                    stmt2.setInt(1, id);
                    stmt2.executeUpdate();

                    PreparedStatement stm3 = this.connection.prepareStatement("INSERT INTO orderitens(fkorid, fkboid, qtde) VALUES (?,?,?)");

                    for (ItemPedidoModel item : order.getItemPedido()) {
                        LivroModel livro = item.getLivro();
                        if (livro != null) {
                            stm3.setInt(1, order.getId());
                            stm3.setLong(2, livro.getBoid());
                            stm3.setInt(3, item.getQuantidade());
                            stm3.executeUpdate();
                        }
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

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
