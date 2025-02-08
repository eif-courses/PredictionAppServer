package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Dto.LoginRequest;
import eif.viko.lt.predictionappserver.Dto.LoginResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public void register(){

    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        //if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            //String token = jwtUtil.generateToken(loginRequest.getUsername());
            //return new LoginResponse(token); // Return the generated token in response
        //}
        //throw new RuntimeException("Invalid credentials"); // Or throw a custom exception

        return new LoginResponse("=Abaelkjawek2131313123ajoeaaweaeawe");

    }
    public void logout(){

    }
}
