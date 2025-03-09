package s21.domain.model;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private Collection<Role> authorities;
    @Getter
    private UUID uuid;
    private String username;
    private String password;
}
