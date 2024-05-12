package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.nio.charset.StandardCharsets;

public class BloomFilter {
    private final BitSet bitset;
    private final MessageDigest[] hashGenerators;
    private final int size;

    public BloomFilter(int size, String... algs) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        this.size = size;
        this.bitset = new BitSet(this.size);
        hashGenerators = new MessageDigest[algs.length];
        for (int i = 0; i < algs.length; i++) {
            try {
                hashGenerators[i] = MessageDigest.getInstance(algs[i]);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Invalid hash algorithm: " + algs[i], e);
            }
        }

    }

    public void add(String word) {
        for (MessageDigest digest : hashGenerators) {
            byte[] bytes = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            int hash = new BigInteger(1, bytes).intValue();
            bitset.set(Math.abs(hash % size));
        }
    }

    public boolean contains(String word) {
        if (word == null) {
            throw new NullPointerException("word must not be null");
        }
        for (MessageDigest digest : hashGenerators) {
            byte[] bytes = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            int hash = new BigInteger(1, bytes).intValue();
            if (!bitset.get(Math.abs(hash % size)))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitset.length(); i++) {
            sb.append(bitset.get(i) ? '1' : '0');
        }
        return sb.toString();
    }
}
