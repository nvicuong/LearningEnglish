package dictionaryapplication.dictionaryapplication;

import java.io.InputStream;
import java.util.*;

import model.Word;


public class SourceReader {
    public static String[] newWord;
    public static List<Word> wordDAOS;

    public static void readFile() {
        InputStream inputStream = SourceReader.class.getResourceAsStream("/data/wordContent.txt");
        newWord = new String[120000];
        if (inputStream == null) {
            System.err.println("File not found.");
            return;
        }

        Scanner scanner = new Scanner(inputStream);
        int count = 0;
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.isEmpty()) {
                continue;
            }
            newWord[count] = s.replaceAll("\\(", "").replaceAll("\\)", "").replaceFirst("'", "");
            count++;
        }
        wordDAOS = new ArrayList<>(count);
    }

    public static void start() {
        readFile();
        int index = 0;
        while (newWord[index] != null) {
            String[] arr = newWord[index].split("', '");
            Word wordDAO = new Word(arr[0], arr[1], arr[2], arr[3].substring(0, arr[3].length() - 1));
            wordDAOS.add(wordDAO);
            index++;
        }
        Collections.sort(wordDAOS);
        newWord = null;

    }

    public static int binarySearchWordList(String s) {
        int l = 0, r = wordDAOS.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            // kiểm tra xem x có ở chính giữa không
            if (wordDAOS.get(m).getSpelling().indexOf(s) == 0)
            {
                return m;
            }

            // Nếu x lớn hơn, bỏ qua nửa bên trái
            if (wordDAOS.get(m).getSpelling().compareTo(s) < 0) {
                l = m + 1;
            }
            // Nếu x nhỏ hơn, bỏ qua nửa bên phải
            else {
                r = m - 1;
            }
        }

        // phần tử không tồn tại
        return -1;
    }

    public static List<String> searchWordList(String s) {
        List<String> arr = new ArrayList<>();
        int index = binarySearchWordList(s);
        if (index >= 0) {
            int up = index, down = index;
            while (up >= 0 && index - up < 10 && wordDAOS.get(up).getSpelling().contains(s)) {
                arr.add(wordDAOS.get(up).getSpelling());
                up--;
            }
            while (down < wordDAOS.size() && down - index < 10 && wordDAOS.get(down).getSpelling().contains(s)) {
                arr.add(wordDAOS.get(down).getSpelling());
                down++;
            }
        }
        return arr;
    }
}

