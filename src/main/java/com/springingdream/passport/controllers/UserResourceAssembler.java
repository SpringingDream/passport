package com.springingdream.passport.controllers;

import com.springingdream.passport.model.User;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {
    @Override
    public Resource<User> toResource(User entity) {
        return new Resource<>(entity);
    }
}
