package lesson3;

import java.util.*;

public class PhoneDirectory {

    private final Map<String, Set<String>> phoneDirectory = new HashMap<>();

    public void add(String name, String phone) {
        Set<String> hs = new HashSet<>();
        if (this.phoneDirectory.containsKey(name)) {
            hs = this.phoneDirectory.get(name);
        }
        hs.add(phone);

        this.phoneDirectory.put(name, hs);
    }

    public Set<String> get(String name) {
        return this.phoneDirectory.getOrDefault(name, Collections.emptySet());
    }

    public Set<String> keySet() {
        return this.phoneDirectory.keySet();
    }
}
