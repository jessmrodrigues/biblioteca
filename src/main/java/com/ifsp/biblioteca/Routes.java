package com.ifsp.biblioteca;

import com.ifsp.biblioteca.DTO.MySqlLivro;
import com.ifsp.biblioteca.model.LivroModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Routes {
    private final MySqlLivro serviceLivro;

    public Routes(MySqlLivro serviceLivro) {
        this.serviceLivro = serviceLivro;
    }

    @GetMapping(value = "/livros")
    public ResponseEntity<List<LivroModel>> getLivros() {
        return serviceLivro.findAll();
    }

    @GetMapping(value = "/livro/{id}")
    public ResponseEntity<LivroModel> getLivros(@PathVariable("id") int id) throws SQLException {
        return serviceLivro.findById(id);
    }

    @PostMapping(value = "/livro",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLivro(@RequestBody LivroModel livro) throws SQLException {
        return serviceLivro.insert(livro);
    }

    @PutMapping(value = "/livro",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LivroModel> updateCidade(@RequestBody LivroModel livro) throws SQLException {
        return serviceLivro.update(livro);
    }

    @DeleteMapping(value = "/livro",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LivroModel> deleteLivro(@RequestBody LivroModel livro) {
        return serviceLivro.remove(livro);
    }
}
