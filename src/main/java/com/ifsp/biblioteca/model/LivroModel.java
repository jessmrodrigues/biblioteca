package com.ifsp.biblioteca.model;

public class LivroModel {
    private int boid;
    private String titulo;
    private String author;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBoid() {
        return boid;
    }

    public void setBoid(int boid) {
        this.boid = boid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
