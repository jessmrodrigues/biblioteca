package com.ifsp.biblioteca.BibliotecaDAO;

import com.ifsp.biblioteca.model.AccountModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountDAO {
    ResponseEntity insert(AccountModel account);
    ResponseEntity<AccountModel> findById(int id);
    ResponseEntity<List<AccountModel>> findAll();
    ResponseEntity<AccountModel> update(AccountModel email, int id);
    void remove(int id);


}
