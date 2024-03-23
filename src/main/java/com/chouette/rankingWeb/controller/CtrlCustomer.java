package com.chouette.rankingWeb.controller;

import com.chouette.rankingWeb.service.*;
import com.chouette.rankingWeb.service.std.StdTierService;
import com.chouette.rankingWeb.vo.BranchVO;
import com.chouette.rankingWeb.vo.CustomerVO;
import com.chouette.rankingWeb.vo.RankingHistoryVO;
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

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( value = "/customer", method = RequestMethod.GET )
@Validated
public class CtrlCustomer extends CtrlBase {

    private final CustomerService customerService;
    private final BranchService branchService;
    private final GameService gameService;
    private final StdTierService stdTierService;
    private final RankingHistoryService rankingHistoryService;

    @PreAuthorize( "hasAnyAuthority('ADMIN', 'ROOT')" )
    @RequestMapping( value = { "/list" } )
    public String list ( Model model, @RequestParam(defaultValue = "1", required = false) Integer pagenum, @RequestParam(defaultValue = "10", required = false) Integer contentnum, @RequestParam(value="customer", required = false ) String keyowrd ) {
        customerService.getPagingList( model, pagenum, contentnum, keyowrd );
        return "customer/list";
    }

    @PreAuthorize( "hasAnyAuthority( 'ADMIN', 'ROOT' )" )
    @RequestMapping( value = { "/mod", "/{id}/mod" } )
    public String mod( Model model, @PathVariable( value = "id", required = false ) Integer id ) {
        CustomerVO customer = new CustomerVO();
        List<BranchVO> branchList = branchService.getList();
        if ( id != null ) {
            customer = customerService.getItem( new CustomerVO( id ) );
        }
        model.addAttribute( "customer", customer );
        model.addAttribute( "branchList", branchList );

        return "customer/mod";
    }

    @PreAuthorize( "hasAnyAuthority('ADMIN', 'ROOT')" )
    @RequestMapping( value = {"/mod/p"}, method = RequestMethod.POST )
    public ResponseEntity<String> mod( @RequestPart( "vo" ) CustomerVO vo, @RequestPart( value = "formFile", required = false ) MultipartFile file ) throws IOException {
        if( !customerService.checkValidate( vo ) ) {
            return new ResponseEntity<>( "이미 존재하는 전화번호이거나 온라인 키입니다.", HttpStatus.BAD_REQUEST );
        }
        customerService.save( vo, file );

        return new ResponseEntity<>( "성공적으로 등록되었습니다.", HttpStatus.OK );
    }

    @PreAuthorize( "hasAnyAuthority('ADMIN', 'ROOT')" )
    @RequestMapping( value = {"/{id}/del"}, method = RequestMethod.POST )
    public ResponseEntity<String> del( @PathVariable( value = "id" ) Integer id ) {
        customerService.delete( new CustomerVO( id ) );
        gameService.delByCustomer( id );
        return new ResponseEntity<>( "성공적으로 삭제되었습니다.", HttpStatus.OK );
    }


    @RequestMapping( value = { "/{id}/view" } )
    public String view( Model model, @PathVariable( value = "id" ) Integer id, @RequestParam( value="game", required = false ) String keyowrd ) {
        model.addAttribute( "tier", stdTierService.getItemByCustomer( id ) );
        model.addAttribute( "customer", customerService.getItem( new CustomerVO( id ) ) );
        model.addAttribute( "ranking", rankingHistoryService.getItemByCustomer( id ) );
        model.addAttribute( "gameList", gameService.getListByCustomer( id, keyowrd ) );
        return "customer/view";
    }

    @ResponseBody
    @RequestMapping( value = {"/{id}/chart"}, method = RequestMethod.POST  )
    public ResponseEntity<List<RankingHistoryVO>> chartData( @PathVariable( value = "id" ) Integer id ) {
        return new ResponseEntity<>( rankingHistoryService.getListByCustomer( id ), HttpStatus.OK );
    }

}