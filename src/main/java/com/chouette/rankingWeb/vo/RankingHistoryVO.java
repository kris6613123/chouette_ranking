package com.chouette.rankingWeb.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RankingHistoryVO {
    private LocalDate gameDttm;
    private Integer customer;
    private Integer totalRank;
    private BigDecimal totalCpi;
    private Integer onlineRank;
    private BigDecimal onlineCpi;
    private Integer offlineRank;
    private BigDecimal  offlineCpi;

    //none db
    private String branchName;
    private String branchNameEng;
    private String colorTable;
    private String name;
    private String nickname;
    private String tel;
    private Double itm;
    private String itmYn;
    private Integer online;
    private Integer file;
    private String tier;
    private String tierFile;
}
