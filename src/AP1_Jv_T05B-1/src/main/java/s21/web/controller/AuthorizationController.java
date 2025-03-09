package s21.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class AuthorizationController {
    @GetMapping("/auth")
    public String authorizationGet() throws IOException {   
        return "auth.html";
    }
    @GetMapping("/reg")
    public String registrationGet() throws IOException {
        return "register.html";
    }
    @PostMapping("testPost")
    public String postMethodName(@RequestBody String username, @RequestBody String password) {
        System.out.println(username + " " + password);
        
        return username;
    }
    @GetMapping("/app")
    public String testMethod() {
        return "app.html";
    }
    
}
