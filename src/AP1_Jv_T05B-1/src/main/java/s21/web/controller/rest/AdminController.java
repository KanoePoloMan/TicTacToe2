package s21.web.controller.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import s21.domain.model.jwt.JwtProvider;
import s21.domain.service.UserService;
import s21.web.mapper.UserDomainWebMapper;
import s21.web.model.UserDTO;




@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    private final UserDomainWebMapper toWebMapper = UserDomainWebMapper.INSTANCE;

    @GetMapping("getallusers")
    public List<String> getAllUsers() throws IOException {
        return userService.getAll().stream().map(param -> param.toString()).toList();
    }
    @GetMapping("getUser/{UUID}")
    public UserDTO getUser(@PathVariable("UUID") String uuid) {
        return toWebMapper.domainToWeb(userService.getUserByUUID(UUID.fromString(uuid.replaceAll("\"", ""))));
    }
    @GetMapping("getUser")
    public ResponseEntity<?> getUserByToken(@RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        
        if(!jwtProvider.validateAccessToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        final Claims claims = jwtProvider.getAccessClaims(token);

        return ResponseEntity.ok(Map.of("username", claims.getSubject(), 
                                        "uuid", claims.get("uuid", String.class),
                                        "roles", claims.get("roles", List.class)));
    }
    
    
}
