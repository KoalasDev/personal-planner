package com.personalplanner.auth.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
  private final UserRepository repo;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public User register(String username, String password) {
    if (repo.existsByUsername(username)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
    }
    User u = new User();
    u.setUsername(username);
    u.setPasswordHash(encoder.encode(password));
    return repo.save(u);
  }

  public User login(String username, String password) {
    User u = repo.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials"));
    if (!encoder.matches(password, u.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials");
    }
    return u;
  }
}
