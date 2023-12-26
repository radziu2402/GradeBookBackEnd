package pl.electronicgradebook.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.electronicgradebook.dto.LoginTO;

@RequestMapping("api/v1")
public interface AuthApi {

    @PostMapping("login")
    ResponseEntity<Object> login(@RequestBody LoginTO loginTO);
}
