package com.chouette.rankingWeb.vo.std;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.ref.PhantomReference;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StdAgingVO {
    private Integer idx;
    @NotNull
    private LocalDate applyDttm;
    @NotNull
    private Integer grade;
    @NotNull
//    @Pattern(regexp = "^([0-9]+)(\\.[0-9]+)?$")
    private Double weight;
    private Integer regUser;
    private LocalDateTime regDttm;



    //none db
    private String regUserId;
}

