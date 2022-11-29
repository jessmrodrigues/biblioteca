package com.ifsp.biblioteca.Routes;

import com.ifsp.biblioteca.DTO.MySqlLivro;
import com.ifsp.biblioteca.model.LivroModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class RouteLivro {
    private final MySqlLivro serviceLivro;

    public RouteLivro(MySqlLivro serviceLivro) {
        this.serviceLivro = serviceLivro;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createLivro(@RequestBody LivroModel livro) {
        return serviceLivro.insert(livro);
    }

    @GetMapping
    public ResponseEntity<List<LivroModel>> getLivros() {
        return serviceLivro.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroModel> getLivros(@PathVariable("id") int id) {
        return serviceLivro.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroModel> updateCidade(@RequestBody LivroModel livro, @PathVariable("id") int id) {
        return serviceLivro.update(livro, id);
    }

    @DeleteMapping("/{id}")
    public void deleteLivro(@PathVariable int id) {
        serviceLivro.remove(id);
    }
}