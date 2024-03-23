package com.chouette.rankingWeb.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GameVO {
    private Integer game;
    @NotBlank
    private String name;
    @NotNull
    private Integer buyin;
    @NotNull
    private Integer totalBuyin;
    @NotNull
    private Double reward;
    @NotNull
    private Double rewardWeight;
    @NotBlank
    private String onoff;
    private Integer customer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gameDttm;
    private Integer regUser;
    private LocalDateTime regDttm;
    private BigDecimal score;
    private BigDecimal cpi;
    private String state;  //READY, FAIL, SUCCESS, OLD


    //none db
    private String branchName;
    private String regUserId;
    @NotBlank
    private String tel;

    public GameVO() {}
    public GameVO( Integer game ) {
        this.game = game;
    }

}
