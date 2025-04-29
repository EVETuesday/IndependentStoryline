package com.is.client.data;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public final class ClientEnhancedBossEventManager {

    private static ClientEnhancedBossEventManager INSTANCE = null;

    public static void invalidate() {
        INSTANCE = null;
    }

    public static ClientEnhancedBossEventManager getInstance() {
        if (INSTANCE == null) INSTANCE = new ClientEnhancedBossEventManager();
        return INSTANCE;
    }

    final Map<UUID, ClientEnhancedBossEvent> bossBars = Maps.newHashMap();

    private ClientEnhancedBossEventManager() {
    }

    public boolean add(ClientEnhancedBossEvent event) {
        event.add();
        return bossBars.putIfAbsent(event.getId(), event) == null;
    }

    public boolean remove(UUID id) {
        return bossBars.remove(id) != null;
    }

    public boolean markRemoved(UUID id) {
        Optional<ClientEnhancedBossEvent> bossEvent = getBossBar(id);
        bossEvent.ifPresent(ClientEnhancedBossEvent::remove);
        return bossEvent.isPresent();
    }

    public boolean update(ClientEnhancedBossEvent event) {
        if (getBossBar(event.getId()).isEmpty()) {
            return add(event);
        }
        return bossBars.replace(event.getId(), event) != null;
    }

    public Optional<ClientEnhancedBossEvent> getBossBar(Predicate<ClientEnhancedBossEvent> predicate) {
        return bossBars.values().stream().filter(predicate).findAny();
    }

    public Optional<ClientEnhancedBossEvent> getBossBar(UUID id) {
        return Optional.ofNullable(bossBars.get(id));
    }

    public List<ClientEnhancedBossEvent> getAllBossBars() {
        return bossBars.values().stream().toList();
    }
}
