package com.chouette.rankingWeb.vo;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserVO {
    private Integer user;
    @NotBlank
    private String id;
    @NotBlank
    private String pwd;
    private String pwd2;
    @NotBlank
    private String name;
    @NotBlank
    private String auth;
    @NotNull
    private Integer branch;
    private String delYn;



    //none db
    private String branchName;

    public UserVO() {}
    public UserVO( Integer id ) {
        this.user = id;
    }

}
