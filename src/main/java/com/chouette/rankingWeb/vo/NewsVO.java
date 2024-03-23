package com.chouette.rankingWeb.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class NewsVO {
    private Integer news;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
    private Integer regUser;
    private LocalDateTime regDttm;


    //none db
    private String filePath;
    private Integer file;
    private String regUserId;

    public NewsVO() {}
    public NewsVO( Integer news ) {
        this.news = news;
    }

}
