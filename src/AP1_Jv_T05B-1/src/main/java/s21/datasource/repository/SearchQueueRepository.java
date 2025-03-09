package s21.datasource.repository;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class SearchQueueRepository {
    @Getter
    private final Queue<UUID> queue = new ConcurrentLinkedQueue<>();

    public void add(UUID uuid) {
        queue.add(uuid);
    }

    public UUID getElem() {
        return queue.poll();
    }
}
