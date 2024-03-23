package com.chouette.rankingWeb.vo.file;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsFileVO {
    private Integer file;
    private Integer news;


    public NewsFileVO() {}
    public NewsFileVO( Integer file ) {
        this.file = file;
    }

    public NewsFileVO( Integer file, Integer news ) {
        this.file = file;
        this.news = news;
    }
}
