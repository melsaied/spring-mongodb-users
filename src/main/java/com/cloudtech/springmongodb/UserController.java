package com.cloudtech.springmongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository repository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<User>> getAll() {
        List<User> result = repository.findAll();
        if (result == null || result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Optional<User>> getById(@PathVariable("id") String id) {
        Optional<User> result = repository.findById(id);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping(value = "/filter0/{param0}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<User>> getByPrice(@PathVariable("param0") String param0) {
        List<User> result = repository.findByEmail(param0);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> create(@RequestBody User hotel) {
        User result = repository.insert(hotel);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
            return ResponseEntity.created(uri).body(result);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> update(@RequestBody User hotel, @PathVariable String id) {
        User result = repository.save(hotel);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> update(@RequestBody User hotel) {
        User result = repository.save(hotel);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity delete(@PathVariable("id") String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> delete(@RequestBody User object) {
        repository.delete(object);
        return ResponseEntity.ok().build();
    }
}