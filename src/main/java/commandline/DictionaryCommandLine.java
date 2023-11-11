package commandline;

import controller.AddWordController;
import model.BookMarkManager;
import model.HistoryManager;
import model.Word;
import model.WordManager;

import java.io.IOException;
import java.util.List;
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
                System.out.println("Hãy chọn các chức năng mà bạn muốn sử dụng");
            }
            if (s == '0') {
                System.out.println("Cảm ơn bạn đã sử dụng ứng dụng của chúng tôi");
                System.out.println("Chúc bạn một ngày tốt lành");
                BookMarkManager.getBookMarkManager().save();
                return;
            }
            if (s == '1') {
                System.out.println("Hãy nhập từ mà bạn muốn thêm vào từ điển");
                System.out.println("Hãy nhập từ tiếng anh mà bạn muốn thêm vào từ điển");
                String englishWord = sc.next();
                sc.nextLine();
                System.out.println("Nhập nghĩa tiếng việt của từ đấy");
                String vietnameseWord = sc.nextLine();
                Word word_target = new Word(englishWord, "", vietnameseWord, "");
                BookMarkManager.getBookMarkManager().addWord(word_target);
                //System.out.println(BookMarkManager.getBookMarkManager().getWordList());
                System.out.println("Ban đã thêm từ " + englishWord + " vào từ điển");
                System.out.println();
                System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
            }
            if (s == '2') {
                System.out.println("Hãy nhập từ mà bạn muốn xóa khỏi từ điển");
                String englishWord = sc.next();
                Word word_target = new Word(englishWord, "", "", "");
                System.out.println("Đã chắc chắn muốn xóa chưa? [Y/N]");
                char question = sc.next().charAt(0);
                if (question == 'Y' || question == 'y') {
                    System.out.println("Bạn đã xóa từ " + englishWord + " khỏi từ điển");
                    BookMarkManager.getBookMarkManager().removeWord(word_target);
                    System.out.println();
                    System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                    System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
                } else  {
                    System.out.println("Bạn đã không xóa từ " + englishWord + " khỏi từ điển");
                    System.out.println();
                    System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                    System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
                }
            }
            if (s == '3') {
                System.out.println("Bạn muốn thay đổi từ nào");
                System.out.println("Hãy nhập từ đấy đi : )");
                String englishWord = sc.next();
                System.out.println("Hãy nhập từ mới của bạn");
                System.out.println("Hãy nhập từ tiếng anh mà bạn muốn thêm vào từ điển");
                String englishWord1 = sc.next();
                sc.nextLine();
                System.out.println("Nhập nghĩa tiếng việt của từ đấy");
                String vietnameseWord1 = sc.nextLine();
                BookMarkManager.getBookMarkManager().removeWord(new Word(englishWord, "", "", ""));
                Word word_target = new Word(englishWord1, "", vietnameseWord1, "");
                System.out.println("Bạn đã thay đổi từ " + englishWord + " thành từ " + englishWord1);
                BookMarkManager.getBookMarkManager().addWord(word_target);
                System.out.println();
                System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
            }
            if (s == '4') {
                System.out.println("Danh sách các từ trong BookMark");
                List<Word> wordList = BookMarkManager.getBookMarkManager().getWordList();
                for (Word word : wordList) {
                    System.out.println(word.getSpelling() + ": " + word.getContent());
                }
                System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
            }
            if (s == '5') {
                boolean isFound = false;
                System.out.println("Hãy nhập từ mà bạn muốn tra cứu");
                String englishWord = sc.next();
                Word word_target = new Word(englishWord, "", "", "");
                List<Word> wordList = BookMarkManager.getBookMarkManager().getWordList();
                for (Word word : wordList) {
                    if (word.getSpelling().equals(word_target.getSpelling())) {
                        System.out.println("Từ bạn muốn tra là: " + word.getSpelling() + " đã có trong từ điển");
                        System.out.println();
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    System.out.println("Từ bạn muốn tra là: " + word_target.getSpelling() + " chưa có trong từ điển");
                    System.out.println("Bạn có muốn thêm từ này vào từ điển không? [Y/N]");
                    char question = sc.next().charAt(0);
                    if (question == 'Y' || question == 'y') {
                        System.out.println("Bạn nhập số 1 đi : )) để thêm nào hihi");
                    } else  {
                        System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                        System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
                    }
                } else  {
                    System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                    System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
                }
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