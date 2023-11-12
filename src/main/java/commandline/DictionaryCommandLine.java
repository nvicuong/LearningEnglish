package commandline;

import controller.AddWordController;
import model.BookMarkManager;
import model.HistoryManager;
import model.Word;
import model.WordManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class DictionaryCommandLine {

    private static final WordGame word = new WordGame();
    private static String myWord;
    private static List<String> myLetters;
    private static List<String> answer;

    private static int correctWord = 2;

    private static int isFound;

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
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
                String englishWord = sc.next();

            }
            if (s == '7') {
                System.out.println("Game Doan Tu : )");
                myWord = word.getRandomWord();
                myLetters = Arrays.asList(myWord.split(""));
//                System.out.println(myLetters);
                answer = Arrays.asList(new String[myLetters.size()*2]);
                for(int i = 0; i < myLetters.size() * 2; i++){
                    if(i % 2 == 0){
                        answer.set(i, "_");
                    }else{
                        answer.set(i, " ");
                    }
                }
                myLetters.set(0,".");
                myLetters.set(myWord.length() - 1, ".");
                System.out.println("Từ này có " + myWord.length() + " chữ cái");
                System.out.println("Từ này bắt đầu bằng chữ " + myWord.charAt(0));
                System.out.println("Và kết thúc bằng chữ " + myWord.charAt(myWord.length() - 1));
                answer.set(0, myWord.charAt(0) + "");
                answer.set(myWord.length() * 2 - 2, myWord.charAt(myWord.length() - 1) + "");
                for (String c : answer) {
                    System.out.print(c);
                }
                System.out.println();
                while (true) {
                    String res;
                    res = sc.next();
                    int count = 0;
                    if (myLetters.contains(res)) {
                        int letterIndex;
                        while ((letterIndex = myLetters.indexOf(res)) != -1) {
                            correctWord++;
                            myLetters.set(letterIndex, ".");
                            answer.set(letterIndex * 2, res);
                            count ++;
                        }
                        System.out.println("Có " + count + " chữ " + res + " trong từ");
                        System.out.println("Như vậy từ của bạn hiện tại là");
                        for (int i = 0; i < answer.size(); i++) {
                            System.out.print(answer.get(i));
                        }
                        System.out.println();
                        if (correctWord == myWord.length()) {
                            System.out.println("Bạn đã thắng");
                            break;
                        }
                    } else {
                        isFound ++;
                        System.out.println("Không có chữ " + res + " nào trong từ");
                        System.out.println("Như vậy từ của bạn hiện tại là");
                        for (int i = 0; i < answer.size(); i++) {
                            System.out.print(answer.get(i));
                        }
                        System.out.println();
                    }
                    if (isFound == 5) {
                        System.out.println("Bạn có muốn được gợi ý không? [Y/N]");
                        char k = sc.next().charAt(0);
                        if (k == 'Y' || k == 'y') {
                            for (int i = 1; i < myWord.length()/2; i++) {
                                answer.set(i + 1, myWord.charAt(i) + "");
                            }
                        }
                        for (String c : answer) {
                            System.out.print(c);
                        }
                        System.out.println();
                    }
                }
                System.out.println();
                System.out.println("Bạn có muốn sử dụng thêm chức nào của tôi nữa không?");
                System.out.println("Nếu có thì nhập các chức năng mà bạn muốn sử dụng\n");
            }
            if (s == '8') {
                System.out.println("Nhập tên tệp bạn muốn nhập từ vựng từ:");
                String fileName = sc.next();
                //List<Word> words = read(fileName);
//                for (Word word : words) {
//                    WordManager.getWordManager().addWord(word);
//                }
                System.out.println("Đã nhập từ vựng từ tệp " + fileName);
            } else if (s == '9') {
                System.out.println("Nhập tên tệp bạn muốn xuất từ vựng ra:");
                String fileName = sc.next();
//                List<Word> words = WordManager.getWordManager().getWordList();
//                writeWordsToFile(words, fileName);
                System.out.println("Đã xuất từ vựng ra tệp " + fileName);
            }
        }
    }
}