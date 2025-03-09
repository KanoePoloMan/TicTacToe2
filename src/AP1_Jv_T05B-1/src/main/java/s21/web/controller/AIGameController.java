package s21.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import s21.web.model.CurrentGameAIDTO;

@Controller
@RequestMapping("/game/ai")
public class AIGameController {
    @Autowired
    private DomainController controller;

    @GetMapping("newGame")
    public String getNewAIGame(Model model, @CurrentSecurityContext SecurityContext context)  throws Exception {
        CurrentGameAIDTO game = controller.findGameWithAI(context.getAuthentication().getName());

        model.addAttribute("pathUUID", game.getUuid().toString());

        return "gameai.html";
    }
}
