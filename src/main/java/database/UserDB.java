package database;

import java.io.FileInputStream;
import java.util.*;

import at.favre.lib.crypto.bcrypt.BCrypt;
import model.Word;

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
//            Bookmark.init(username);
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
            if (document.exists() && document.contains(spell)) {
                // Update new value
//                docRef.update(spell, newValue).get();
                Map<String, Object> updateData = new HashMap<>();
                updateData.put(spell, newValue);
                docRef.update(updateData).get();
            } else {
                // Create new field with new value
//                docRef.update(spell, newValue).get();
                Map<String, Object> newData = new HashMap<>();
                newData.put(spell, newValue);
                docRef.set(newData, SetOptions.merge()).get();
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

        public static List<Word> fetchAllWords() throws Exception {
            String username = currentUser;
            if (username == null) throw new Exception("Not logged in");

            DocumentReference docRef = db.collection("bookmark").document(username);
            DocumentSnapshot document = docRef.get().get();

            List<Word> wordsList = new ArrayList<>();

            if (document.exists()) {
                // Lặp qua tất cả các giá trị trong tài liệu và thêm chúng vào danh sách
                Map<String, Object> allData = document.getData();

                // In ra tất cả các trường và giá trị
                for (Map.Entry<String, Object> entry : allData.entrySet()) {
                    String field = entry.getKey();
                    Object value = entry.getValue();
                    value = (HashMap<String, String>) value;
                    wordsList.add(new Word(field, (String) ((HashMap<?, ?>) value).get("pronunciation"), (String) ((HashMap<?, ?>) value).get("definition"), (String) ((HashMap<?, ?>) value).get("synonym")));
                }

            }
            return wordsList;
        }
    }

    public static String getUsername() {
        return currentUser;
    }

    public static boolean loggedIn() {
        return currentUser != null;
    }

    public static void main(String[] args) throws Exception {
        // Credential.signin("admin", "admin");
        try {
            initialize();
            Credential.login("admin", "admin");
            addWord(new Word("world", "wɜːld", "thế giới", "earth"));
        } catch (Exception e) {
            System.err.println("Khong the ket noi");
        }

//        addWord(new Word("hello", "həˈləʊ", "xin chào", "hi"));
    }

    public static void addWord(Word word) throws Exception {
        Bookmark.add(word.getSpelling(), word.getPronunciation(), word.getContent(), word.getSynonym());
    }

    public static Word getWord(String word) throws Exception {
        HashMap<String, String> hashMap = Bookmark.fetch(word);
        return new Word(word, hashMap.get("pronunciation"), hashMap.get("definition"), hashMap.get("synonym"));
    }

    public static void clearAll() {
        db.collection("bookmark").document(currentUser).delete();
    }

    public static void addWordAll(List<Word> wordList) {
        for (Word word : wordList) {
            try {
                addWord(word);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(List<Word> wordList) {
        addWordAll(wordList);
    }

    public static List<Word> getAllWords() throws Exception {
        return Bookmark.fetchAllWords();
    }
}
