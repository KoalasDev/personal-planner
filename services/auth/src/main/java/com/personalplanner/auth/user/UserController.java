package com.personalplanner.auth.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService service;
  public UserController(UserService service) { this.service = service; }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Map<String,String> body) {
    var saved = service.register(body.get("username"), body.get("password"));
    return ResponseEntity.ok(Map.of("id", saved.getId(), "username", saved.getUsername()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
    var u = service.login(body.get("username"), body.get("password"));
    return ResponseEntity.ok(Map.of("id", u.getId(), "username", u.getUsername()));
  }
}
