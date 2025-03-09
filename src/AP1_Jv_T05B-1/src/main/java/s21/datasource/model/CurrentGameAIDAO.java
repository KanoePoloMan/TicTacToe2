package s21.datasource.model;

import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="games_ai")
@Entity
public class CurrentGameAIDAO {
    @Id
    private UUID uuid;
    private UUID player;
    private boolean x;
    @Column(length=10)
    private String field;
    @Column(length=20)
    private String state;
}
