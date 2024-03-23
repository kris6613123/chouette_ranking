package com.chouette.rankingWeb.vo.std;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class StdTierVO {
    @NotBlank
    private String type;
    @NotBlank
    private String tier;
    @NotNull
    private Double weight;
    private Integer file;
    private Integer regUser;
    private LocalDateTime regDttm;


    //none db
    private String fileStatus;   //파일이 추가가 됐는지 되지 않았는지
    private String fileName;
    private Integer delFile;
    private String regUserId;
}
