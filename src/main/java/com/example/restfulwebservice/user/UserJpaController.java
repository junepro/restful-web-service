package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJpaController {
    private final UserRepo userRepo;

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable int id) {
        Optional<User> user = userRepo.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }
        return user.get();
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        userRepo.deleteById(id);

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepo.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/users/{id}/posts")
    public List<Post> findAllPostsByuSER(@PathVariable int id) {
        Optional<User> user = userRepo.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        return user.get().getPosts();
    }
}
