package me.mepv.blog.controller;

import me.mepv.blog.dto.ApiResponse;
import me.mepv.blog.dto.UserDTO;
import me.mepv.blog.security.AppUserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class SignUpController {

    private final AppUserDetailsServiceImpl userDetailsService;

    public SignUpController(AppUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userDetailsService.save(userDTO), HttpStatus.OK);
    }
}
