package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.repo.AddressRepository;
import pl.electronicgradebook.security.UserAuthenticationProvider;
import pl.electronicgradebook.service.UserServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Test {

    private final AddressRepository addressRepository;
    private final UserServiceImpl userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @GetMapping("/siema")
    public ResponseEntity<String> siema() {
        return ResponseEntity.ok("SIEMANO");
    }

    @GetMapping("/esa")
    public String esa() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Pobierz role aktualnego użytkownika
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return "Current user roles: " + roles;
    }

    @GetMapping("/hello")
    public String get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Pobierz role aktualnego użytkownika
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return "Current user roles: " + roles;
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
//        UserDto userDto = userService.login(credentialsDto);
//        userDto.setToken(userAuthenticationProvider.createToken(userDto));
//        return ResponseEntity.ok(userDto);
//    }

//    @PostMapping("/register")
//    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user) {
//        UserDto createdUser = userService.register(user);
//        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
//        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
//    }

}
