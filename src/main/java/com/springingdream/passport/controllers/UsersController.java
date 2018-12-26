package com.springingdream.passport.controllers;

import com.springingdream.passport.model.User;
import com.springingdream.passport.exceptions.UserNotFoundException;
import com.springingdream.passport.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/passport")
public class UsersController {
    private final UserRepository repository;
    private final UserResourceAssembler assembler;

    @Autowired
    public UsersController(UserRepository repository, UserResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping(path = "/auth", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<User> auth(@RequestBody User user) {
        User u = repository.findByLogin(user.getLogin()).orElseThrow(() -> new UserNotFoundException(0L));
        if (!u.getPasswordHash().equals(user.getPasswordHash()))
            throw new UserNotFoundException(0L);
        return pack(u);
    }

    @GetMapping
    public List<User> list() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).peek(u -> {
            u.setLogin(null);
            u.setPasswordHash(null);
            u.setImageUrl(null);
        }).collect(Collectors.toList());
    }

    @GetMapping(path = "/{uid}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<User> get(@PathVariable Long uid) {
        return pack(findOrDie(uid));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<User> insert(@RequestBody User user) {
        return pack(repository.save(user));
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<User> update(@RequestBody User user) {
        User updated = findOrDie(user.getUid());
        if (user.getLogin() != null)
            updated.setLogin(user.getLogin());
        if (user.getPasswordHash() != null)
            updated.setPasswordHash(user.getPasswordHash());
        if (user.getImageUrl() != null)
            updated.setImageUrl(user.getImageUrl());
        updated = repository.save(updated);

        return pack(updated);
    }

    @DeleteMapping(path = "/{uid}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<User> delete(@PathVariable Long uid) {
        User deleted = findOrDie(uid);
        repository.deleteById(uid);
        return pack(deleted);
    }

    private Resource<User> pack(User user) {
        return assembler.toResource(user);
    }

    private User findOrDie(Long uid) {
        if (uid == null)
            throw new UserNotFoundException(null);

        return repository.findById(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));
    }
}
