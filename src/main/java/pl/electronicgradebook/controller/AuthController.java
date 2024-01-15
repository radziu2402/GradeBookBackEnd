package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.api.AuthApi;
import pl.electronicgradebook.dto.JwtResultDto;
import pl.electronicgradebook.dto.LoginDTO;
import pl.electronicgradebook.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController implements AuthApi {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<Object> login(LoginDTO loginDTO) {
        JwtResultDto jwtResult = userService.login(loginDTO);

        if (jwtResult.isSuccess()) {
            return new ResponseEntity<>(jwtResult, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
}
