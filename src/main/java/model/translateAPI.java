package model;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class translateAPI {
    static HttpRequest.Builder builder;

    public static void initialize() {
        builder = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "d2c5e20068mshe31dffe11518238p16a140jsnf527385aeb14")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com");
    }

    public static void main(String[] args) throws Exception {
        initialize();

        HttpRequest request = builder.method("POST", HttpRequest.BodyPublishers.ofString("q=Hello%2C%20world!&target=vi&source=en")).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public static String translateTextEntoVi(String s) throws IOException, InterruptedException {
        String body = String.format("q=%s&target=vi&source=en", s);
        HttpRequest request = builder.method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        return jsonResponse.getAsJsonObject("data")
                .getAsJsonArray("translations")
                .get(0)
                .getAsJsonObject()
                .get("translatedText")
                .getAsString();
    }

    public static String translateTextViToEn(String s) throws IOException, InterruptedException {
        String body = String.format("q=%s&target=en&source=vi", s);
        HttpRequest request = builder.method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        return jsonResponse.getAsJsonObject("data")
                .getAsJsonArray("translations")
                .get(0)
                .getAsJsonObject()
                .get("translatedText")
                .getAsString();
    }
}
