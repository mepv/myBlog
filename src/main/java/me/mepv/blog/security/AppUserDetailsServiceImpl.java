package me.mepv.blog.security;

import me.mepv.blog.dto.ApiResponse;
import me.mepv.blog.dto.UserDTO;
import me.mepv.blog.entity.User;
import me.mepv.blog.exception.UserNameNotAvailableException;
import me.mepv.blog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static me.mepv.blog.security.Role.USER;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public AppUserDetailsServiceImpl(UserRepository userRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
        AppUserDetails appUserDetails = new AppUserDetails(user);
        switch (Role.valueOf(user.getRole().name())) {
            case USER:
                appUserDetails.setGrantedAuthorities(USER.getGrantedAuthorities());
                break;
        }
        return appUserDetails;
    }

    public ApiResponse save(UserDTO userDTO) {
        boolean userExists = userRepository.findByUsername(userDTO.getUsername()).isPresent();
        if (userExists) throw new UserNameNotAvailableException(String.format("User %s already exists.", userDTO.getEmail()));
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        switch (Role.valueOf(userDTO.getRole().name())) {
            case USER:
                user.setRole(USER);
                break;
        }
        userRepository.save(user);
        return new ApiResponse(String.format("User %s saved successfully.", user.getUsername()));
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) return authentication.getName();
        else throw new IllegalStateException("No user logged in.");
    }
}