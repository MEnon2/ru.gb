package lesson3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PhoneDirectory {

    private final HashMap<String, HashSet<String>> phoneDirectory = new HashMap<>();

    public void add(String name, String phone) {
        HashSet<String> hs = new HashSet<>();
        if (this.phoneDirectory.containsKey(name)) {
            hs = this.phoneDirectory.get(name);
        }
        hs.add(phone);

        this.phoneDirectory.put(name, hs);
    }

    public HashSet<String> get(String name) {
        HashSet<String> hs = new HashSet<>();
        if (this.phoneDirectory.containsKey(name)) {
            hs = this.phoneDirectory.get(name);
        }
        return hs;
    }

    public Set<String> keySet() {
        return this.phoneDirectory.keySet();
    }
}
