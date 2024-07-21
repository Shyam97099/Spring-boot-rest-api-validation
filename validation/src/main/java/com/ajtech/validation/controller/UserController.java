package com.ajtech.validation.controller;

import com.ajtech.validation.entity.User;
import com.ajtech.validation.request.UserRequest;
import com.ajtech.validation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        user.setEmail(userRequest.getEmail());
        user.setNationality(userRequest.getNationality());
        user.setGender(userRequest.getGender());
        userService.saveUser(user);

        return new ResponseEntity<>("User is valid", HttpStatus.OK);
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
      return  userService.getUserById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id){
          userService.deleteUserById(id);
    }
}
