package com.ifsp.biblioteca.Routes;

import com.ifsp.biblioteca.DTO.MySqlUsuario;
import com.ifsp.biblioteca.model.UsuarioModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class RouteUser {
    private final MySqlUsuario serviceUsuario;

    public RouteUser(MySqlUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createLivro(@RequestBody UsuarioModel user) {
        return serviceUsuario.insert(user);
    }
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getLivros() {
        return serviceUsuario.findAll();
    }

    @GetMapping("/{id}")
    public UsuarioModel getLivros(@PathVariable("id") int id) {
        return serviceUsuario.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> updateCidade(@RequestBody UsuarioModel user, @PathVariable("id") int id) {
        return serviceUsuario.update(user, id);
    }
    @DeleteMapping("/{id}")
    public void deleteLivro(@PathVariable int id) {
        serviceUsuario.remove(id);
    }
}
