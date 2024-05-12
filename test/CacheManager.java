package test;

import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;

public class CacheManager {
    private int maxSize;
    private CacheReplacementPolicy crp;
    private Set<String> cache;

    public CacheManager(int maxSize, CacheReplacementPolicy crp) {
        this.maxSize = maxSize;
        this.crp = crp;
        this.cache = new HashSet<>(maxSize);
    }

    public boolean query(String word) {
        return cache.contains(word);
    }

    public void add(String word) {
        crp.add(word);
        cache.add(word);
        if (cache.size() > getCacheSize()) {
            cache.remove(crp.remove());
        }
    }

    public int getCacheSize() {
        return maxSize;
    }
}
