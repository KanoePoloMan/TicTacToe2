package s21.datasource.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name="games_multiplayer")
@Entity
public class CurrentGameDAO {
    @Id
    private UUID uuid;
    private UUID x;
    private UUID o;
    @Column(length=10)
    private String field;
    @Column(length=20)
    private String state;
    private LocalDateTime date;
}
