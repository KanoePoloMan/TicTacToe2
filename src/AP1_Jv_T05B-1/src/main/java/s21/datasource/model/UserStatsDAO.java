package s21.datasource.model;

import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name="user_stats")
@Entity
public class UserStatsDAO {
    @Id
    private UUID uuid;
    private int wins;
    private int loses;
    private int draws;
    private double ratio;
}
