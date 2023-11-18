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
            if (s.length() <= 6) return false;

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
                throw new Exception("Username must be at least 6 characters long and contain only letters and digits");
            }
            if (password.length() < 6) {
                throw new Exception("Password must be at least 6 characters long");
            }
            if (getPassword(username) != null) {
                throw new Exception("Username already exists");
            }

            setPassword(username, password);
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
        private static void init() throws Exception {
            String username = currentUser;
            if (username == null) throw new Exception("Not logged in");

            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            if (!document.exists()) {
                Map<String, Object> data = new HashMap<>();
                data.put("value", new ArrayList<String>());
                docRef.set(data).get();
            }
        }

        public static void add(String word) throws Exception {
            String username = currentUser;
            if (username == null) throw new Exception("Not logged in");

            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            if (document.exists()) {
                ArrayList<String> res = (ArrayList<String>) document.get("value");
                if (!res.contains(word)) res.add(word);
                docRef.update("value", res).get();
            } else {
                System.err.println("No such document!");
            }
        }

        public static ArrayList<String> fetch() throws Exception {
            String username = currentUser;
            if (username == null) throw new Exception("Not logged in");

            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            return (ArrayList<String>) document.get("value");
        }
    }

    public static String getUsername() {
        return currentUser;
    }

    public static void main(String[] args) throws Exception {
        initialize();


    }
}
