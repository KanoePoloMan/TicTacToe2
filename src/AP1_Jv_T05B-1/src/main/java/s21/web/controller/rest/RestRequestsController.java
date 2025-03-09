package s21.web.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import s21.web.controller.DomainController;



@RestController
public class RestRequestsController {
    @Autowired
    private DomainController controller;

    @GetMapping("/")
    public String getHelloWorld() {
        return "Hello world!";
    }
    @GetMapping("/game/list")
    public List<String> getAllGames() {
        return controller.getGames();
    }
    @GetMapping("/anon")
    public String getMethodName(@CurrentSecurityContext SecurityContext context) {
        return context.getAuthentication().getName();
    }
}
