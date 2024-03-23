package com.chouette.rankingWeb.controller;

import com.chouette.rankingWeb.service.std.*;
import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.service.FileService;
import com.chouette.rankingWeb.vo.std.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( value = "/std",  method = RequestMethod.GET)
@Validated
@PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
public class CtrlStd extends CtrlBase {

    private final StdVolumeService stdVolumeService;
    private final StdWinningBuyinService stdWinningBuyinService;
    private final StdWinningRewardService stdWinningRewardService;
    private final StdAgingService stdAgingService;
    private final StdTierService stdTierService;

    @RequestMapping( value = { "/mod", "/{std}/mod" } )
    public String mod (Model model, @PathVariable( name = "std", required = false ) String std) throws IOException {
        if (std == null) {
            std = "volume";
        }
        model.addAttribute("std", std);
        switch (std) {
            case "volume":
                model.addAttribute( "volume", stdVolumeService.getRecentItem() );
                return "std/volume";
            case "buyin":
                model.addAttribute("stdList", stdWinningBuyinService.getRecentList());
                return "std/winningAging";
            case "reward":
                model.addAttribute("stdList", stdWinningRewardService.getRecentList());
                return "std/winningAging";
            case "aging":
                model.addAttribute("stdList", stdAgingService.getRecentList());
                return "std/winningAging";
            case "tier":
                model.addAttribute("typeS", stdTierService.getTypeSItem());
                model.addAttribute("typeRList", stdTierService.getTypeRList());
                return "std/tier";
            default:
                throw new IOException("bad");
        }
    }

    @ResponseBody
    @RequestMapping( value = "/volume/mod/p", method = RequestMethod.POST)
    public ResponseEntity<String> modVolume ( @Valid @RequestBody StdVolumeVO vo ) {
        stdVolumeService.save( vo );
        return new ResponseEntity<>( "성공적으로 등록하였습니다.", HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = "/buyin/mod/p", method = RequestMethod.POST)
    public ResponseEntity<String> modBuyin ( @Valid @RequestBody List<StdWinningBuyinVO> vo ) {
        if( stdWinningBuyinService.checkDuplicate( vo ) ) {
            new ResponseEntity<>("중복되는 값이 있습니다.", HttpStatus.BAD_REQUEST);
        }
        stdWinningBuyinService.save( vo );
        return new ResponseEntity<>( "성공적으로 등록하였습니다.", HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = "/reward/mod/p", method = RequestMethod.POST)
    public ResponseEntity<String> modReward ( @Valid @RequestBody List<StdWinningRewardVO> vo ) {
        if( stdWinningRewardService.checkDuplicate( vo ) ) {
            new ResponseEntity<>("중복되는 값이 있습니다.", HttpStatus.BAD_REQUEST);
        }
        stdWinningRewardService.save( vo );
        return new ResponseEntity<>( "성공적으로 등록하였습니다.", HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = "/aging/mod/p", method = RequestMethod.POST )
    public ResponseEntity<String> modAging ( @Valid @RequestBody List<StdAgingVO> vo ) {
        if( stdAgingService.checkDuplicate( vo ) ) {
            new ResponseEntity<>("중복되는 값이 있습니다.", HttpStatus.BAD_REQUEST);
        }
        stdAgingService.save( vo );
        return new ResponseEntity<>( "성공적으로 등록하였습니다.", HttpStatus.OK );
    }


    @RequestMapping( value = "/tier/mod/p", method = RequestMethod.POST )
    public ResponseEntity<String> modTier ( @Valid @RequestPart("vo") List<StdTierVO> vo, @RequestPart(value = "formFile", required = false) List<MultipartFile> fileList ) throws IOException {
        if( stdTierService.checkDuplicate( vo ) ) {
            new ResponseEntity<>("중복되는 값이 있습니다.", HttpStatus.BAD_REQUEST);
        }
        List<Integer> notDelList = vo.stream()
                .map( StdTierVO::getFile )
                .filter( Objects::nonNull )
                .collect( Collectors.toList() );
        stdTierService.save( vo, notDelList, fileList );
        return new ResponseEntity<>( "성공적으로 등록하였습니다.", HttpStatus.OK );
    }
}
