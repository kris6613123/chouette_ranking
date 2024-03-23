package com.chouette.rankingWeb.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CustomerVO {
    private Integer customer;
    private String name;
    @NotBlank
    private String nickname;
    private String tel;
    @NotBlank
    private String itmYn;
    private Integer online;
    @NotNull
    private Integer branch;
    private Integer file;
    private Integer regUser;
    private LocalDateTime regDttm;



    //none db
    private String branchName;
    private Integer branchLogo;
    private String onoff;
    private String tier;

    public CustomerVO() {}
    public CustomerVO( Integer customer ) {
        this.customer = customer;
    }

    public CustomerVO( Integer customer, String onoff ) {
        this.customer = customer;
        this.onoff = onoff;
    }

}
