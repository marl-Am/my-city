package com.example.mycity.services;

import com.example.mycity.models.NewsData;
import com.example.mycity.utility.PropertiesUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenNewsService {
    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private ObjectMapper mapper;

    /**
     * This method returns a List<NewsData> object, otherwise it throws an exception.
     *
     * @param  city the text input written in the search bar of the React frontend
     * @return      arraylist of news data objects
     */

    @CrossOrigin(origins = "*")
    public List<NewsData> getNewsDataList(String city) throws Exception {

        Map<String, String> properties = PropertiesUtils.getProperties();
        String NEWS_API_KEY = properties.get("NEWS_API_KEY.key");

        // String city = "New York City"
        city = city.replace(" ", "%20");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://newsdata.io/api/1/news?&apikey=" +
                NEWS_API_KEY +
                "&country=us" +
                "&category=top" +
                "&language=en" +
                "&q=" + city);
        CloseableHttpResponse response = httpClient.execute(request);

        try {
            List<NewsData> newsDataList = new ArrayList<>();

            // Get the response body as a String
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            int totalResults = root.path("totalResults").asInt();

            JsonNode articles = root.path("results");
            for (int i = 0; i < totalResults; i++) {
                String title = articles.get(i).get("title").asText();
                String url = articles.get(i).get("link").asText();
//                String content = articles.get(i).get("description").asText();
                NewsData newsData = new NewsData(title, url);
                newsDataList.add(newsData);

//                System.out.println("*** [" + i + "] Title: " + title + "\nUrl: " + url + " ***");
                if (i == 9){
                    break;
                }
            }
            return newsDataList;
        } finally {
            response.close();
        }
    }
}
