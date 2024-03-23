package com.chouette.rankingWeb.vo.file;

import lombok.Data;

@Data
public class BranchFileVO {
    private Integer file;
    private Integer branch;
    private String type;


    public BranchFileVO() {}
    public BranchFileVO( Integer file ) {
        this.file = file;
    }

    public BranchFileVO( Integer file, Integer branch, String type ) {
        this.file = file;
        this.branch = branch;
        this.type = type;
    }

}
