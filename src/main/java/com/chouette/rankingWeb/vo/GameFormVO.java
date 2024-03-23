package com.chouette.rankingWeb.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GameFormVO {
    private Integer gameForm;
    @NotBlank
    private String gameName;



    public GameFormVO() {}
    public GameFormVO( Integer gameForm ) {
        this.gameForm = gameForm;
    }
}
