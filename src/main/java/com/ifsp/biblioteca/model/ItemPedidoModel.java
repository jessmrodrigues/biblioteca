package com.ifsp.biblioteca.model;

public class ItemPedidoModel {
    private int id;
    private LivroModel livro;
    private int quantidade;

    public LivroModel getLivro() {
        return livro;
    }
    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
