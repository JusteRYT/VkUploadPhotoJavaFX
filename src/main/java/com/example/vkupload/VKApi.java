package com.example.vkupload;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VKApi {
    private int appId;
    private String appSecret;
    private String login;
    private String password;
    private String redirectUri = "https://oauth.vk.com/blank.html";
    VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

    public VKApi(int appId, String appSecret, String login, String password) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.login = login;
        this.password = password;
    }

    public String getAuthorizationCode() throws IOException {
        // Параметры авторизации
        String scope = "friends,photos,audio,video,docs,notes,pages,status,wall,groups,messages,offline";
        String responseType = "code";

        // Получение URL для запроса авторизации
        String authorizationUri = vk.oAuth()
                .userAuthorizationCodeFlow(appId, redirectUri, responseType, scope)
                .build().toString();

        // Предполагается, что эта ссылка будет открыта пользователем в браузере, и пользователь разрешит доступ
        System.out.println("Please open the following URL in your browser and authorize the application:");
        System.out.println(authorizationUri);

        // Получение authorization code вручную
        System.out.println("Please enter the code you received after authorizing the application:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String code = reader.readLine();

        return code;
    }

    public String getAccessToken(String code) throws ClientException, ApiException {
        UserAuthResponse authResponse = vk.oAuth()
                .userAuthorizationCodeFlow(appId, appSecret, redirectUri, code)
                .execute();

        return authResponse.getAccessToken();
    }
}
