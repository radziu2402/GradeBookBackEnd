package pl.electronicgradebook.controller;

import org.springframework.http.ResponseEntity;
import pl.electronicgradebook.api.AuthApi;
import pl.electronicgradebook.dto.LoginTO;

public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<Object> login(LoginTO loginTO) {
        return null;
    }
}
