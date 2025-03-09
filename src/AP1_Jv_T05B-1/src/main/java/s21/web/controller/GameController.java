package s21.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class GameController {
    
    // @GetMapping("/test")
    // public String getTest() {
    //     return "gameai.html";
    // }
    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("nickname", SecurityContextHolder.getContext().getAuthentication().getName());
        return "menu.html";
    }
    @GetMapping("/statistic")
    public String statistic() {
        return "statistic.html";
    }
    @GetMapping("/top")
    public String top() {
        return "top.html";
    }
    
}
