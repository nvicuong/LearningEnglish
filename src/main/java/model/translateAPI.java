package model;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.FileWriter;
import java.io.IOException;

public class translateAPI {

    private static final String TEXT_PATH = "src\\\\main\\\\resources\\\\data\\\\translateText.txt";

    public static void main(String[] args) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("POST", "https://google-translate1.p.rapidapi.com/language/translate/v2")
                .setHeader("content-type", "application/x-www-form-urlencoded")
                .setHeader("Accept-Encoding", "application/gzip")
                .setHeader("X-RapidAPI-Key", "a3e6901cb7msh6d0dcabab249273p1cc558jsna9898d03c02b")
                .setHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .setBody("q=dictionary&target=vi&source=en")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    // Xử lý phản hồi ở đây
                    try {
                        FileWriter writer = new FileWriter(TEXT_PATH);
                        writer.write(response.getResponseBody());
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .join();

        client.close();
    }

    public static void translateText(String s) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("POST", "https://google-translate1.p.rapidapi.com/language/translate/v2")
                .setHeader("content-type", "application/x-www-form-urlencoded")
                .setHeader("Accept-Encoding", "application/gzip")
                .setHeader("X-RapidAPI-Key", "a3e6901cb7msh6d0dcabab249273p1cc558jsna9898d03c02b")
                .setHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .setBody("q=" + s + "&target=vi&source=en")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    try {
                        FileWriter writer = new FileWriter(TEXT_PATH);
                        writer.write(response.getResponseBody());
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .join();

        client.close();
    }
}
