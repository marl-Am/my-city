package com.example.mycity.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsData {
    private String title;
    private String url;
//    private String content;

    public NewsData(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
