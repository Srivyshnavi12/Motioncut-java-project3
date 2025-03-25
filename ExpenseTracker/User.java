import java.util.HashMap;

public class User {
    private static HashMap<String, String> users = new HashMap<>();

    public static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Try a different one.");
            return false;
        }
        users.put(username, password);
        return true;
    }

    public static boolean loginUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
