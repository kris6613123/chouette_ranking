package com.chouette.rankingWeb.vo.file;

import lombok.Data;

@Data
public class CustomerFileVO {
    private Integer file;
    private Integer customer;


    public CustomerFileVO() {}
    public CustomerFileVO( Integer file ) {
        this.file = file;
    }
    public CustomerFileVO( Integer file, Integer customer ) {
        this.file = file;
        this.customer = customer;
    }
}
