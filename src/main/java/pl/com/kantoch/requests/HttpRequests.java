package pl.com.kantoch.requests;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpRequests {

    public static HttpResponse<String> sendPostRequest(String uri, Map<String,String> bodyParams) throws IOException, InterruptedException {
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(bodyParams);

        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type","application/json")
                .header("User-Agent", "Java client")
                .build();
        return client.send(request,
                HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> sendGetRequest(String uri, Map<String,String> params) throws IOException, InterruptedException {
        if(params!=null && params.size()>0){
            uri+="?";
            int size = params.size();
            int pointer = 0;
            StringBuilder uriBuilder = new StringBuilder(uri);
            for(String key : params.keySet()){
                if(pointer<size){
                    uriBuilder.append(key).append("=").append(params.get(key));
                    pointer++;
                }
            }
            uri = uriBuilder.toString();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .header("Content-Type","application/json")
                .header("User-Agent", "Java client")
                .build();
        return client.send(request,
                HttpResponse.BodyHandlers.ofString());
    }
}
