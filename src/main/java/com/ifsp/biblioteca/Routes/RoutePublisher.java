package com.ifsp.biblioteca.Routes;

import com.ifsp.biblioteca.DTO.MySqlPublisher;
import com.ifsp.biblioteca.model.PublisherModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
public class RoutePublisher {
    private final MySqlPublisher servicePublisher;

    public RoutePublisher(MySqlPublisher serviceLivro) {
        this.servicePublisher = serviceLivro;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createLivro(@RequestBody PublisherModel publisher) {
        return servicePublisher.insert(publisher);
    }
    @GetMapping
    public ResponseEntity<List<PublisherModel>> getLivros() {
        return servicePublisher.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherModel> getLivros(@PathVariable("id") int id) {
        return servicePublisher.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherModel> updateCidade(@RequestBody PublisherModel publisher, @PathVariable("id") int id) {
        return servicePublisher.update(publisher, id);
    }
    @DeleteMapping("/{id}")
    public void deleteLivro(@PathVariable int id) {
        servicePublisher.remove(id);
    }
}
