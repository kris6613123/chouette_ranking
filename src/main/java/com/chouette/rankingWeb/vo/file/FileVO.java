package com.chouette.rankingWeb.vo.file;

import lombok.Data;

@Data
public class FileVO {
    private Integer file;
    private String name;
    private String serverName;
    private String path;
    private Integer news;
    private Integer customer;
    private Integer branch;
    private Integer branchLogo;


    public FileVO() {}
    public FileVO( Integer file ) {
        this.file = file;
    }
    public FileVO( String name, String serverName, String path ) {
        this.name = name;
        this.serverName = serverName;
        this.path = path;

    }

}
