package model;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;

public class translateAPI {

    private static final String TEXT_PATH = "src\\\\main\\\\resources\\\\data\\\\translateText.txt";

    public static void main(String[] args) throws IOException {
        try {
            AsyncHttpClient client = new DefaultAsyncHttpClient();
            client.prepare("POST", "https://google-translate113.p.rapidapi.com/api/v1/translator/text")
                    .setHeader("content-type", "application/x-www-form-urlencoded")
                    .setHeader("X-RapidAPI-Key", "b4a10b2eebmsh4174637d18d6aa4p1a236ejsnb849ca9b50cd")
                    .setHeader("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                    .setBody("from=auto&to=en&text=xin%20ch%C3%A0o")
                    .execute()
                    .toCompletableFuture()
                    .thenAccept(System.out::println)
                    .join();

            client.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String translateTextEntoVi(String s) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            return client.prepare("POST", "https://google-translate1.p.rapidapi.com/language/translate/v2")
                    .setHeader("content-type", "application/x-www-form-urlencoded")
                    .setHeader("Accept-Encoding", "application/gzip")
                    .setHeader("X-RapidAPI-Key", "f3ab0c33e1msh2ce3b88d5979e49p1867f1jsn146816834117")
                    .setHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                    .setBody("q=" + s + "&target=vi&source=en")
                    .execute()
                    .toCompletableFuture()
                    .thenApply(response -> {
                        // Xử lý phản hồi ở đây
                        JsonObject jsonResponse = JsonParser.parseString(response.getResponseBody()).getAsJsonObject();

                        // Extract the translated text
                        return jsonResponse.getAsJsonObject("data")
                                .getAsJsonArray("translations")
                                .get(0)
                                .getAsJsonObject()
                                .get("translatedText")
                                .getAsString();
                    })
                    .join();
        } finally {
            client.close();
        }
    }

    public static String translateTextViToEn(String s) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            return client.prepare("POST", "https://google-translate1.p.rapidapi.com/language/translate/v2")
                    .setHeader("content-type", "application/x-www-form-urlencoded")
                    .setHeader("Accept-Encoding", "application/gzip")
                    .setHeader("X-RapidAPI-Key", "f3ab0c33e1msh2ce3b88d5979e49p1867f1jsn146816834117")
                    .setHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                    .setBody("q=" + s + "&target=en&source=vi")
                    .execute()
                    .toCompletableFuture()
                    .thenApply(response -> {
                        // Xử lý phản hồi ở đây
                        JsonObject jsonResponse = JsonParser.parseString(response.getResponseBody()).getAsJsonObject();

                        // Extract the translated text
                        return jsonResponse.getAsJsonObject("data")
                                .getAsJsonArray("translations")
                                .get(0)
                                .getAsJsonObject()
                                .get("translatedText")
                                .getAsString();
                    })
                    .join();
        } finally {
            client.close();
        }
    }
}
