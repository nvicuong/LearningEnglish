package dictionaryapplication.dictionaryapplication;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;

public class ApiController {
    public static void main(String[] args) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("POST", "https://google-translate1.p.rapidapi.com/language/translate/v2")
                .setHeader("content-type", "application/x-www-form-urlencoded")
                .setHeader("Accept-Encoding", "application/gzip")
                .setHeader("X-RapidAPI-Key", "a3e6901cb7msh6d0dcabab249273p1cc558jsna9898d03c02b")
                .setHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .setBody("q=Hello World!&target=ja&source=en")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }
}
