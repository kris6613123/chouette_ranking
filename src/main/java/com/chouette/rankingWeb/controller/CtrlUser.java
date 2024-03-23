package com.chouette.rankingWeb.controller;

import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.service.BranchService;
import com.chouette.rankingWeb.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping( value = "/user",  method = RequestMethod.GET)
public class CtrlUser extends CtrlBase {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final BranchService branchService;

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/list" } )
    public String list (Model model) {
        model.addAttribute("userList", userService.getAllList() );
        return "user/list";
    }

    @RequestMapping( value = { "/login" } )
    public String login( @RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model ) {
        //이미 로그인한 유저라면 home으로 리다이렉트
        if( !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser") ) {
            return "redirect:/";
        }
        model.addAttribute( "error", error );
        model.addAttribute( "exception", exception );
        model.addAttribute( "user", new UserVO() );
        return "user/login";
    }

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/join" } )
    public String join( Model model ) {
        model.addAttribute( "branchList", branchService.getList() );
        return "user/join";
    }

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/join/p" }, method = RequestMethod.POST )
    public ResponseEntity<String> join( @Valid @RequestBody UserVO vo) {

        if( userService.getItemById( vo.getId() ) != null ) {
            return new ResponseEntity<>("이미 존재하는 계정입니다.", HttpStatus.BAD_REQUEST);
        }
        vo.setPwd( encoder.encode( vo.getPwd() ) );
        userService.add( vo );
        return new ResponseEntity<>("계정을 생성하였습니다.", HttpStatus.OK);
    }

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/{id}/del" }, method = RequestMethod.POST  )
    public ResponseEntity<String> del( @PathVariable( value = "id" ) Integer id ) {
        userService.del( new UserVO( id ) );
        return new ResponseEntity<>("계정을 삭제하였습니다.", HttpStatus.OK);
    }

}