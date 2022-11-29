package com.ifsp.biblioteca.Routes;

import com.ifsp.biblioteca.DTO.MySqlAccount;
import com.ifsp.biblioteca.model.AccountModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class RouteAccount {
    private final MySqlAccount mySqlAccount;

    public RouteAccount(MySqlAccount mySqlAccount) {
        this.mySqlAccount = mySqlAccount;
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createAccount(@RequestBody AccountModel account) {
        return mySqlAccount.insert(account);
    }
    @GetMapping
    public ResponseEntity<List<AccountModel>> getAccounts() {
        return mySqlAccount.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountModel> getAccount(@PathVariable("id") int id) {
        return mySqlAccount.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountModel> updateAccount(@RequestBody AccountModel account, @PathVariable("id") int id) {
        return mySqlAccount.update(account, id);
    }
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) {
        mySqlAccount.remove(id);
    }
}
