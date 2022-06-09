package com.clockworks.clock.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpClient {

    public void sendTime(String url, String json) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            client.execute(httpPost);
            log.info("Request Entity: " + entity);
            client.close();
        } catch (Exception e) {
            log.info("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
