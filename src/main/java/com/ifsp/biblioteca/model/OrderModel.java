package com.ifsp.biblioteca.model;

import java.util.List;

public class OrderModel {
   private int id;
   private String date;
   private UsuarioModel usuario;
   private String endereco;
   private String pagamento;
   private List<ItemPedidoModel> itemPedido;

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public List<ItemPedidoModel> getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(List<ItemPedidoModel> itemPedido) {
        this.itemPedido = itemPedido;
    }

    public OrderModel(){}
    public OrderModel(int id, String date, UsuarioModel usuario, String endereco, String pagamento){
       this.id = id;
       this.date = date;
       this.usuario = usuario;
       this.endereco = endereco;
       this.pagamento = pagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsuario(UsuarioModel cliente) {
        this.usuario = cliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
