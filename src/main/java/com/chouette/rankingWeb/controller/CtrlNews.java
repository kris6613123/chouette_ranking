package com.chouette.rankingWeb.controller;

import com.chouette.rankingWeb.service.FileService;
import com.chouette.rankingWeb.service.NewsService;
import com.chouette.rankingWeb.vo.NewsVO;
import com.chouette.rankingWeb.vo.SmarteditorVO;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@Controller
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping( value = "/news", method = RequestMethod.GET )
public class CtrlNews extends CtrlBase{

    private final NewsService newsService;
    private final FileService fileService;

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = "/list" )
    public String list ( Model model, @RequestParam( defaultValue = "1", required = false ) Integer pagenum, @RequestParam(defaultValue = "10", required = false) Integer contentnum, @RequestParam(value="subject", required = false ) String keyowrd ) {
        newsService.getPagingList( model, pagenum, contentnum, keyowrd );
        return "news/list";
    }

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/mod", "/{id}/mod"} )
    public String mod ( Model model, @PathVariable( value = "id", required = false ) Integer id ) {
        NewsVO news = new NewsVO();
        if(id != null) {
            news = newsService.getItem( new NewsVO( id ) );
        }
        model.addAttribute( "news", news );

        return "news/mod";

    }
    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/mod/p" }, method = RequestMethod.POST )
    public ResponseEntity<String> mod ( @Valid @RequestBody NewsVO vo ) throws IOException {
        newsService.save( vo );
        return new ResponseEntity<>( "게시글을 등록하였습니다.", HttpStatus.OK );
    }

    @PreAuthorize( "hasAnyAuthority( 'ROOT' )" )
    @RequestMapping( value = { "/{id}/del" }, method = RequestMethod.POST )
    public ResponseEntity<String> del ( @PathVariable( value = "id" ) Integer id ) {
        newsService.del( new NewsVO( id ) );
        return new ResponseEntity<>( "게시글을 삭제하였습니다.", HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/view" )
    public String view ( Model model, @PathVariable( value = "id" ) Integer id ) {
        model.addAttribute( "news", newsService.getItem( new NewsVO( id ) ) );
        return "news/view";
    }

    @ResponseBody
    @RequestMapping( value = {"/image/upload"}, method = RequestMethod.POST  )
    public void uploadNewsImage( SmarteditorVO smarteditorVO, HttpServletResponse response ) throws IOException {
        String callback = smarteditorVO.getCallback();
        String callback_func = smarteditorVO.getCallback_func();
        MultipartFile file = smarteditorVO.getFiledata();
        String result = "&bNewLine=true&sFileName=" + new String( file.getOriginalFilename().getBytes("8859_1"), "UTF-8" ) + "&sFileURL=/images/";

        if( !file.isEmpty() ) {
            result = result + newsService.saveFile( file );
        }

        response.sendRedirect(callback + "?callback_func=" + callback_func + result);
    }

}
