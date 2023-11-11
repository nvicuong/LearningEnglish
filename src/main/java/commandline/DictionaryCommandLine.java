package commandline;

import controller.AddWordController;
import model.BookMarkManager;
import model.HistoryManager;
import model.Word;
import model.WordManager;

import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandLine {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        WordManager.getWordManager();
        BookMarkManager.getBookMarkManager();
        HistoryManager.getHistoryManager();
        System.out.println("Welcome to My Application!");
        System.out.println("\t[0] Exit");
        System.out.println("\t[1] Add");
        System.out.println("\t[2] Remove");
        System.out.println("\t[3] Update");
        System.out.println("\t[4] Display");
        System.out.println("\t[5] Lookup");
        System.out.println("\t[6] Search");
        System.out.println("\t[7] Game");
        System.out.println("\t[8] Import from file");
        System.out.println("\t[9] Export to file");
        System.out.println("Hãy chọn các chức năng mà bạn muốn sử dụng [0-9]");
        Scanner sc = new Scanner(System.in);
        while (true) {
            char s;
            s = sc.next().charAt(0);
            if (s < '0' || s > '9') {
                System.out.println("Action not supported.");
                System.out.println("Hãy chọn các chức năng mà bạn muốn sử dụng [0-9]");
            }
            if (s == '0') {
                BookMarkManager.getBookMarkManager().save();
                return;
            }
            if (s == '1') {
                System.out.println("Vui lòng nhập từ mà bạn muốn thêm vào từ điển");
                String englishWord = sc.next();
                String vietnameseWord = sc.next();
                Word word_target = new Word(englishWord, "", vietnameseWord, "");
                BookMarkManager.getBookMarkManager().addWord(word_target);
                System.out.println(BookMarkManager.getBookMarkManager().getWordList());
            }
            if (s == '2') {
                System.out.println("Vui lòng nhập từ mà bạn muốn xóa khỏi từ điển");
                String englishWord = sc.next();
                Word word_target = new Word(englishWord, "", "", "");
                BookMarkManager.getBookMarkManager().removeWord(word_target);
                System.out.println(BookMarkManager.getBookMarkManager().getWordList());
            }
            if (s == '3') {
                System.out.println("Vui lòng nhập từ mà bạn muốn thay đổi nó");
            }
            if (s == '4') {
                System.out.println("Danh sách các từ trong BookMark");

            }
            if (s == '5') {
                System.out.println("Vui lòng nhập từ mà bạn muốn tra cứu");
            }
            if (s == '6') {
                System.out.println("Vui lòng nhập từ mà bạn muốn tìm kiếm");
            }
            if (s == '7') {
                System.out.println("Game đoán từ");
            }
            if (s == '8') {
                System.out.println("Vui lòng nhập đường dẫn file bạn muốn import");
            }
            if (s == '9') {
                System.out.println("Vui lòng nhập đường dẫn file bạn muốn export");
            }
        }
    }
}