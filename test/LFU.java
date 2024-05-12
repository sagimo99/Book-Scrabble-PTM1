package test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LFU implements CacheReplacementPolicy {
    private final LinkedHashMap<String, Integer> Cache;
    private int min;

    public LFU() {
        this.Cache = new LinkedHashMap<>();
        this.min = 0;
    }

    @Override
    public void add(String word) {
        if (Cache.containsKey(word)) {
            Cache.put(word, Cache.get(word) + 1);
        } else {
            Cache.put(word, 1);
        }
        min = 1;
    }

    @Override
    public String remove() throws NoSuchElementException {
        for (Map.Entry<String, Integer> entry : Cache.entrySet()) {
            if (entry.getValue() == min) {
                String remove = entry.getKey();
                if (remove != null) {
                    min++;
                    return remove;
                }
            }
        }
        throw new NoSuchElementException("Element not found in cache");
    }

}
