package s21.datasource.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name="users")
@Entity
public class UserDAO {
    @Id
    private UUID uuid;
    @Column(length=30)
    private String login;
    @Column(length=128)
    private String password;
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name = "user_roles",
        joinColumns=@JoinColumn(name = "user_uuid")
    )
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();
}
