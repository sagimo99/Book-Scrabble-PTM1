package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryManager {
    private Map<String, Dictionary> department;

    private static DictionaryManager instance;

    private DictionaryManager() {
        department = new HashMap<>();
    }

    public static synchronized DictionaryManager get() {
        if (instance == null) {
            instance = new DictionaryManager();
        }
        return instance;
    }

    public boolean query(String... args) {// Checking if the word (last element in args) exists in one of the
                                          // dictionaries that represents the given files, using quey method
        String word = args[args.length - 1];
        boolean wordExists = false;

        for (int i = 0; i < args.length - 1; i++) {
            if (!department.containsKey(args[i])) {
                department.put(args[i], new Dictionary(args[i]));
            }

            if (department.get(args[i]).query(word)) {
                wordExists = true;
                // Not returning true because the caches in each dictionary need to be updated
                // (for later searches)
            }
        }

        return wordExists;
    }

    public boolean challenge(String... args) {// Checking if the word (last element in args) exists in one of the
                                              // dictionaries that represents the given files, using challenge method
        String word = args[args.length - 1];
        boolean wordExists = false;

        for (int i = 0; i < args.length - 1; i++) {
            if (!department.containsKey(args[i])) {
                department.put(args[i], new Dictionary(args[i]));
            }

            if (department.get(args[i]).challenge(word)) {
                wordExists = true;
                // Not returning true because the caches in each dictionary need to be updated
                // (for later searches)
            }
        }

        return wordExists;
    }

    private Dictionary getDictionaryForBook(String book) {
        return department.computeIfAbsent(book, k -> new Dictionary(book + ".txt"));
    }

    public int getSize() {
        return department.size();
    }
}
