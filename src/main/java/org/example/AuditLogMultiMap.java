package org.example;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class AuditLogMultiMap<K, V> {

    private final ConcurrentHashMap<K, KeyEntry<V>> store = new ConcurrentHashMap<>();

    /* ===================== CORE OPERATIONS ===================== */

    public void addValue(K key, V value) {
        store.compute(key, (k, entry) -> {
            if (entry == null) {
                entry = new KeyEntry<>();
                entry.log("KEY_CREATED");
            }
            boolean added = entry.values.add(value);
            if (added) {
                entry.log("VALUE_ADDED: " + value);
            }
            return entry;
        });
    }

    public void removeValue(K key, V value) {
        store.computeIfPresent(key, (k, entry) -> {
            if (entry.values.remove(value)) {
                entry.log("VALUE_REMOVED: " + value);
            }
            return entry;
        });
    }

    public void removeKey(K key) {
        KeyEntry<V> removed = store.remove(key);
        if (removed != null) {
            removed.log("KEY_REMOVED");
        }
    }

    /* ===================== QUERY METHODS ===================== */

    public Set<K> keysWithNoValues() {
        return store.entrySet()
                .stream()
                .filter(e -> e.getValue().values.isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public int totalKeys() {
        return store.size();
    }

    public int totalValuesForKey(K key) {
        KeyEntry<V> entry = store.get(key);
        return entry == null ? 0 : entry.values.size();
    }

    public Map<K, Integer> valuesCountPerKey() {
        return store.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().values.size()
                ));
    }

    public List<String> auditHistory(K key) {
        KeyEntry<V> entry = store.get(key);
        return entry == null ? List.of() : List.copyOf(entry.auditLog);
    }

    /* ===================== JSON EXPORT ===================== */

    public Map<K, Object> toJsonCompatibleMap() {
        Map<K, Object> result = new HashMap<>();
        store.forEach((k, v) -> {
            Map<String, Object> node = new HashMap<>();
            node.put("values", v.values);
            node.put("auditLog", v.auditLog);
            result.put(k, node);
        });
        return result;
    }

    /* ===================== INTERNAL ENTRY ===================== */

    private static class KeyEntry<V> {
        private final Set<V> values = ConcurrentHashMap.newKeySet();
        private final List<String> auditLog = new CopyOnWriteArrayList<>();

        void log(String action) {
            auditLog.add(Instant.now() + " :: " + action);
        }
    }
}
