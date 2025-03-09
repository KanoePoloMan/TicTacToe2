package s21.web.controller.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import s21.web.controller.DomainController;
import s21.web.model.CurrentGameAIDTO;

@RestController
@RequestMapping("/game/ai")
public class AIGameRestController {
    @Autowired
    private DomainController controller;

    @GetMapping("{UUID}")
    public CurrentGameAIDTO getAIGame(@PathVariable("UUID") String uuid) throws Exception {
        return controller.initStartGame(UUID.fromString(uuid));
    }

    @PostMapping("{UUID}")
    public CurrentGameAIDTO updateAIGameField(
                                @PathVariable("UUID") String uuid, 
                                @RequestBody CurrentGameAIDTO game) throws Exception {
        return controller.updateFieldAndGetNextStep(game);
    }
    @GetMapping("list")
    public List<String> getAiGames(@RequestParam String param) {
        return controller.getAIGames();
    }
}
