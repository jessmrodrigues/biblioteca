package com.ifsp.biblioteca.Routes;

import com.ifsp.biblioteca.DTO.MySqlAuthor;
import com.ifsp.biblioteca.model.AuthorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class RouteAuthor {
    private final MySqlAuthor mySqlAuthor;

    public RouteAuthor(MySqlAuthor serviceLivro) {
        this.mySqlAuthor = serviceLivro;
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createAuthor(@RequestBody AuthorModel author) {
        return mySqlAuthor.insert(author);
    }
    @GetMapping
    public ResponseEntity<List<AuthorModel>> getAuthor() {
        return mySqlAuthor.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorModel> getAuthor(@PathVariable("id") int id) {
        return mySqlAuthor.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorModel> updateAuthor(@RequestBody AuthorModel author, @PathVariable("id") int id) {
        return mySqlAuthor.updated(author, id);
    }
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) {
        mySqlAuthor.remove(id);
    }
}
