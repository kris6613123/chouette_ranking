package com.chouette.rankingWeb.vo;

import lombok.Data;

@Data
public class BranchVO {
    private Integer branch;
    private String name;
    private String nameEng;
    private String location;
    private String kakao;
    private String instagram;
    private String tel;
    private String color;
    private String colorTable;
    private String onoff;
    private String delYn;


    //none db
    private Integer file;
    private Integer fileLogo;

    public BranchVO() {}

    public BranchVO( Integer branch ) {
        this.branch = branch;
    }

}
