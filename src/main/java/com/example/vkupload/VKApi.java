package com.example.vkupload;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class VKApi {
    private int appId;
    private String appSecret;
    private String login;
    private String password;

    public VKApi(int appId, String appSecret, String login, String password) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.login = login;
        this.password = password;
    }

    public String getAccessToken() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
        // Получаем адрес страницы авторизации
        String authUrl = "https://oauth.vk.com/authorize" +
                "?client_id=" + appId +
                "&redirect_uri=https://oauth.vk.com/blank.html" +
                "&display=mobile" +
                "&scope=friends,photos,audio,video,docs,notes,pages,status,wall,groups,messages,offline" +
                "&response_type=token";

        // Получаем страницу авторизации и извлекаем параметр hash
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(authUrl);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String content = org.apache.http.util.EntityUtils.toString(httpResponse.getEntity());
        String hash = extractHash(content);

        // Формируем данные для отправки POST запроса на получение access_token
        String data = "client_id=" + appId +
                "&client_secret=" + appSecret +
                "&grant_type=password" +
                "&username=" + URLEncoder.encode(login, "UTF-8") +
                "&password=" + URLEncoder.encode(password, "UTF-8") +
                "&scope=friends,photos,audio,video,docs,notes,pages,status,wall,groups,messages,offline" +
                "&response_type=token" +
                "&hash=" + hash;

        // Отправляем POST запрос и получаем access_token
        httpPost = new HttpPost("https://oauth.vk.com/token");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        String[] params = data.split("&");
        for (String param : params) {
            String[] parts = param.split("=");
            nameValuePairs.add(new BasicNameValuePair(parts[0], parts[1]));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        httpResponse = httpClient.execute(httpPost);
        content = org.apache.http.util.EntityUtils.toString(httpResponse.getEntity());
        String accessToken = extractAccessToken(content);

        return accessToken;
    }

    private String extractHash(String content) throws UnsupportedEncodingException {
        int startIndex = content.indexOf("hash=") + 5;
        int endIndex = content.indexOf("\"", startIndex);
        String hash = content.substring(startIndex, endIndex);
        hash = URLEncoder.encode(hash, "UTF-8");
        return hash;
    }

    private String extractAccessToken(String content) throws NoSuchAlgorithmException {
        int startIndex = content.indexOf("access_token=") + 17;
        int endIndex = content.indexOf("&", startIndex);
        String accessToken = content.substring(startIndex, endIndex);
        // Проверяем корректность полученного access_token с помощью подсчета MD5 хэша
        MessageDigest md = MessageDigest.getInstance("MD5");
        String checkString = appId + "_" + accessToken + "_" + appSecret;
        byte[] digest = md.digest(checkString.getBytes());
        String expectedHash = DatatypeConverter.printHexBinary(digest).toLowerCase();
        startIndex = content.indexOf("email=") + 6;
        endIndex = content.indexOf("&", startIndex);
        String email = content.substring(startIndex, endIndex);
        if (!expectedHash.equals(email)) {
            throw new NoSuchAlgorithmException("Invalid access_token");
        }

        return accessToken;
    }
}
