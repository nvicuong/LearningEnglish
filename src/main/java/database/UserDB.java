package database;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import at.favre.lib.crypto.bcrypt.BCrypt;

import com.google.cloud.firestore.*;
import com.google.auth.oauth2.GoogleCredentials;

public class UserDB {
    private static Firestore db;
    private static String currentUser = null;

    public static void logout() {
        currentUser = null;
    }

    public static void initialize() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
        db = options.getService();
    }

    public static class Credential {
        private static boolean checkValidUsername(String s) {
            if (s.length() < 5) return false;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                }
            }

            return true;
        }

        private static String getPassword(String username) throws Exception {
            DocumentReference docRef = db.collection("login").document(username);
            DocumentSnapshot document = docRef.get().get();

            if (document.exists()) {
                return document.getString("password");
            } else {
                return null;
            }
        }

        private static void setPassword(String username, String password) throws Exception {
            DocumentReference docRef = db.collection("login").document(username);
            DocumentSnapshot document = docRef.get().get();

            password = BCrypt.withDefaults().hashToString(12, password.toCharArray());

            if (document.exists()) {
                docRef.update("password", password).get();
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("password", password);
                docRef.set(data).get();
            }
        }

        public static void signin(String username, String password) throws Exception {
            if (!checkValidUsername(username)) {
                throw new Exception("Username must be at least 5 characters long and contain only letters and digits");
            }
            if (password.length() < 6) {
                throw new Exception("Password must be at least 6 characters long");
            }
            if (getPassword(username) != null) {
                throw new Exception("Username already exists");
            }

            setPassword(username, password);
            Bookmark.init(username);
        }

        public static void login(String username, String password) throws Exception {
            String expected = getPassword(username);
            if (expected == null) {
                throw new Exception("Username " + username + " does not exist");
            }
            if (!BCrypt.verifyer().verify(password.toCharArray(), expected).verified) {
                throw new Exception("Wrong password");
            }
            currentUser = username;
        }
    }

    public static class Bookmark {
        private static void init(String username) throws Exception {
            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            if (!document.exists()) {
                docRef.set(new HashMap<>()).get();
            }
        }

        public static void add(String spell, String pronun, String def, String syn) throws Exception {
            String username = currentUser;
            if (username == null) throw new Exception("Not logged in");

            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            HashMap<String, String> newValue = new HashMap<>();
            newValue.put("pronunciation", pronun);
            newValue.put("definition", def);
            newValue.put("synonym", syn);

            // Check if a field of name spell of document exists
            if (document.contains(spell)) {
                // Update new value
                docRef.update(spell, newValue).get();
            } else {
                // Create new field with new value
                docRef.update(spell, newValue).get();
            }
        }

        public static HashMap<String, String> fetch(String word) throws Exception {
            String username = currentUser;
            if (username == null) throw new Exception("Not logged in");

            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            if (document.contains(word)) {
                return (HashMap<String, String>) document.get(word);
            } else {
                throw new Exception("Word " + word + " does not exist");
            }
        }
    }

    public static String getUsername() {
        return currentUser;
    }

    public static boolean loggedIn() {
        return currentUser != null;
    }

    public static void main(String[] args) throws Exception {
        initialize();
        // Credential.signin("admin", "admin");
        Credential.login("admin", "admin");

        Bookmark.add("hello", "həˈlō", "used as a greeting or to begin a telephone conversation", "hi");
        Bookmark.add("world", "wərld", "the earth, together with all of its countries and peoples", "earth");
        Bookmark.add("java", "ˈjävə", "a high-level programming language developed by Sun Microsystems", "programming language");
        Bookmark.add("cheese", "CHēz", "a food made from the pressed curds of milk", "milk");

        System.out.println("Fetch \"Java\" bookmark: " + Bookmark.fetch("java"));
        System.out.println("Fetch \"world\" bookmark: " + Bookmark.fetch("world"));

        try {
            System.out.println(Bookmark.fetch("sugoi"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
