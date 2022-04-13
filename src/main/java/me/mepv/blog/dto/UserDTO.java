package me.mepv.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.mepv.blog.security.Role;

import javax.validation.Valid;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Valid
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private Role role;
}
