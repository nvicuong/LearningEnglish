package games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class RunCrosswordGame {
    public static boolean start(int size, int longWord, int shorWord) {
        try {
            // Đường dẫn đến chương trình C++ đã biên dịch

            String compileCommand = "g++ -o program src/main/java/games/gener.cpp";
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();

            // Chạy chương trình C++ đã biên dịch
            String runCommand = "./program " + size + " " + longWord + " " + shorWord;
            Process runProcess = Runtime.getRuntime().exec(runCommand);

            /*String currentDir = System.getProperty("user.dir");
            String command = currentDir + "\\src\\test.exe";
            // Thay thế "path/to/your/cpp/program.exe" bằng đường dẫn thực tế của chương trình C++ đã biên dịch

            // Tạo một quy trình và thực hiện lệnh hệ thống
            Process process = Runtime.getRuntime().exec(command);*/

            // Đọc dữ liệu đầu ra từ quy trình
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Đợi quy trình hoàn thành
            runProcess.waitFor();
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        start(13, 7, 6);
    }
}
