package com.chouette.rankingWeb.vo.std;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StdVolumeVO {
        private Integer idx;
        @NotNull
        private LocalDate applyDttm;
        @NotNull
        private Double weight;
        private Integer regUser;
        private LocalDateTime regDttm;

        //none db
        private String regUserId;
        private String stdWeight;

}
