package com.chouette.rankingWeb.controller;

import com.chouette.rankingWeb.dao.RankingHistoryDAO;
import com.chouette.rankingWeb.service.*;
import com.chouette.rankingWeb.service.std.StdAgingService;
import com.chouette.rankingWeb.service.std.StdVolumeService;
import com.chouette.rankingWeb.service.std.StdWinningBuyinService;
import com.chouette.rankingWeb.service.std.StdWinningRewardService;
import com.chouette.rankingWeb.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( value = "/game",  method = RequestMethod.GET)
@Validated
@PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
public class CtrlGame extends CtrlBase {

    private final GameService gameService;
    private final UserService userService;
    private final GameFormService gameFormService;
    private final CustomerService customerService;

    @RequestMapping( value = { "/list" } )
    public String list ( Model model,
                         @RequestParam(defaultValue = "1", required = false) Integer pagenum,
                         @RequestParam(defaultValue = "10", required = false) Integer contentnum,
                         @RequestParam(value="game", required = false ) String keyowrd,
                         @RequestParam(value="state", required = false ) String state,
                         @RequestParam(value="start", required = false ) String start,
                         @RequestParam(value="end", required = false ) String end ) {
        gameService.getPagingList( model, pagenum, contentnum, keyowrd, state, start, end );
        return "game/list";
    }

    @RequestMapping( value = { "/mod", "/{id}/mod" } )
    public String mod ( Model model, @PathVariable( value = "id", required = false ) Integer id, @RequestParam( value = "excel", required = false, defaultValue = "N" ) String excel ) {
        if( excel.equals( "Y" ) ) {
            return "game/excel";
        }
        GameVO game = new GameVO();
        if( id != null ) {
            game = gameService.getItem( new GameVO( id ) );
            game.setBuyin( game.getBuyin() / 10000 );
            game.setTotalBuyin( game.getTotalBuyin() / 10000 );
            game.setReward( game.getReward() / 10000 );
            game.setTel( customerService.getItem( new CustomerVO( game.getCustomer() ) ).getTel() );
        }
        model.addAttribute("game", game );
        model.addAttribute("gameFormList", gameFormService.getList() );
        return "game/mod";
    }

    @ResponseBody
    @RequestMapping( value = { "/mod/p" }, method = RequestMethod.POST )
    public ResponseEntity<String> mod ( @Valid @RequestBody GameVO vo ) {
        vo.setBuyin( vo.getBuyin() * 10000 );
        vo.setTotalBuyin( vo.getTotalBuyin() * 10000 );
        vo.setReward( vo.getReward() * 10000 );
        vo.setRegUser( userService.getUser() );
        vo.setState( "READY" );

        LocalDate now = LocalDate.now();
        if( now.isBefore( vo.getGameDttm() ) ) {
            return new ResponseEntity<>( "게임날짜가 올바르지 않습니다.", HttpStatus.BAD_REQUEST );
        }

        if( !vo.getOnoff().equals("N") && !vo.getOnoff().equals("F") ) {
            return new ResponseEntity<>( "온라인이면 N, 오프라인이면 F를 입력해주세요.", HttpStatus.BAD_REQUEST );
        }

        CustomerVO c = customerService.getItemByTel( vo.getTel() );

        if( c == null ) {
            return new ResponseEntity<>( "존재하지 않은 유저가 있습니다.", HttpStatus.BAD_REQUEST );
        }
        vo.setCustomer( c.getCustomer() );

        if( vo.getGame() != null ) {
            gameService.mod( vo );
            return new ResponseEntity<>( "수정되었습니다. 저장된 정보는 오전 9시에 반영이 시작됩니다.", HttpStatus.OK );
        }
        else {
            gameService.add( vo );
            return new ResponseEntity<>( "등록되었습니다. 저장된 정보는 오전 9시에 반영이 시작됩니다.", HttpStatus.OK );
        }
    }

    @ResponseBody
    @RequestMapping( value = { "/mod/excel/p" }, method = RequestMethod.POST )
    public ResponseEntity<String> mod( @RequestPart( value = "formFile") MultipartFile file ) {
        List<GameVO> gameList = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook( file.getInputStream() );
            int sheets = workbook.getNumberOfSheets();

            for( int i = 0; i < sheets; i++ ) {

                Sheet sheet = workbook.getSheetAt( i );

                //공통정보
                Cell gameDttm = sheet.getRow(0).getCell(1);
                Cell branch = sheet.getRow(1).getCell(1);
                Cell name = sheet.getRow(2).getCell(1);
                Cell rewardWeight = sheet.getRow(3).getCell(1);
                Cell buyin = sheet.getRow(4).getCell(1);
                if( !( gameDttm != null && branch != null && name != null && rewardWeight != null && buyin != null  ) ) {
                    return new ResponseEntity<>( "excel " + ( i + 1 ) + "번째 시트 입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST );
                }
                if( !(gameDttm.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(gameDttm) && branch.getCellType() == CellType.STRING
                        && name.getCellType() == CellType.STRING && rewardWeight.getCellType() == CellType.NUMERIC
                        && buyin.getCellType() == CellType.NUMERIC ) ) {
                    return new ResponseEntity<>( "excel " + ( i + 1 ) + "번째 시트 입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST );
                }

                if( !branch.getStringCellValue().equals("N") && !branch.getStringCellValue().equals("F") ) {
                    return new ResponseEntity<>( "온라인이면 N, 오프라인이면 F를 입력해주세요.", HttpStatus.BAD_REQUEST );
                }

                int rows = sheet.getPhysicalNumberOfRows();

                for( int j = 6; j < rows; j++ ) {
                    if( sheet.getRow(j) == null ) {
                        break;
                    }
                    GameVO game = new GameVO();
                    game.setRegUser( userService.getUser() );
                    game.setGameDttm( LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format( gameDttm.getDateCellValue() ) ) );
                    game.setOnoff( branch.getStringCellValue() );
                    game.setName( name.getStringCellValue() );
                    game.setRewardWeight( rewardWeight.getNumericCellValue() );
                    game.setBuyin( (int) buyin.getNumericCellValue() * 10000 );

                    Row row = sheet.getRow(j);
                    //공통정보
                    Cell tel = row.getCell(0);
                    Cell totalBuyin = row.getCell(1);
                    Cell reward = row.getCell(2);
                    if( tel == null || tel.getCellType() == CellType.BLANK) {
                        break;
                    }
                    if( !( totalBuyin != null && reward != null ) ) {
                        return new ResponseEntity<>( "excel " + ( i + 1 ) + "번째 시트 " + ( j + 1 ) + "행 입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST );
                    }
                    if( !(tel.getCellType() == CellType.STRING && totalBuyin.getCellType() == CellType.NUMERIC
                            && reward.getCellType() == CellType.NUMERIC ) ) {
                        return new ResponseEntity<>( "excel " + ( i + 1 ) + "번째 시트 " + ( j + 1 ) + "행 입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST );
                    }

                    CustomerVO c = customerService.getItemByTel( tel.getStringCellValue().replaceAll( "-", "" ) );
                    if( c == null ) {
                        return new ResponseEntity<>( "존재하지 않은 유저가 있습니다.", HttpStatus.BAD_REQUEST );
                    }
                    game.setCustomer( c.getCustomer() );
                    game.setTotalBuyin( (int) totalBuyin.getNumericCellValue() * 10000 );
                    game.setReward( reward.getNumericCellValue() * 10000 );
                    game.setState("READY");
                    gameList.add( game );
                }
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameService.addList(gameList);

        return new ResponseEntity<>( "등록되었습니다. 저장된 정보는 오전 9시에 반영이 시작됩니다.", HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = { "/form/mod/p" }, method = RequestMethod.POST )
    public ResponseEntity<String> modForm ( @Valid @RequestBody GameFormVO vo ) {
        gameFormService.add( vo );
        return new ResponseEntity<>( "게임명이 등록되었습니다.", HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = { "/{id}/del" }, method = RequestMethod.POST )
    public ResponseEntity<String> del ( @PathVariable( value = "id" ) Integer id ) {
        gameService.del( new GameVO( id ) );
        return new ResponseEntity<>( "게임이 삭제되었습니다.", HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = { "/form/{id}/del" }, method = RequestMethod.POST )
    public ResponseEntity<String> delForm ( @PathVariable( value = "id" ) Integer id ) {
        gameFormService.del( new GameFormVO( id ) );
        return new ResponseEntity<>( "게임명이 삭제되었습니다.", HttpStatus.OK );
    }

}
