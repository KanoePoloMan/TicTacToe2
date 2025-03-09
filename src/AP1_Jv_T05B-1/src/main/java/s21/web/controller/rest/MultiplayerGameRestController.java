package s21.web.controller.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import s21.domain.model.jwt.JwtProvider;
import s21.web.controller.DomainController;
import s21.web.model.CurrentGameDTO;
import s21.web.model.WinPlayerInfoDTO;


@RestController
@RequestMapping("/game/multiplayer")
@RequiredArgsConstructor
public class MultiplayerGameRestController {
    private final DomainController controller;
    private final JwtProvider jwtProvider;

    @GetMapping("{UUID}/get")
    public CurrentGameDTO getMultiplayerGameField(@PathVariable("UUID") String uuid) {
        return controller.getMultiplayerGameByUUID(uuid);
    }
    @PostMapping("{UUID}/changes")
    public CurrentGameDTO getChangingsGameField(
                            @PathVariable("UUID") String uuid, 
                            @RequestBody CurrentGameDTO field) throws Exception {
        return controller.checkFieldChanges(uuid, field.getField());
    }
    @PostMapping("{UUID}")
    public CurrentGameDTO updateMultiplayerGameField(
                                @PathVariable("UUID") String uuid, 
                                @RequestBody CurrentGameDTO field) throws Exception {
        return controller.updateMultiplayerField(field);
    }
    @PostMapping("getNameByUUID")
    public String getNameByUUID(@RequestBody String uuid) {
        System.out.println(uuid);
        return controller.getPlayerLogin(UUID.fromString(uuid.replaceAll("\"", "")));
    }
    
    @GetMapping("list")
    public List<String> getMultiplayerGames() {
        return controller.getMultiplayerGames();
    }
    @GetMapping("checkInFoundedList")
    public String getSearchGame(@CurrentSecurityContext SecurityContext context) {
        UUID uuid = controller.checkInFoundedList(context.getAuthentication().getName());
        if(uuid == null) return null;
        return uuid.toString();
    }
    @GetMapping("getAvailableGames")
    public List<CurrentGameDTO> getAvailableGames() {
        return controller.getAvailableGames();
    }
    @GetMapping("getEndedGames")
    public List<CurrentGameDTO> getEndedGames(@RequestHeader("Authorization") String header) throws AuthException {
        final String token = header.substring(7);

        if(!jwtProvider.validateAccessToken(token)) {
            throw new AuthException("Expired or invalid token in getEndGames");
        }

        final Claims claims = jwtProvider.getAccessClaims(token);

        return controller.getEndGames(claims.get("uuid", String.class));
    }
    @PostMapping("getTopPlayers")
    public List<WinPlayerInfoDTO> getTopPlayers(@RequestBody String limit) {
        return controller.getTopPlayers(Integer.parseInt(limit.replace("\"", "")));
    }
    
}
