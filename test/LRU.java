package test;

import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {
    private final LinkedHashSet<String> cache;
    private final int maxSize;

    public LRU() {
        this.cache = new LinkedHashSet<>();
        this.maxSize = 100;

    }

    @Override
    public void add(String word) {
        this.cache.remove(word);
        this.cache.add(word);

        if (this.cache.size() > this.maxSize) {
            String leastRecentlyUsed = this.remove();
            this.cache.remove(leastRecentlyUsed);
        }
    }

    @Override
    public String remove() {
        String[] cacheArr = this.cache.toArray(new String[this.cache.size()]);
        return cacheArr[0];
    }
}
