package com.chouette.rankingWeb.controller;

import com.chouette.rankingWeb.service.BranchService;
import com.chouette.rankingWeb.service.FileService;
import com.chouette.rankingWeb.vo.BranchVO;
import com.chouette.rankingWeb.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( value = "/branch", method = RequestMethod.GET )
@PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
public class CtrlBranch extends CtrlBase{
    private final BranchService branchService;

    @RequestMapping( value = { "/list" } )
    public String list( Model model ) {
        model.addAttribute( "branchList", branchService.getAllList() );
        return "branch/list";
    }

    @RequestMapping( value = { "/mod", "/{id}/mod" } )
    public String mod(Model model, @PathVariable( value = "id", required = false ) Integer id ) {
        BranchVO branch = new BranchVO();
        if ( id != null ) {
            branch = branchService.getItem( new BranchVO( id ) );
        }
        model.addAttribute( "branch", branch );
        return "branch/mod";
    }

    @RequestMapping( value = {"/mod/p"}, method = RequestMethod.POST )
    public ResponseEntity<String> mod( @Valid @RequestPart( "vo" ) BranchVO vo, @RequestPart( value = "formFile", required = false ) MultipartFile file, @RequestPart( value = "formFile-logo", required = false ) MultipartFile fileLogo ) throws IOException {
        branchService.save( vo, file, fileLogo );
        return new ResponseEntity<>( "성공적으로 등록되었습니다.", HttpStatus.OK );
    }

    @RequestMapping( value = { "/{id}/del" }, method = RequestMethod.POST  )
    public ResponseEntity<String> del( @PathVariable( value = "id" ) Integer id ) {
        branchService.del( new BranchVO( id ) );
        return new ResponseEntity<>("매장을 삭제하였습니다.", HttpStatus.OK);
    }

}
